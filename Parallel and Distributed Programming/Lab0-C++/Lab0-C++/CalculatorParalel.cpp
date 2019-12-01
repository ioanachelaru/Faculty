#include "CalculatorParalel.h"
#include <thread>
using namespace std;

void CalculatorParalel::checkVectors() {
	while (numarMare1.size() > numarMare2.size())
		numarMare2.push_back(0);
	while (numarMare1.size() < numarMare2.size())
		numarMare1.push_back(0);
}

void CalculatorParalel::initVectors() {
	for (int i = 0; i < numarMare1.size(); i++) {
		vectorRezultat.push_back(0);
		vectorCarry.push_back(0);
		rezultat.push_back(0);
	}
	vectorCarry.push_back(0);
}

void CalculatorParalel::pas1() {
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
				vectorRezultat.at(j) = (numarMare1.at(j) + numarMare2.at(j)) % 10;
				if (numarMare1.at(j) + numarMare2.at(j) > 9)
					vectorCarry.at(j + 1) = 1;
				else
					vectorCarry.at(j + 1) = 0;
			}
		}));

		start = stop;
	}

	for (int i = 0; i < nr_threads; i++) {
		threads[i].join();
	}
}

std::vector<int> CalculatorParalel::aduna() {
	pas1();

	std::vector<std::thread> threads;

	int size = vectorRezultat.size();
	int start = 0, stop;
	int chunck_size = size / nr_threads, reminder = size % nr_threads;

	for (int i = 0; i < nr_threads; i++) {
		if (reminder > 0) {
			stop = start + chunck_size + 1;
			reminder--;
		}
		else stop = start + chunck_size;

		threads.push_back(std::thread([start, stop, i, this]() {
			for (int j = start; j < stop; j++) {
				rezultat.at(j) = (vectorRezultat.at(j) + vectorCarry.at(j)) % 10;
				if (vectorRezultat.at(j) + vectorCarry.at(j) > 9)
					vectorCarry.at(j + 1) = vectorCarry.at(j + 1) + 1;
			}

			if (i == nr_threads - 1)
				if (vectorCarry.at(vectorCarry.size() - 1) == 1)
					rezultat.push_back(1);
		}));

		threads[i].join();
		start = stop;
	}
	std::reverse(std::begin(rezultat), std::end(rezultat));
	return rezultat;
}