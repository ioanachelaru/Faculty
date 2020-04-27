function yAprox = splineComplete(x,y,yd1,yd2,xAprox)

    % x, y - punctele de interpolat (doi vectori de aceeasi lungime)
    % yd1 = y'(x(1))
    % yd2 = y'(x(length(x))
    
    n = length(x);
    
    
    %P[1](x) = c1 + c2*(x - x1) + c3*(x-x1)^2 + c4*(x-x1)^3;
    %P[2](x) = c5 + c6*(x - x2) + c7*(x - x2)^2 + c8*(x - x2)^3;
    %P[i](x) = c[4i-3] + c[4i-2] *(x - xi) + c[4i-1] *(x - xi)^2 + c[4i] *(x - xi)^3;
    
    % ecuatiile:
    % (1) P[i](x[i]) = y[i]; (interpolare) i=1..n-1
    % (2) P[i](x[i+1]) = P[i](x[i+1]); (continuitate) i=1..n-1
    % (3) P[i]'(x[i+1]) = P[i]'(x[i+1]); (continuitate derivata) i=1..n-1
    % (4) P[i]"(x[i+1]) = P[i]"(x[i+1]); (continuitate derivata a doua) i=1..n-1
    % (5) P[n-1](x[n]) = y[n]; interpolare in ultimul nod
    
    % alte doua conditii "arbitrare":
    % pt spline complete:
    % (6) P[1]'(x[1]) = m
    % (7) P[n-1]"(x[n]) = 
    
    M = zeros(4*n - 4, 4*n - 4);
    t = zeros(4*n - 4, 1);
    nEcuatie = 1;
    
    % ecuatia 1
    for i = 1:n-1
        M(nEcuatie, 4*i - 3) = 1;
        t(nEcuatie) = y(i);
        nEcuatie = nEcuatie + 1;
    end
    
    % ecuatia 2
    for i = 1:n-2
        M(nEcuatie, 4*i - 3) = 1;
        M(nEcuatie, 4*i - 2) = x(i+1) - x(i);
        M(nEcuatie, 4*i - 1) = (x(i+1) - x(i))^2;
        M(nEcuatie, 4*i) = (x(i+1) - x(i))^3;
        M(nEcuatie, 4*i + 1) = -1;
        nEcuatie = nEcuatie + 1;
    end
    
    % ecuatia 3
    for  i = 1:n-2
        M(nEcuatie, 4*i - 2) = 1;
        M(nEcuatie, 4*i - 1) = 2*(x(i+1) - x(i));
        M(nEcuatie, 4*i) = 3*(x(i+1) - x(i))^2;
        M(nEcuatie, 4*i + 2) = -1;
        nEcuatie = nEcuatie + 1;
    end
    
    % ecuatia 4
    for i = 1:n-2 
        M(nEcuatie, 4*i - 1) = 1;
        M(nEcuatie, 4*i) = x(i+1) - x(i);
        M(nEcuatie, 4*i + 3) = -1;
        nEcuatie = nEcuatie + 1;
    end
    
    % ecuatia 5
    M(nEcuatie, 4*n - 7) = 1;
    M(nEcuatie, 4*n - 6) = x(n) - x(n-1);
    M(nEcuatie, 4*n - 5) = (x(n) - x(n-1))^2;
    M(nEcuatie, 4*n - 4) =( x(n) - x(n-1))^3;
    t(nEcuatie) = y(n);
    nEcuatie = nEcuatie + 1;
    
    % ecuatia 6
    M(nEcuatie, 2) = 1;
    t(nEcuatie) = yd1;
    nEcuatie = nEcuatie + 1;
    
    % ecuatia 7
    M(nEcuatie, 4*n - 6) = 1;
    M(nEcuatie, 4*n - 5) = 2*(x(n) - x(n-1));
    M(nEcuatie, 4*n - 4) = 3*(x(n) - x(n-1))^2;
    t(nEcuatie) = yd2;
    
    c = M \ t;
    
    % calculul fctei
    
    for k = 1:length(xAprox)
        xk = xAprox(k);
    	% gaseste nodurile intre care se gaseste xk;
        for i = 1:n-1
            if x(i) <= xk & xk <= x(i+1)
                break;
            end
        end
        yAprox(k) = c(4*i - 3) + c(4*i - 2)*(xk - x(i)) + c(4*i - 1)*(xk -x(i))^2+c(4*i)*(xk - x(i))^3;
    end    
end