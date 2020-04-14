%Reprezentati pe acelasi grafic f si Lmf

nodes = [0, 1];
nodevals = exp(nodes);

t = 0 : 0.01 : 1;
res = interpolareLagrangeMultiplePoints(nodes, nodevals, t);

plot(t, res, 'r');
hold on;

resexp = exp(t);
plot(t, resexp);
hold off;