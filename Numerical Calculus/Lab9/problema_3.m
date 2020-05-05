% La masurarea unui segment de drum, presupunem ca am efectuat 5 masuratori AD = 89m, AC = 67m, BD = 53m, AB = 35m si CD = 20m, Sa se determine lungimile segmentelor x1 = AB, x2 = BC si x3 = CD.

function problema_3()

    A = [1 1 1; 1 1 0; 0 1 1; 1 0 0; 0 0 1];
    B = [89; 67; 53; 35; 20];
    a = linsolve(A, B)
end