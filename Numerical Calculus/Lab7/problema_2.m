% Reprezentati pe acelasi grafic f si polinomul sau de interpolare Hermite

function problema_2()

    nodes = [0.8 1 1.4 1.8 2.1];
    nodevals = exp(nodes);
    
    t = 1 : 0.01 : 2;
    res = interpolareHermiteMultiplePoints(nodes, nodevals, nodevals, t);
    
    plot(t, res);
    hold on;
    
    resexp = exp(t);
    plot(t, resexp);
    hold off;
end