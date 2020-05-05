% Sa se gaseasca aproximanta discreta prin metoda celor mai mici patrate pentru ponderea w(x)=1 ¸si baza 1, x, x^2, . . . , x^n

function problema_1()
    x = 1:10;
    y = sin(1:10);
    xAprox = x(1):(x(length(x))-x(1))/100:x(length(x));
    phi = @(x)[ones(1, length(x)); x; x.^2; x.^3];
    
    yAprox = least_squares_approx(x, y, phi, xAprox);
    
    figure(5);
    plot(x, y, 'o', xAprox, yAprox, '-');
end