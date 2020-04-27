% Utilizati valorile date mai jos pentru a aproxima sin 0:34 utilizand interpolarea Hermite. Dati o delimitare a erorii si comparati-o cu eroarea exacta. Adaugati datele pentru nodul x = 0:33 si refaceti calculele.

function problema_practica_2()
    
    nodes = [0.30 0.32 0.35];
    interpolareHermite(nodes, [0.29552 0.31457 0.34290], 
    [0.95534 0.94924 0.93937], 0.34)
    
    % vom delimita eroarea conform formulei : |(Rnf)(x)|<= M / (m+1)! * |u(x)|
    delim = delimitateError(0.34, nodes);
    disp('Eroarea este <=');
    disp(delim);
    
    new_nodes = [0.30 0.32 0.33 0.35];
    interpolareHermite(new_nodes, [0.29552 0.31457 sin(0.33) 0.34290], 
    [0.95534 0.94924 cos(0.33) 0.93937], 0.34)
    
    new_delim = delimitateError(0.34, nodes);
    disp('Eroarea este <=');
    disp(new_delim);

end

function delim = delimitateError(t, nodes) 
    syms x
    n = length(nodes);
    y = sin(x);
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