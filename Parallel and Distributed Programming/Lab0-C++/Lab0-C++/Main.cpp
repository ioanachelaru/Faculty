#include<iostream>
#include "Worker.h"
#include "CalculatorSerial.h"
#include "CalculatorParalel.h"
#include "CalculatorParalelOptimizat.h"
#include "InmultireSecvential.h"
#include "InmultireParalel.h"
#include<vector>
#include <chrono>

using namespace std;

int main() {

	Worker worker;

	/*
	worker.writeToFileEasy("numbers.txt", 10, 40, 500);

	if (worker.compareFiles("numbers.txt", "numbers.txt"))
		std::cout << "Identical files\n";
	else std::cout << "The files are different\n";

	std::vector<int> vector = { 56,90,87,43,213,674,986 };
	worker.writeListInFile("new.csv", vector);
	*/

	//worker.writeToFileEasy("moreNumbers.txt", 7, 1, 9);
	//worker.writeToFileEasy("numbers.txt", 7, 1, 9);

	std::vector<int> numar1 = worker.getDataFromFile("moreNumbers.txt");
	std::vector<int> numar2 = worker.getDataFromFile("numbers.txt");

	CalculatorParalel calculator(1,numar1, numar2);

	//InmultireSecvential calculator(numar1, numar2);
	//InmultireParalel calculator(16, numar1, numar2);

	auto start = std::chrono::high_resolution_clock::now();

	std::vector<int> rezultat = calculator.aduna();

	auto finish = std::chrono::high_resolution_clock::now();

	for(int nr : rezultat)
	{
		cout << nr;
	}

	cout << endl;

	std::cout << std::chrono::duration_cast<std::chrono::nanoseconds>(finish - start).count() << "ns\n";

	system("pause");
	return 0;
}