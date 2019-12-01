#pragma once
#include <string>
#include <vector>

using namespace std;

class MPI_Adder2
{
private:
	string filenameA, filenameB;
public:

	void loadSizesFromFile(const string& filenameA, const string& filenameB, int& a, int& b);
	void loadArrayPartsFromFile(int startPos, int endPos, vector<int>& a, vector<int>& b, int len_diff);
	void getResult(string outputFile);
	MPI_Adder2(string filenameA, string filenameB);
};