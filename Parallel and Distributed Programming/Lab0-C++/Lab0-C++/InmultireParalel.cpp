#include "InmultireParalel.h"
#include "CalculatorParalelOptimizat.h"
#include <vector>
#include <thread>

void InmultireParalel::initResultsofMultiplication() {
	for (int i = 0; i < numarMare2.size(); i++) {
		resultOfMultiplication.push_back({});
	}
}

void InmultireParalel::addBackZeros() {
	for (int i = 0; i < resultOfMultiplication.size(); i++) {
		for (int j = 0; j < i; j++) {
			resultOfMultiplication.at(i).push_back(0);
		}
	}
}

void InmultireParalel::addFrontZeros() {
	for (int i = 0; i < resultOfMultiplication.size(); i++) {
		std::reverse(std::begin(resultOfMultiplication.at(i)), std::end(resultOfMultiplication.at(i)));
		for (int j = 0; j < numarMare1.size() - 1 - i; j++) {
			resultOfMultiplication.at(i).push_back(0);
		}
		std::reverse(std::begin(resultOfMultiplication.at(i)), std::end(resultOfMultiplication.at(i)));
	}
}

void InmultireParalel::getResultsOfMultiplication() {
	int size = numarMare2.size();
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

				std::vector<int> currentResult;
				std::vector<int> currentCarry;
				currentCarry.push_back(0);

				for (int ii = 0; ii < numarMare1.size(); ii++) {

					int result = numarMare1.at(ii) * numarMare2.at(j);
					currentResult.push_back(result % 10);

					if (result > 9) {
						int carry = result / 10;
						currentCarry.push_back(carry);
					}
					else currentCarry.push_back(0);
				}

				std::vector<int> rezultat;
				for (int k = 0; k < currentResult.size(); k++)
				{
					rezultat.push_back((currentResult.at(k) + currentCarry.at(k)) % 10);

					if (currentResult.at(k) + currentCarry.at(k) > 9) {
						currentCarry.at(k + 1) = currentCarry.at(k + 1) + 1;
					}

				}
				if (currentCarry.at(currentCarry.size() - 1) != 0)
					rezultat.push_back(currentCarry.at(currentCarry.size() - 1));
				std::reverse(std::begin(rezultat), std::end(rezultat));
				resultOfMultiplication.at(j) = rezultat;

			}
		}));

		start = stop;
	}

	for (int i = 0; i < nr_threads; i++) {
		threads[i].join();
	}
}

std::vector<int> InmultireParalel::inmulteste() {

	addBackZeros();
	addFrontZeros();

	std::vector<int> currentResult = resultOfMultiplication.at(0);

	for (int i = 1; i < resultOfMultiplication.size(); i++) {

		CalculatorParalelOptimizat calculator(nr_threads, currentResult, resultOfMultiplication.at(i));
		currentResult = calculator.aduna();
	}

	int i = 0;
	while (currentResult.at(i) == 0) {
		currentResult.erase(currentResult.begin());
	}

	return currentResult;
}