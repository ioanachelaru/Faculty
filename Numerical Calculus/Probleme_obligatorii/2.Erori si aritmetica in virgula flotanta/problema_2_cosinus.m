%calculul lui cos de x folosind  seria Taylor

function problema_2_cosinus
    %pentru k=10
    sprintf('Rezultat pentru 2*pi : %d', myCos(2*pi))
    sprintf('Rezultat pentru 4*pi : %d', myCos(4*pi))
    sprintf('Rezultat pentru 10*pi : %d', myCos(10*pi))
    sprintf('Rezultat pentru 20*pi : %d', myCos(20*pi))
    sprintf('Rezultat pentru 30*pi : %d', myCos(30*pi))
    sprintf('Rezultat pentru 40*pi : %d', myCos(40*pi))
end
%La valori mari ale lui x termenii din suma Taylor oscileaza foarte puternic
%O solutie ar fi reducerea la intervalul [0, 2pi]

function s=myCos(x)
    s=0;
    t=1;
    n=1;
    while s+t~=s
        s=s+t;
        t=t*x^2/(n*(n+1));
        n=n+2;
    end
end