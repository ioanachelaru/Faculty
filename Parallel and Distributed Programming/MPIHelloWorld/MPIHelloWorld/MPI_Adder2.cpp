#include "pch.h"
#include "mpi.h"
#include "MPI_Adder2.h"
#include <iostream>
#include <string>
#include <fstream>
#include <algorithm>
#include <vector>
#include <chrono>

using namespace std;
using namespace std::chrono;

/*
	Loads the sizes of both files
*/
void MPI_Adder2::loadSizesFromFile(const string& filenameA, const string& filenameB, int& a, int& b)
{
	ifstream f(filenameA.c_str(), ios::binary);
	f.seekg(0, ios::end);
	a = f.tellg();

	ifstream g(filenameB.c_str(), ios::binary);
	g.seekg(0, ios::end);
	b = g.tellg();

	f.close();
	g.close();
}

/*
	Loads parts of the numbers within given interval
*/
void MPI_Adder2::loadArrayPartsFromFile(int startPos, int endPos, vector<int>& a, vector<int>& b, int len_diff) {
	string __filenameA, __filenameB;
	
	
	if (len_diff < 0) {
		__filenameA = filenameB;
		__filenameB = filenameA;
		len_diff *= -1;
	}
	else {
		__filenameA = filenameA;
		__filenameB = filenameB;
	}
	ifstream f(__filenameA);
	ifstream g(__filenameB);

	int start_pos_b = startPos, end_pos_b = endPos;
	if (len_diff != 0) {
		start_pos_b = startPos - len_diff;
		end_pos_b = endPos - len_diff;
	}
	if (start_pos_b >= 0) {
		g.seekg(start_pos_b, std::ios::beg);
	}

	f.seekg(startPos, std::ios::beg);
	char ch, chh;
	while (startPos < endPos) {
		if (f.peek() == EOF || f.fail()) {
			ch = '0';
		}
		else {
			f >> ch;
		}

		if (g.peek() == EOF || g.fail() || start_pos_b < 0) {
			chh = '0';
			start_pos_b++;
		}
		else {
			g >> chh;
		}
		int digit = ch - '0';
		int digitt = chh - '0';
		a.push_back(digit);
		b.push_back(digitt);
		startPos++;
	}

	std::reverse(a.begin(), a.end());
	std::reverse(b.begin(), b.end());
	f.close();
	g.close();
}


/*

*/
void MPI_Adder2::getResult(string outputFile)
{
	int procCount, rank, buf = 0;
	MPI_Status status;
	int rc = MPI_Init(NULL, NULL);

	if (rc != MPI_SUCCESS) {
		cout << "Error\n";
		MPI_Abort(MPI_COMM_WORLD, rc);
	}

	MPI_Comm_size(MPI_COMM_WORLD, &procCount);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	if (rank == 0) {
		int size_a, size_b;
		
		loadSizesFromFile(filenameA, filenameB, size_a, size_b);
		
		int len_diff = size_a - size_b;
		const int RESULT_LENGTH = max(size_a, size_b);
		const int OPS_PER_PROC = RESULT_LENGTH / (procCount - 1);
		int EXTRA_OPS = RESULT_LENGTH % (procCount - 1);

		vector<int> result;
		int endPos = RESULT_LENGTH;
		int startPos = RESULT_LENGTH - OPS_PER_PROC;

		auto start = high_resolution_clock::now();
		for (int i = 1; i < procCount; i++) {
			if (EXTRA_OPS > 0) {
				EXTRA_OPS--;
				startPos--;
			}

			MPI_Send(&startPos, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
			MPI_Send(&endPos, 1, MPI_INT, i, 1, MPI_COMM_WORLD);
			MPI_Send(&len_diff, 1, MPI_INT, i, 42, MPI_COMM_WORLD);

			endPos = startPos;
			startPos = startPos - OPS_PER_PROC;
		}

		for (int i = 1; i < procCount; i++) {
			vector<int> temp;
			int result_size;
			MPI_Recv(&result_size, 1, MPI_INT, i, 8, MPI_COMM_WORLD, &status);
			temp.resize(result_size);
			MPI_Recv(temp.data(), result_size, MPI_INT, i, 7, MPI_COMM_WORLD, &status);
			for (int i = 0; i < result_size; i++) {
				result.push_back(temp[i]);
			}
		}
		result.push_back(0); //poate rezultatul sa fie cu o cifra mai lung decat cel mai lung numar

		for (int i = 1; i < procCount; i++) {
			int startPos, carry;
			MPI_Recv(&startPos, 1, MPI_INT, i, 5, MPI_COMM_WORLD, &status);
			MPI_Recv(&carry, 1, MPI_INT, i, 6, MPI_COMM_WORLD, &status);
			startPos = RESULT_LENGTH - startPos;

			while (carry == 1 && startPos < RESULT_LENGTH + 1) {
				int crt = result[startPos];
				result[startPos] = (crt + carry) % 10;
				carry = (crt + carry) / 10;
				startPos += 1;
			}
		}
		auto finish = high_resolution_clock::now();
		auto duration = duration_cast<milliseconds>(finish - start);

		cout << "time: " << duration.count() << " milliseconds" << "\n";
		ofstream g(outputFile);
		int i = result.size() - 1;
		while (result[i] == 0)
			i--;
		for (; i >= 0; i--) {
			g << result[i];
		}
	}
	else {
		vector<int> a, b;
		int startPos, endPos;
		int len_diff;

		MPI_Recv(&startPos, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		MPI_Recv(&endPos, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
		MPI_Recv(&len_diff, 1, MPI_INT, 0, 42, MPI_COMM_WORLD, &status);

		loadArrayPartsFromFile(startPos, endPos, a, b, len_diff);
		vector<int> result(max(a.size(), b.size()));

		int carry = 0;
		for (int i = 0; i < a.size(); i++) {
			result[i] = (a[i] + b[i] + carry) % 10;
			carry = (a[i] + b[i] + carry) / 10;
		}
		int result_size = result.size();

		MPI_Send(&result_size, 1, MPI_INT, 0, 8, MPI_COMM_WORLD);
		MPI_Send(result.data(), result.size(), MPI_INT, 0, 7, MPI_COMM_WORLD);
		MPI_Send(&startPos, 1, MPI_INT, 0, 5, MPI_COMM_WORLD);
		MPI_Send(&carry, 1, MPI_INT, 0, 6, MPI_COMM_WORLD);

	}
	MPI_Finalize();
}

/*
	Constructor of the MPI_Adder2 class
*/
MPI_Adder2::MPI_Adder2(string filenameA, string filenameB) : filenameA{ filenameA }, filenameB{ filenameB } {}