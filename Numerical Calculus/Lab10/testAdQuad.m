% test functie adaptive pentru calculul unei Integrale
function testAdQuad()

    % se calculeaza integrala de la 0 la pi din sin(x)dx, cu eps = 10^(-2)
    
    % f - integrala
    f = @(x) sin(x);
    
    % a, b - capele integralei
    a = 0;
    b = pi;
    
    epsi = 10e-2;
    
    % apelul functiei
    I = problema_5(f,a,b,epsi)
end
