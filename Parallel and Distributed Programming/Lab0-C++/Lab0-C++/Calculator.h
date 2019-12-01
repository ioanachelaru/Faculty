#pragma once
#include<vector>

class Calculator {
private:
	std::vector<int> numarMare1;
	std::vector<int> numarMare2;

	std::vector<std::string> pas1();
	std::vector<std::string> pas2();
	std::vector<int> pas3();
	std::vector<int> pas4();
	void checkVectors();
	std::string operatorX(std::string, std::string);

public:
	Calculator(std::vector<int> n1, std::vector<int> n2)
	{
		numarMare1 = n1;
		numarMare2 = n2;

		std::reverse(std::begin(numarMare1), std::end(numarMare1));
		std::reverse(std::begin(numarMare2), std::end(numarMare2));

		checkVectors();
	}
	std::vector<int> aduna();
};