from copy import deepcopy
import numpy as np
import random
import matplotlib.pyplot as plot
from VariantaEvolutiva.utils import Chromosome

'''
    initializeaza o populatie cu un numar de indivizi si un numar de gene
    fiecare individ are un nr de gene egal cu nuamrul de litere al puzzle-ului
    :param noInds: numarul de indivizi dintr-o populatie
    :param cryptoPuzzle: puzzle-ul '''


def initialize(noInds, cryptoPuzzle):
    population = []
    permutare = deepcopy(cryptoPuzzle.letters)
    while len(permutare) < 10:
        permutare.append('*')
    for i in range(noInds):
        perm = list(np.random.permutation(permutare))
        population.append(Chromosome(perm))
    return population


'''
    algoritmul evalueaza fiecare individ din populatie
    :param population: populatia actuala
    :param obiecte: infromatia utila despre obiecte '''


def evaluation(population, cryptoPuzzle):
    for chormosome in population:
        chormosome.evaluate(cryptoPuzzle)


'''
    functia selecteaza din populatie 2 indivizi si il returneaza pa cel cu fitnes mai bun
    :param population:
    :return: un individ din populatie
    '''


def selection(population):
    sample = random.sample(population, k=len(population)//10)
    return max(sample, key=lambda x: x.fit)


'''
    combina doi indivizi din populatie, dati ca parametrii pentru a rezulta unul nou
    :param M: un individ "mama"
    :param F: un individ "tata"
    :return: un individ nou, combinat din M si F '''


def crossOver(M, F):
    ch = Chromosome()
    for i in range(0, 5):
        ch.perm.append(deepcopy(M.perm[i]))
    j = 0
    for i in range(5, len(M.perm)):
        while j < 10 and F.perm[j] in ch.perm:
            j += 1
        if j < 10:
            ch.perm.append(deepcopy(F.perm[j]))
        j += 1
    while len(ch.perm) < 10:
        ch.perm.append('*')
    return ch


'''
    produce o mutatie random in genele individului primit prin parametru
    :return individul cu mutatie '''


def mutation(offspring):
    pos1 = random.choice(range(len(offspring.perm)))
    pos2 = random.choice(range(len(offspring.perm)))
    temp = deepcopy(offspring.perm[pos1])
    offspring.perm[pos1] = deepcopy(offspring.perm[pos2])
    offspring.perm[pos2] = deepcopy(temp)
    return offspring


'''
    algoritmul evolutic
    :param noGenerations: numarul de generatii pentru algoritm
    :param noIndividuals: numarul de indivizi din populatie
    :param obiecte: informatia despre obiecte si rucsac
    :return: returneaza cel mai bun individ '''


def evolutivAlgorithm(noGenerations, noIndividuals, cryptoPuzzle):
    population = initialize(noIndividuals, cryptoPuzzle)
    evaluation(population, cryptoPuzzle)
    best = max(population, key=lambda x: x.fit)
    puncte = [best.fit]
    for _ in range(noGenerations):
        if best.fit == len(cryptoPuzzle.result):
            break
        popAux = []
        for _ in range(noIndividuals):
            M = selection(population)
            F = selection(population)
            offspring = crossOver(M, F)
            mutation(offspring)
            popAux.append(offspring)
        population = popAux
        evaluation(population, cryptoPuzzle)
        currentBest = max(population, key=lambda x: x.fit)
        if currentBest.fit > best.fit:
            best = currentBest
            puncte.append(best.fit)
            print("New current best: {}".format(currentBest.fit))
    print("Final best: {}".format(best.fit))
    plot.plot(puncte)
    plot.show()
    return best.perm