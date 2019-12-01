#pragma once
#include <vector>

class InmultireParalel {
private:
	int nr_threads;
	std::vector<int> numarMare1;
	std::vector<int> numarMare2;

	std::vector< std::vector<int> > result_of_multiplication;

	std::vector<std::vector<int>> resultOfMultiplication;

	void addBackZeros();
	void addFrontZeros();
	void initResultsofMultiplication();
	void getResultsOfMultiplication();

public:
	InmultireParalel(int nr, std::vector<int> numar1, std::vector<int> numar2)
	{
		nr_threads = nr;
		numarMare1 = numar1;
		numarMare2 = numar2;

		std::reverse(std::begin(numarMare1), std::end(numarMare1));
		std::reverse(std::begin(numarMare2), std::end(numarMare2));

		initResultsofMultiplication();

		getResultsOfMultiplication();
	}

	std::vector<int> inmulteste();
};