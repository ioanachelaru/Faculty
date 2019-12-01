#pragma once
#include <vector>
#include "InmultireSecvential.h"
#include "CalculatorSerial.h"

void InmultireSecvential::addBackZeros() {
	for (int i = 0; i < resultOfMultiplication.size(); i++) {
		for (int j = 0; j < i; j++) {
			resultOfMultiplication.at(i).push_back(0);
		}
	}
}

void InmultireSecvential::addFrontZeros() {
	for (int i = 0; i < resultOfMultiplication.size(); i++) {
		std::reverse(std::begin(resultOfMultiplication.at(i)), std::end(resultOfMultiplication.at(i)));
		for (int j = 0; j < numarMare1.size() - 1 - i; j++) {
			resultOfMultiplication.at(i).push_back(0);
		}
		std::reverse(std::begin(resultOfMultiplication.at(i)), std::end(resultOfMultiplication.at(i)));
	}
}

void InmultireSecvential::getResultsOfMultiplication() {
	for (int i = 0; i < numarMare1.size(); i++) {
		std::vector<int> currentResult = {};
		std::vector<int> currentCarry = {};
		currentCarry.push_back(0);

		for (int j = 0; j < numarMare2.size(); j++) {
			int result = numarMare1.at(i) * numarMare2.at(j);
			currentResult.push_back(result % 10);

			if (result > 9) {
				int carry = result / 10;
				currentCarry.push_back(carry);
			}
			else currentCarry.push_back(0);
		}

		std::vector<int> rezultat = {};
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

		resultOfMultiplication.push_back(rezultat);
	}

	addBackZeros();
	addFrontZeros();
}

std::vector<int> InmultireSecvential::inmulteste() {
	std::vector<int> currentResult = resultOfMultiplication.at(0);

	for (int i = 1; i < resultOfMultiplication.size(); i++) {

		CalculatorSerial calculatorSerial(currentResult, resultOfMultiplication.at(i));
		currentResult = calculatorSerial.aduna();
	}

	return currentResult;
}
