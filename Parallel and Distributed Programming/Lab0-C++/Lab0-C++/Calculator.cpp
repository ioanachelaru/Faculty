#include "Calculator.h"

void Calculator::checkVectors() {
	while (numarMare1.size() > numarMare2.size())
		numarMare2.push_back(0);
	while (numarMare1.size() < numarMare2.size())
		numarMare1.push_back(0);
}

std::string Calculator::operatorX(std::string x, std::string y) {
	if (x == "N" && y == "N")
		return "N";
	else if (x == "N" && y == "M")
		return "N";
	else if (x == "N" && y =="C")
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

std::vector<std::string> Calculator::pas1() {
	std::vector<std::string> c;
	for (int i = 0; i < numarMare1.size(); i++) {
		if (numarMare1.at(i) + numarMare2.at(i) >= 10)
			c.push_back("C");

		if (numarMare1.at(i) + numarMare2.at(i) == 9)
			c.push_back("M");

		if (numarMare1.at(i) + numarMare2.at(i) <= 8)
			c.push_back("N");
	}
	return c;
}

std::vector<std::string> Calculator::pas2() {
	std::vector<std::string> c = pas1();
	std::vector<std::string> e;
	for (int i = 0; i < c.size(); i++) {
		if (i == 0) {
			e.push_back(c.at(i));
		}
		else
			if (c.at(i) == "M")
			{
				int j = i - 1;
				while (c.at(j) == "M" && j > 0) {
					j--;
				}
				if (c.at(j) == "C")
					e.push_back("C");
				else e.push_back(operatorX(c.at(i - 1), c.at(i)));
			}
			else e.push_back(operatorX(c.at(i - 1), c.at(i)));
	}
	return e;
}

std::vector<int> Calculator::pas3() {
	std::vector<int> m;
	std::vector<std::string> e = pas2();
	for (int i = 0; i < e.size(); i++) {
		if (i == 0) {
			m.push_back(0);
		}
		else
			if (e.at(i - 1) == "C")
				m.push_back(1);
			else
				m.push_back(0);
	}
	return m;
}

std::vector<int> Calculator::pas4() {
	std::vector<int> r;
	std::vector<int> m = pas3();
	for (int i = 0; i < m.size(); i++) {
		r.push_back((numarMare1.at(i) + numarMare2.at(i) + m.at(i)) % 10);
		if (i == m.size() - 1) {
			int b = (numarMare1.at(i) + numarMare2.at(i) + m.at(i)) % 100;
			if (b > 9) {
				r.push_back(b / 10);
			}
		}
	}

	return r;
}

std::vector<int> Calculator::aduna() {
	std::vector<int> r = pas4();
	std::reverse(std::begin(r), std::end(r));
	return r;
}