from copy import deepcopy

class Crypto:
    def __init__(self):
        self.dictionary=dict()
        self.words=[]
        self.result=[]
        self.state=None
        self.found = False

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
        file.close()

    #Verifica daca o solutie este completa (s-au asignat valori tuturor literelor)

    def complete(self, dictionary):
        for key in dictionary:
            if dictionary[key] == -1:
                return False
        return True

    # Verifica corectitudinea operatiei de adunare

    def checkSum(self, dictionary):
        sumWords = 0
        for word in self.words:
            p = 1
            sum = 0
            for i in range(len(word) - 1, -1, -1):
                sum = sum + p * dictionary[word[i]]
                p = p * 10
            sumWords = sumWords + sum

        sumRes = 0
        p = 1
        for i in range(len(self.result) - 1, -1, -1):
            sumRes = sumRes + p * dictionary[self.result[i]]
            p = p * 10

        if sumWords == sumRes: return True
        return False

    #Verifica daca un rezultat este solutie: sa nu fie vida, sa fie completa si sa verifice operatia de adunare

    def solution(self,dictionary):
        if dictionary is None: return False
        if not self.complete(dictionary): return False
        return self.checkSum(dictionary)

    # Verifica daca un rezultat este valid si merita a se continua prelucrarea lui

    def valid(self,dictionary):

        # verificare ca 2 chei diferite sa nu aiba aceeasi valoare
        for key in dictionary:
            for otherKey in dictionary:
                if dictionary[key]== dictionary[otherKey] and key != otherKey and dictionary[key] != -1 and dictionary[otherKey] != -1:
                    return False

        # prima litera a unui cuvant =/= 0
        for word in self.words:
            if dictionary[word[0]]==0: return False
        if dictionary[self.result[0]] == 0: return False

        # daca solutia este completa, dar nu verifica suma
        if self.complete(dictionary) and not self.checkSum(dictionary): return False

        return True

    #Returneaza urmatoarea cheie de verificat sau None daca nu mai exista

    def nextKey(self):
        lg = max(len(self.result), max([len(x) for x in self.words]))
        for i in range(lg - 1,-1,-1):
            for word in self.words:
                if i<len(word):
                    if self.dictionary[word[i]]==-1:
                        return word[i]
            if i < len(self.result):
                if self.dictionary[self.result[i]] == -1:
                    return self.result[i]
        return None

    #Apleaza functia solve_aux cu parametrul []

    def solve(self):
        return self.solve_aux([])

    #Realizeaza generarea de solutii in mod recursiv

    def solve_aux(self,stiva):
        stiva.append(None)
        for p in range(0, 10):
            if self.found:
                return self.state
            currentKey = self.nextKey()
            if stiva[-1] is None:
                if not currentKey is None:
                    stiva[-1] = currentKey
                else:continue
            else: 
                currentKey = stiva[-1]
                self.dictionary[currentKey] = -1
                
            self.dictionary[currentKey] = p
            if self.valid(self.dictionary):
                if self.solution(self.dictionary):
                    self.found = True
                    self.state = deepcopy(self.dictionary)
                    return self.state
                self.solve_aux(stiva)
            else:
                self.dictionary[currentKey] = -1
        self.dictionary[currentKey] = -1
        stiva.pop()
        if self.found:
            return self.state

    def printSol(self):
        file=open("D:\AI\Lab1-CRYPTO\out.txt","w")
        result=self.solve()
        file.write(str(len(result))+'\n')
        for l in sorted(result.keys()):
            file.write(l+',')
            file.write(str(result.get(l))+'\n')
        file.close()