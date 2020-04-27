% Evaluati spline-ul pe o multime de puncte, daca se dau nodurile, punctele si coeficientii

function z = problema_2(x,a,b,c,d,t)
    
    % x - nodurile
    % a,b,c,d - coeficientii
    % t - punctele in care se face evaluarea
    
    n = length(x);
    x = x(:); 
    t = t(:);
    k = ones(size(t));
    
    for j = 2:n-1
        k(x(j) <= t) = j;
    end
    
    % Evaluare interpolant
    s = t - x(k);
    z = d(k) + s.*(c(k) + s.*(b(k) + s.*a(k)));
end