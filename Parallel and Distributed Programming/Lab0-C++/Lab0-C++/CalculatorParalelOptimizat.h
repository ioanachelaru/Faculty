#pragma once
#include<vector>
class CalculatorParalelOptimizat {
private:
	int nr_threads;
	std::vector<int> numarMare1;
	std::vector<int> numarMare2;

	int size;
	std::vector<std::string> vectorC;
	std::vector<std::string> vectorE;
	std::vector<int> vectorM;
	std::vector<int> vectorR;

	void pas1();
	void pas2();
	void pas3();
	void pas4();
	void checkVectors();
	void initVectors();
	std::string operatorX(std::string, std::string);
public:
	CalculatorParalelOptimizat(int nT, std::vector<int> n1, std::vector<int> n2)
	{
		nr_threads = nT;
		numarMare1 = n1;
		numarMare2 = n2;

		std::reverse(std::begin(numarMare1), std::end(numarMare1));
		std::reverse(std::begin(numarMare2), std::end(numarMare2));

		checkVectors();

		size = numarMare1.size();

		initVectors();
	}
	std::vector<int> aduna();
};