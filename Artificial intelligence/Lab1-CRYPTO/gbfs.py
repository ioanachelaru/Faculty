
class Greedy:
    def __init__(self):
        self.dictionary=dict()
        self.words=[]
        self.result=[]
        self.lst=[]

        self.initAll()

    #Initializeaza toate campurile clasei

    def initAll(self):
        file = open("D:\AI\Lab1-CRYPTO\in.txt", "r")
        number=int(file.readline())

        for line in file.read().split("\n"):
            letters=line.split(",")
            let=[]
            for l in letters:
                let.append(l)
                self.dictionary.update({l:-1})
            self.words.append(let)

        self.result=self.words.pop()
        self.lst=list(self.dictionary.keys())
        file.close()

    def printSol(self):
        file=open("D:\AI\Lab1-CRYPTO\out.txt","w")
        result=self.greedy()
        if result is not None:
            file.write(str(len(self.lst))+'\n')
            for l in sorted(self.lst):
                file.write(l+',')
                file.write(str(result.index(l))+'\n')
        else: print("Nu exista solutii!")
        file.close()

    def permutation(self,lst):

        #Daca lista e vida, nu sunt permutari
        if len(lst) == 0:
            return []

        #Daca lista are un singur element => o singura permutare
        if len(lst) == 1:
            return [lst]

        #Genereaza permutarile pentru listele cu mai mult de un element

        l = []  #retine permutarea curenta

        #Itereaza lst si genereaza permutarea
        for i in range(len(lst)):
            m = lst[i]
            remLst = lst[:i] + lst[i + 1:]      #remLst - lista ramasa

            #Genereaza toate permutarile care incep cu m
            for p in self.permutation(remLst):
                l.append([m] + p)
        return l

    # Verifica corectitudinea operatiei de adunare

    def checkSum(self, lst):
        sumWords = 0
        for word in self.words:
            p = 1
            suma = 0
            for i in range(len(word) - 1, -1, -1):
                suma = suma + p * lst.index(word[i])
                p = p * 10
            sumWords = sumWords + suma

        sumRes = 0
        p = 1
        for i in range(len(self.result) - 1, -1, -1):
            sumRes = sumRes + p * lst.index(self.result[i])
            p = p * 10

        if sumWords == sumRes: return True
        return False

    def checkZero(self,lst):
        for i in lst:
            if i != '*':
                for j in self.words:
                    if i in j and j.index(i) == 0 and lst.index(i) == 0:
                        return False
                if i in self.result and self.result.index(i) == 0 and lst.index(i) == 0:
                    return False
        return True

    def greedy(self):
        lst=list(self.dictionary.keys())
        while len(lst) < 10:
            lst.append("*")
        for perm in self.permutation(lst):
            if self.checkZero(perm):
                if self.checkSum(perm):
                    return perm
        return None