#pragma once
#include <vector>

class InmultireSecvential {
private:
	std::vector<int> numarMare1;
	std::vector<int> numarMare2;

	std::vector<std::vector<int>> resultOfMultiplication;

	void addBackZeros();
	void addFrontZeros();
	void getResultsOfMultiplication();

public:
	InmultireSecvential(std::vector<int> numar1, std::vector<int> numar2)
	{
		numarMare1 = numar1;
		numarMare2 = numar2;

		std::reverse(std::begin(numarMare1), std::end(numarMare1));
		std::reverse(std::begin(numarMare2), std::end(numarMare2));

		getResultsOfMultiplication();
	}

	std::vector<int> inmulteste();
};