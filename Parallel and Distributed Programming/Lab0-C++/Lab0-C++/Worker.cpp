#include<string>
#include<fstream>
#include <experimental/filesystem>
#include<iostream>
#include"Worker.h"
#include<vector>

void Worker::createFile(std::string file_name) {

	bool exist = std::experimental::filesystem::exists(file_name);

	if (exist)
		std::cout << "File already exists"<<std::endl;
	else {
		std::ofstream outfile(file_name);

		outfile.close();

		std::cout << "File created"<<std::endl;
	}

}

void Worker::writeToFileEasy(std::string file_name, int size, int min, int max) {
	Worker::createFile(file_name);

	std::ofstream myfile(file_name);
	if (myfile.is_open())
	{
		while (size>0)
		{
			int number = (rand() % (max - min + 1)) + min;
			myfile << number;
			size--;
			
			if (size > 0)
				myfile << ", ";
		}
		
		myfile.close();

		std::cout << "Successfully wrote to the file\n";
	}
	else std::cout << "Unable to open file\n";
}

bool Worker::compareFiles(std::string fileName1, std::string fileName2) {
	std::ifstream f1(fileName1, std::ifstream::binary | std::ifstream::ate);
	std::ifstream f2(fileName2, std::ifstream::binary | std::ifstream::ate);

	if (f1.fail() || f2.fail()) {
		return false; //file problem
	}

	if (f1.tellg() != f2.tellg()) {
		return false; //size mismatch
	}
	//seek back to beginning and use std::equal to compare contents
	f1.seekg(0, std::ifstream::beg);
	f2.seekg(0, std::ifstream::beg);
	return std::equal(std::istreambuf_iterator<char>(f1.rdbuf()),
		std::istreambuf_iterator<char>(),
		std::istreambuf_iterator<char>(f2.rdbuf()));
}

void Worker::writeListInFile(std::string file_name, std::vector<int> values) {
	std::ofstream fileStream(file_name, std::fstream::in | std::fstream::out | std::fstream::app);
	for (int v : values) {
		fileStream << v << ",";
	}
	fileStream << std::endl;
	fileStream.close();
}

std::vector<int> Worker::getDataFromFile(std::string file_name) {
	std::vector<int> data;
	std::ifstream file(file_name);
	std::string  line;

	std::string delimiter = ", ";
	size_t pos = 0;
	std::string token;

	while (std::getline(file, line))
	{
		
		while ((pos = line.find(delimiter)) != std::string::npos) {
			token = line.substr(0, pos);

			data.push_back(std::stoi(token));

			line.erase(0, pos + delimiter.length());
		}
		data.push_back(std::stoi(line));
	}

	file.close();
	return data;
}