% Fie f(x) = e^x^2 − 1
% Aproximati f(1.25) utilizand valorile lui f ın 
% 1, 1.1, 1.2, 1.3 ¸si 1.4 si dati o delimitare a erorii

function lab6_probl_prac_2()
    
    nodes = [1 1.1 1.2 1.3 1.4];
    y = @(x)exp(x.^2-1);
    disp('Valoarea functiei in 1.25:');
    % vom determina valoarea polinomului de interpolare Lagrange in punctul
    % x = 1.25, pentru nodurile nodes date si functia data, folosind
    % P4_InterpolLagrangeFdat.
    disp(lab6_problema4(1.25, y, 4, nodes));
    % vom delimita eroarea conform formulei : |(Rnf)(x)|<= M / (m+1)! * |u(x)|
    delim = delimitateError(1.25, nodes);
    disp('Eroarea este <=');
    disp(delim);
    % delim = 2.38*10^(-4)
end

function delim = delimitateError(t, nodes) 
    syms x
    n = length(nodes);
    y = exp(x^2-1);
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