function c=lab2_problema3(k,n)
    
    % coeficientii polinomului
    a = poly(1:n);
    
    %ultimul coef, x^n, nu ne intereseaza
    %vectorii incep de la indexul 1, nu de la 0
    a = a(2:end);
    
    %inmulteste liniile cu coloanele
    S = abs(a) * ( k.^[0:n-1])';
    r = [1:k-1,k+1:n];
    c = S/ (k*abs(prod(k-r)))
end