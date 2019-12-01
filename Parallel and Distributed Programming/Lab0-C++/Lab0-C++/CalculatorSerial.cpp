#pragma once
#include "CalculatorSerial.h"

void CalculatorSerial::checkVectors() {
	while (numarMare1.size() > numarMare2.size())
		numarMare2.push_back(0);
	while (numarMare1.size() < numarMare2.size())
		numarMare1.push_back(0);
}

std::vector<int> CalculatorSerial::aduna() {
	for (int i = 0; i < numarMare1.size(); i++) {
		vectorRezultat.push_back((numarMare1.at(i) + numarMare2.at(i)) % 10);
		if (numarMare1.at(i) + numarMare2.at(i) > 9)
			vectorCarry.push_back(1);
		else vectorCarry.push_back(0);
	}
	std::vector<int> rezultat;
	for (int i = 0; i < vectorRezultat.size(); i++) {
		rezultat.push_back((vectorRezultat.at(i) + vectorCarry.at(i)) % 10);
		if (vectorRezultat.at(i) + vectorCarry.at(i) > 9)
			vectorCarry.at(i + 1) = vectorCarry.at(i + 1) + 1;
	}

	if (vectorCarry.at(vectorCarry.size() - 1) == 1)
		rezultat.push_back(1);

	std::reverse(std::begin(rezultat), std::end(rezultat));
	return rezultat;
}