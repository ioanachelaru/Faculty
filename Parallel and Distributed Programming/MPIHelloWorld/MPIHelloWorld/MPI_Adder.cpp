#include "pch.h"
#include "mpi.h"
#include "MPI_Adder.h"
#include <iostream>
#include <string>
#include <fstream>
#include <algorithm>
#include <vector>
#include <chrono>

using namespace std;
using namespace std::chrono;

/*
	Loads datasets from filenameA and filenameB into vectors a and b
*/
void MPI_Adder::loadFromFile(const string& filenameA, const string& filenameB, vector<int>& a, vector<int>& b) {
	
	// opens files
	ifstream f(filenameA);
	ifstream g(filenameB);

	string lineA, lineB;

	// reads content
	f >> lineA;
	g >> lineB;

	// resizes both vectors
	a = vector<int>(max(lineA.length(), lineB.length()) + 1);
	b = vector<int>(max(lineA.length(), lineB.length()) + 1);

	// saves the data into the vectors, but in reverse sense
	for (int i = 0; i < lineA.length(); i++) {
		a[lineA.length() - i - 1] = (int)lineA[i] - '0';
	}

	for (int i = 0; i < lineB.length(); i++) {
		b[lineB.length() - i - 1] = (int)lineB[i] - '0';
	}

	// closes files
	f.close();
	g.close();
}

/*
	Computes the result and prints it into the output file
*/
void MPI_Adder::getResult(string outputFile)
{
	/*
		procCount - number of processes
		rank - the rank of the current process
	*/
	int procCount, rank, rc = MPI_Init(NULL, NULL);

	MPI_Status status;
	
	if (rc != MPI_SUCCESS) {
		cout << "Error\n";
		MPI_Abort(MPI_COMM_WORLD, rc);
	}

	// Get number of processes and current rank
	MPI_Comm_size(MPI_COMM_WORLD, &procCount);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	// Parent process
	if (rank == 0) {

		/*
			a - first big number
			b - second big number
			result - the result of the sum
		*/
		vector<int> a, b, result;

		// Get data from filenameA into a and filenameB into b
		loadFromFile(filenameA, filenameB, a, b);

		// The result's length
		const int RESULT_LENGTH = max(a.size(), b.size());

		// Number of operations per process
		const int OPS_PER_PROC = RESULT_LENGTH / (procCount - 1);

		// Number of remaining operations
		int EXTRA_OPS = RESULT_LENGTH % (procCount - 1);

		// Starting and ending point of the addition
		int startPos = 0, endPos = OPS_PER_PROC;

		auto start = high_resolution_clock::now();

		// Distribuites data to each slave process
		for (int i = 1; i < procCount; i++) {

			// Distribuites equaly the remaining operations 
			if (EXTRA_OPS > 0) {
				EXTRA_OPS--;
				endPos++;
			}

			// The data to be sent to the slave process
			vector<int> toSend_a = vector<int>(a.begin() + startPos, a.begin() + endPos);
			vector<int> toSend_b = vector<int>(b.begin() + startPos, b.begin() + endPos);

			// The size of the vectors to be sent
			int size_a = toSend_a.size();
			int size_b = toSend_b.size();

			// Sending the informations to slave process
			MPI_Send(&endPos, 1, MPI_INT, i, 4, MPI_COMM_WORLD);
			MPI_Send(&size_a, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
			MPI_Send(&size_b, 1, MPI_INT, i, 1, MPI_COMM_WORLD);
			MPI_Send(toSend_a.data(), toSend_a.size(), MPI_INT, i, 2, MPI_COMM_WORLD);
			MPI_Send(toSend_b.data(), toSend_b.size(), MPI_INT, i, 3, MPI_COMM_WORLD);
			
			// Updating the star and end points for the next slave process
			startPos = endPos;
			endPos = endPos + OPS_PER_PROC;
		}

		// Receiving and assembling the data computed by each slave process
		for (int i = 1; i < procCount; i++) {
			
			// Temporary vector to receive result segment
			vector<int> temp;
			
			// The size of the result segment
			int result_size;

			MPI_Recv(&result_size, 1, MPI_INT, i, 8, MPI_COMM_WORLD, &status);
			
			// Resizing the temporary vector
			temp.resize(result_size);

			MPI_Recv(temp.data(), result_size, MPI_INT, i, 7, MPI_COMM_WORLD, &status);
			
			// Saving the data received into the result vector
			for (int i = 0; i < result_size; i++) {
				result.push_back(temp[i]);
			}
		}

		// Add the carry computed by each process
		for (int i = 1; i < procCount; i++) {
			
			/*
				endPos - starting position to add the carry
				carry - the carry received from the slave process
			*/
			int endPos, carry;
			
			MPI_Recv(&endPos, 1, MPI_INT, i, 5, MPI_COMM_WORLD, &status);
			MPI_Recv(&carry, 1, MPI_INT, i, 6, MPI_COMM_WORLD, &status);

			// Propagate the carry when needed
			while (carry == 1 && endPos < RESULT_LENGTH) {
				int crt = result[endPos];
				result[endPos] = (crt + carry) % 10;
				carry = (crt + carry) / 10;
				endPos += 1;
			}
		}

		auto finish = high_resolution_clock::now();
		auto duration = duration_cast<milliseconds>(finish - start);

		cout << "time: " << duration.count() << " milliseconds" << "\n";
		
		// Opening the output file
		ofstream g(outputFile);
		int i = result.size() - 1;
		
		// Erasing all useless zeros from the result computed
		while (result[i] == 0)
			i--;
		
		// Writing the result into the output file
		for (; i >= 0; i--) {
			g << result[i];
		}
	}
	else {
		// Containers to store the received data
		vector<int> toReceive_a, toReceive_b;
		int size_a, size_b, endPos;

		MPI_Recv(&endPos, 1, MPI_INT, 0, 4, MPI_COMM_WORLD, &status);
		MPI_Recv(&size_a, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		MPI_Recv(&size_b, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
		
		// Resize the vectors before storing the data in
		toReceive_a.resize(size_a);
		toReceive_b.resize(size_b);

		MPI_Recv(toReceive_a.data(), size_a, MPI_INT, 0, 2, MPI_COMM_WORLD, &status);
		MPI_Recv(toReceive_b.data(), size_b, MPI_INT, 0, 3, MPI_COMM_WORLD, &status);

		// Result container with the appropriate size
		vector<int> result(max(toReceive_a.size(), toReceive_b.size()));
		int carry = 0;
		
		// Compute the summation considering the carry
		for (int i = 0; i < toReceive_a.size(); i++) {
			result[i] = (toReceive_a[i] + toReceive_b[i] + carry) % 10;
			carry = (toReceive_a[i] + toReceive_b[i] + carry) / 10;
		}
		
		// The size of the result
		int result_size = result.size();

		// Sending the data back to the main process
		MPI_Send(&result_size, 1, MPI_INT, 0, 8, MPI_COMM_WORLD);
		MPI_Send(result.data(), result.size(), MPI_INT, 0, 7, MPI_COMM_WORLD);
		MPI_Send(&endPos, 1, MPI_INT, 0, 5, MPI_COMM_WORLD);
		MPI_Send(&carry, 1, MPI_INT, 0, 6, MPI_COMM_WORLD);

	}
	MPI_Finalize();
}

/*
	Constructor of the MPI_Adder class
*/
MPI_Adder::MPI_Adder(string filenameA, string filenameB) : filenameA{ filenameA }, filenameB{ filenameB } {}