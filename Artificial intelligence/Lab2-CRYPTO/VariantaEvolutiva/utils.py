""" Clasa defineste un obiect de tip CryptoPuzzle si memoreaza informatiile utile:
    - un dictionar cu toate literele intalnite;
    - o lista de liste cu cuvintele puzzle-ului;
    - rezultatul puzzle-ului """


class CryptoPuzzle:
    def __init__(self, letters, words, result):
        self.letters = letters
        self.words = words
        self.result = result


""" Clasa defineste un obiect de tip Chromosome:
    - perm = o permuatre a literelor puzzle-ului;
    - fit = valoarea fitness-ului 
    - cryptoPuzzle = puzzle-ul """


class Chromosome:
    def __init__(self, perm=None):
        if perm is None:
            perm = []
        self.perm = perm
        self.fit = 0

    """ Calculeaza suma puzzle-ului conform permutarii curente """

    def calculateSumWords(self, cryptoPuzzle):
        sumWords = 0
        for word in cryptoPuzzle.words:
            p = 1
            suma = 0
            for i in range(len(word) - 1, -1, -1):
                suma = suma + p * self.perm.index(word[i])
                p = p * 10
            sumWords = sumWords + suma
        return sumWords

    """ Calculeaza valoarea fitness-ului in functie de numarul de litere corecte din rezultat """

    def evaluate(self, cryptoPuzzle):
        sumWords = self.calculateSumWords(cryptoPuzzle)
        reverse = list(reversed(cryptoPuzzle.result))
        i = 0
        count = 0
        if len(sumWords.__str__()) > len(reverse):
            return count
        while sumWords > 0 and i < len(reverse):
            n = sumWords % 10
            if self.perm[n] == reverse[i]:
                count += 1
            i += 1
            sumWords = sumWords // 10
        for word in cryptoPuzzle.words:
            if self.perm.index(word[0]) == 0:
                count = count - 1
        if self.perm.index(cryptoPuzzle.result[0]):
            count = count - 1
        self.fit = count