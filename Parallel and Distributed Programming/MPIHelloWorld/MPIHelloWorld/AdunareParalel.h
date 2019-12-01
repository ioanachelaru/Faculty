#pragma once
#include <vector>

class AdunareParalel {
private:
	std::vector<int> numarMare1;
	std::vector<int> numarMare2;
	std::vector<int> rezultat;
	int nr_processes;

	

	void checkVectors();
	void initVectors();
	

public:
	std::vector<int> vectorRezultat;
	std::vector<int> vectorCarry;
	AdunareParalel(int nP, std::vector<int> n1, std::vector<int> n2)
	{
		nr_processes = nP;
		numarMare1 = n1;
		numarMare2 = n2;

		std::reverse(std::begin(numarMare1), std::end(numarMare1));
		std::reverse(std::begin(numarMare2), std::end(numarMare2));

		checkVectors();
		initVectors();
	}
	void pas1();
	std::vector<int> aduna();
};