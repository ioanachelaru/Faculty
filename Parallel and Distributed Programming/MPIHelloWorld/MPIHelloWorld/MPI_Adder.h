#pragma once
#include <string>
#include <vector>

using namespace std;

class MPI_Adder
{
private:
	string filenameA, filenameB;
public:

	void loadFromFile(const string& filenameA, const string& filenameB, vector<int>& a, vector<int>& b);
	void getResult(string outputFile);
	MPI_Adder(string filenameA, string filenameB);
};