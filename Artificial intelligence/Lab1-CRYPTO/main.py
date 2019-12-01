from dfs import Crypto
from gbfs import Greedy

def main():
    while True:
        print("Ce metoda doriti sa apelati?")
        print("1 -> DFS")
        print("2 -> GBFS")
        print("0 -> Exit")
        method = input("Metoda aleasa de dvs. este: ")

        if method == '1':
            c = Crypto()
            c.printSol()

        elif method == '2':
            g = Greedy()
            g.printSol()

        elif method == '0':
            break
        else: print("Metoda incorecta!\nIncercati din nou. :)")

main()