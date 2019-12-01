""" Clasa defineste un obiect de tip CryptoPuzzle si memoreaza informatiile utile:
    - o lista cu toate literele intalnite;
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

    """ Transforma rezultatul puzzle-ului in numar conform permutarii curente """

    def calculateResult(self, cryptoPuzzle):
        sumRes = 0
        p = 1
        for i in range(len(cryptoPuzzle.result) - 1, -1, -1):
            sumRes = sumRes + p * self.perm.index(cryptoPuzzle.result[i])
            p = p * 10
        return sumRes

    def checkFirstLetters(self, cryptoPuzzle):
        for word in cryptoPuzzle.words:
            if self.perm.index(word[0]) == 0:
                return False
        if self.perm.index(cryptoPuzzle.result[0]) == 0:
            return False
        return True

    """ Calculeaza valoarea fitness-ului in functie de numarul de litere corecte din rezultat """

    def evaluate(self, cryptoPuzzle):
        self.fit = 0
        sumWords = self.calculateSumWords(cryptoPuzzle)
        sumRes = self.calculateResult(cryptoPuzzle)

        if len(sumWords.__str__()) != len(sumRes.__str__()):
            self.fit = 10

        elif not self.checkFirstLetters(cryptoPuzzle):
            self.fit = 10

        else:
            while sumRes > 0:
                difference = abs(sumRes % 10 - sumWords % 10)
                self.fit = self.fit + difference
                sumWords = sumWords // 10
                sumRes = sumRes // 10