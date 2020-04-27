% Pentru f(x) = e^x si nodurile de interpolare 0, 1, 2, aproximati f(0.25) prin interpolare Hermite si comparati rezultatul cu cel obtinut prin interpolare Lagrange. Dati o delimitare a erorii. Comparati cu rezultatul furnizat de software-ul utilizat.

function problema_practica_1()

    nodes = [0 1 2];

    disp("Interpolare Hermite:");
    interpolareHermite(nodes, [exp(0) exp(1) exp(2)], [exp(0) exp(1) exp(2)], 0.25)
    
    disp("Interpolare Lagrange:");
    y = @(x)exp(x);
    % vom determina valoarea polinomului de interpolare Lagrange in punctul x = 0.25, pentru nodes date si functia y
    disp(interpolareLagrange(0.25, y, 3, nodes));
    
    % vom delimita eroarea conform formulei : |(Rnf)(x)|<= M / (m+1)! * |u(x)|
    delim = delimitateError(0.25, nodes);
    disp('Eroarea este <=');
    disp(delim)
    
    disp("Valoarea furnizata de sistem:");
    exp(0.25)

end

function [ val ] = interpolareLagrange( x, f, m, nodes )
    
    considered = nodes(1 : m);
    consideredVals = f(considered);
    
    val = aprox(considered, consideredVals, x);
end

function [ val ] = aprox( nodes, nodevals, point )
    % aproximeaza punctul dat prin interpolare Lagrange
    val = 0;
    
    [lin, col] = size(nodes);
    m = col;
    x = point;
    
    for k = 1 : m
       f = nodevals(1, k); % f(xk) 
       
       % calculam lk(x)
       u = 1; d = 1;
       for j = 1 : m
          if j ~= k
              u = u * (x - nodes(1, j)); % x - xj
              d = d * (nodes(1, k) - nodes(1, j)); % xk - xj
          end
       end
       
       val = val + f * (u / d);
    end
end

function delim = delimitateError(t, nodes) 
    syms x
    n = length(nodes);
    y = exp(x);
    deriv = diff(y, n);
    x = nodes(1);
    max = eval(subs(deriv));
    for i = 2:n
       x = nodes(i);
       val = eval(subs(deriv));
       if val > max
           max = val;
       end
    end
    
    M = max;
    u = 1;
    for i = 1:n
        u = u * (t - nodes(i));
    end
    
    delim = M/factorial(n) * abs(u);
end