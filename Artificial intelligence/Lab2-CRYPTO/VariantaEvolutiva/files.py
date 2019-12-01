from VariantaEvolutiva.utils import CryptoPuzzle

""" Functia citeste datele primite in fisier;
    :returns CryptoPuzzle: informatia despre puzzle 
    stocata sub forma de obiect de tip CryptoPuzzle """


def read(fileName):
    file = open(fileName, "r")

    # numarul de cuvinte ce se aduna
    number = int(file.readline())

    # multime ce retine toate literele aparute in
    myset = set()
    words = []

    for line in file.read().split("\n"):
        letters = line.split(",")
        let = []
        for l in letters:
            let.append(l)
            myset.add(l)
        words.append(let)

    result = words.pop()
    letters = list(myset)
    file.close()

    return CryptoPuzzle(letters, words, result)


""" Functia scrie datele rezultate in fisier """


def write(fileName, cryptoPuzzle, perm):
    file = open(fileName, "w")

    # scrie numarul de litere ale puzzle-ului
    file.write(str(len(cryptoPuzzle.letters)) + '\n')

    # scrie pe cate o linie litera si valoarea ei corespunzatoare
    for i in sorted(perm):
        if i != '*':
            file.write(str(i) + ',')
            file.write(str(perm.index(i)) + '\n')
    file.close()