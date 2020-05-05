% Datele urmatoare dau populatia SUA (ın milioane) determinata la recensaminte de US Census, ıntre anii 1900 si 2010. Dorim sa modelam populatia si sa o estimam pentru anii 1975 si 2010. Modelati populatia printr-un model polinomial de gradul 3 y = c0 + c1t + c2t^2 + c3t^3, si printr-un model exponential y = Ke^(λt)

function problema_4()
   
    % model polinomial de grad 3
    x = [1900:10:2010];
    y = [75.995, 91.972, 105.710, 123.200, 131.670, 150.700, 179.320, 203.210, 226.510, 249.630, 281.420, 308.790];
    phi = @(x)[ones(1, length(x)); x; x.^2; x.^3];
    
    xAprox = x(1):(x(length(x))-x(1))/100:x(length(x));
    yAprox = least_squares_approx(x, y, phi, xAprox);
    figure(3);
    plot(x, y, 'o', xAprox, yAprox, '-');
    
    % model exponential
    phii = @(x)[ones(1, length(x)); exp(x); exp(x*2); exp(x*3)];
    
    xAproxx = x(1):(x(length(x))-x(1))/100:x(length(x));
    yAproxx = least_squares_approx(x, y, phii, xAproxx);
    figure(4);
    plot(x, y, 'o', xAprox, yAprox, '-');
end