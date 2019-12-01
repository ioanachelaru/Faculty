#pragma once
#include<vector>

class Worker {
public:
	void createFile(std::string  file_name);
	void writeToFileEasy(std::string, int, int, int);
	bool compareFiles(std::string, std::string);
	void writeListInFile(std::string, std::vector<int>);
	std::vector<int> getDataFromFile(std::string file_name);
};