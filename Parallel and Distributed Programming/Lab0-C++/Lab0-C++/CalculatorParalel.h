#pragma once
#include <vector>

class CalculatorParalel {
private:
	std::vector<int> numarMare1;
	std::vector<int> numarMare2;
	std::vector<int> rezultat;
	int nr_threads;

	std::vector<int> vectorRezultat;
	std::vector<int> vectorCarry;

	void checkVectors();
	void initVectors();
	void pas1();

public:
	CalculatorParalel(int nT, std::vector<int> n1, std::vector<int> n2)
	{
		nr_threads = nT;
		numarMare1 = n1;
		numarMare2 = n2;

		std::reverse(std::begin(numarMare1), std::end(numarMare1));
		std::reverse(std::begin(numarMare2), std::end(numarMare2));

		checkVectors();
		initVectors();
	}
	std::vector<int> aduna();
};