#pragma once
#include <vector>

class CalculatorSerial {
private:
	std::vector<int> numarMare1;
	std::vector<int> numarMare2;

	std::vector<int> vectorRezultat;
	std::vector<int> vectorCarry;

	void checkVectors();

public:
	CalculatorSerial(std::vector<int> n1, std::vector<int> n2)
	{
		vectorCarry.push_back(0);
		numarMare1 = n1;
		numarMare2 = n2;

		std::reverse(std::begin(numarMare1), std::end(numarMare1));
		std::reverse(std::begin(numarMare2), std::end(numarMare2));

		checkVectors();
	}
	std::vector<int> aduna();
};