from VariantaEvolutiva import evolutiv, files

cryptoPuzzle = files.read("D:\AI\Tema2\VariantaEvolutiva\in.txt")
rezultat = evolutiv.evolutivAlgorithm(10000, 50, cryptoPuzzle)
files.write("D:\AI\Tema2\VariantaEvolutiva\out.txt", cryptoPuzzle, rezultat)