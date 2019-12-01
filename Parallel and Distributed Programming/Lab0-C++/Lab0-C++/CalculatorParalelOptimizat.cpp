#include "CalculatorParalelOptimizat.h"
#include<thread>

void CalculatorParalelOptimizat::checkVectors() {
	while (numarMare1.size() > numarMare2.size())
		numarMare2.push_back(0);
	while (numarMare1.size() < numarMare2.size())
		numarMare1.push_back(0);
}

void CalculatorParalelOptimizat::initVectors() {
	for (int i = 0; i < size; i++) {
		vectorC.push_back("0");
		vectorM.push_back(0);
		vectorE.push_back("0");
		vectorR.push_back(0);
	}
	vectorR.push_back(0);
}

std::string CalculatorParalelOptimizat::operatorX(std::string x, std::string y) {
	if (x == "N" && y == "N")
		return "N";
	else if (x == "N" && y == "M")
		return "N";
	else if (x == "N" && y == "C")
		return "C";
	else if (x == "M" && y == "N")
		return "N";
	else if (x == "M" && y == "M")
		return "M";
	else if (x == "M" && y == "C")
		return "C";
	else if (x == "C" && y == "N")
		return "N";
	else if (x == "C" && y == "M")
		return "C";
	else
		return "C";
}

void CalculatorParalelOptimizat::pas1() {
	int size = numarMare1.size();
	int start = 0, stop;
	int chunck_size = size / nr_threads, reminder = size % nr_threads;

	std::vector<std::thread> threads;

	for (int i = 0; i < nr_threads; i++) {
		if (reminder > 0) {
			stop = start + chunck_size + 1;
			reminder--;
		}
		else stop = start + chunck_size;

		threads.push_back(std::thread([start, stop, this]() {
			for (int j = start; j < stop; j++) {
				if (numarMare1.at(j) + numarMare2.at(j) >= 10)
					vectorC.at(j) = "C";

				if (numarMare1.at(j) + numarMare2.at(j) == 9)
					vectorC.at(j) = "M";

				if (numarMare1.at(j) + numarMare2.at(j) <= 8)
					vectorC.at(j) = "N";
			}
		}));

		start = stop;
	}

	for (int i = 0; i < nr_threads; i++) {
		threads[i].join();
	}
}

void CalculatorParalelOptimizat::pas2() {
	int size = numarMare1.size();
	int start = 0, stop;
	int chunck_size = size / nr_threads, reminder = size % nr_threads;

	std::vector<std::thread> threads;

	for (int i = 0; i < nr_threads; i++) {
		if (reminder > 0) {
			stop = start + chunck_size + 1;
			reminder--;
		}
		else stop = start + chunck_size;

		threads.push_back(std::thread([start, stop, this]() {
			for (int j = start; j < stop; j++) {
				if (j == 0) {
					vectorE.at(j) = vectorC.at(j);
				}
				else
					if (vectorC.at(j) == "M")
					{
						int y = j - 1;
						while (vectorC.at(j) == "M" && y > 0) {
							y--;
						}
						if (vectorC.at(y) == "C")
							vectorE.at(j) = "C";
						else vectorE.at(j) = operatorX(vectorC.at(j - 1), vectorC.at(j));
					}
					else vectorE.at(j) = operatorX(vectorC.at(j - 1), vectorC.at(j));
			}
		}));

		start = stop;
	}

	for (int i = 0; i < nr_threads; i++) {
		threads[i].join();
	}
}

void CalculatorParalelOptimizat::pas3() {
	int size = numarMare1.size();
	int start = 0, stop;
	int chunck_size = size / nr_threads, reminder = size % nr_threads;

	std::vector<std::thread> threads;

	for (int i = 0; i < nr_threads; i++) {
		if (reminder > 0) {
			stop = start + chunck_size + 1;
			reminder--;
		}
		else stop = start + chunck_size;

		threads.push_back(std::thread([start, stop, this]() {
			for (int j = start; j < stop; j++) {
				if (j == 0) {
					vectorM.at(j) = 0;
				}
				else
					if (vectorE.at(j - 1) == "C")
						vectorM.at(j) = 1;
					else
						vectorM.at(j) = 0;
			}
		}));

		start = stop;
	}

	for (int i = 0; i < nr_threads; i++) {
		threads[i].join();
	}
}

void CalculatorParalelOptimizat::pas4() {
	int size = numarMare1.size();
	int start = 0, stop;
	int chunck_size = size / nr_threads, reminder = size % nr_threads;

	std::vector<std::thread> threads;

	for (int i = 0; i < nr_threads; i++) {
		if (reminder > 0) {
			stop = start + chunck_size + 1;
			reminder--;
		}
		else stop = start + chunck_size;

		threads.push_back(std::thread([start, stop, this]() {
			for (int j = start; j < stop; j++) {
				vectorR.at(j) = (numarMare1.at(j) + numarMare2.at(j) + vectorM.at(j)) % 10;
				if (j == vectorM.size() - 1) {
					int b = (numarMare1.at(j) + numarMare2.at(j) + vectorM.at(j)) % 100;
					if (b > 9) {
						vectorR.at(j + 1) = b / 10;
					}
				}
			}
		}));

		start = stop;
	}

	for (int i = 0; i < nr_threads; i++) {
		threads[i].join();
	}
}

std::vector<int> CalculatorParalelOptimizat::aduna() {
	pas1();
	pas2();
	pas3();
	pas4();

	if (vectorR.at(vectorR.size() - 1) == 0)
		vectorR.pop_back();

	std::reverse(std::begin(vectorR), std::end(vectorR));
	return vectorR;
}