from copy import deepcopy
import numpy as np
import random
import matplotlib.pyplot as plot
from HillClimbing.utils import Chromosome

''' Initializeaza starea initiala = o permutare generata random
        a literelor ce apar in puzzle '''


def initialState(cryptoPuzzle):
    permutare = deepcopy(cryptoPuzzle.letters)
    while len(permutare) < 10:
        permutare.append('*')
    perm = list(np.random.permutation(permutare))
    return perm


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


def simAnn(cryptoPuzzle):
    perm = initialState(cryptoPuzzle)
    current = Chromosome(perm)
    current.evaluate(cryptoPuzzle)
    best = deepcopy(current)
    puncte = [best.fit]
    print("Initial best: {}".format(best.fit))

    while best.fit > 0:
        newC = deepcopy(current)
        mutation(newC)
        newC.evaluate(cryptoPuzzle)

        if newC.fit < best.fit:
            best = deepcopy(newC)
            current = deepcopy(newC)
            puncte.append(best.fit)
            print("New best: {}".format(best.fit))

        elif random.uniform(0, 1) < 0.02:
            current = deepcopy(newC)
    plot.plot(puncte)
    plot.show()
    return best.perm