import VariantaEvolutiva.files as files
from HillClimbing.simulatedAnnealing import simAnn

cryptoPuzzle = files.read("D:\AI\Tema2\HillClimbing\in.txt")
rezultat = simAnn(cryptoPuzzle)
files.write("D:\AI\Tema2\HillClimbing\out.txt", cryptoPuzzle, rezultat)
