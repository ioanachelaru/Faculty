#include "pch.h"
#include "mpi.h"
#include "MPI_Adder.h"
#include "MPI_Adder2.h"
#include <iostream>
#include <string>
#include <fstream>
#include <algorithm>
#include <vector>
#include <chrono>

using namespace std;
using namespace std::chrono;

/*
	Creates an MPI_Adder object and calls the getResult function
*/
void v1() {
	MPI_Adder adder{ "numbers.txt", "moreNumbers.txt" };
	adder.getResult("output.txt");
}

/*
	Creates an MPI_Adder2 object and calls the getResult function
*/
void v2() {
	MPI_Adder2 adder{ "numbers.txt", "moreNumbers.txt" };
	adder.getResult("output.txt");
}

int main()
{
	v1();
	//v2();
	return 0;
}
