%Calculul lui Sin de x folosind  seria Taylor

function problema_2_sinus
    %pentru k=10
    sprintf('Rezultat pentru 2*pi : %d', SinTaylor(2*pi))
    sprintf('Rezultat pentru 4*pi : %d', SinTaylor(4*pi))
    sprintf('Rezultat pentru 10*pi : %d', SinTaylor(10*pi))
    sprintf('Rezultat pentru 20*pi : %d', SinTaylor(20*pi))
    sprintf('Rezultat pentru 30*pi : %d', SinTaylor(30*pi))
    sprintf('Rezultat pentru 40*pi : %d', SinTaylor(40*pi))
end
%La valori mari ale lui x termenii din suma Taylor oscileaza foarte puternic
%O solutie ar fi reducerea la intervalul [0, 2pi]

function s=SinTaylor(x)
  s=0;
  t=x;
  n=1;
  
  while s+t~=s
    s=s+t;
    t=-x^2/((n+1)*(n+2))*t;
    n=n+2;
  end
end