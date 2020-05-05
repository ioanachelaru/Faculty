% Un asteroid ce orbiteaza ın jurul Soarelui a putut fi observat timp de cateva zile ınainte sa dispara. Se doreste calcularea traiectoriei pe baza acestor observatii pentru a putea prevedea situatia cand orbita va fi din nou vizibila. Se presupune un model elipsoidal pentru orbita x^2 = ay^2 + bxy + cx + dy + e. El ne conduce la un sistem supradeterminat, care trebuie rezolvat ın sensul celor mai mici patrate pentru a determina parametrii a, b, c, d, e. Realizati o estimare a erorii si un test de ıncredere ın model. Faceti acelasi lucru pentru modelul parabolic x^2 = ay + e. Care este mai probabil? 

x = [-1.024940 -0.949898 -0.866114 -0.773392 -0.671372 -0.559524 -0.437067 -0.302909 -0.159493 -0.007464]';
y = [-0.389269 -0.322894 -0.265256 -0.216557 -0.177152 -0.147582 -0.128618 -0.121353 -0.127348 -0.148895]';
A = [ y.^2 x.*y x y ones(size(x))];
bv = x.^2;
coef = A \ bv;
a = coef(1);
b = coef(2);
c = coef(3);
d = coef(4);
e = coef(5);    

u = linspace(-2,2,40);
v= linspace(-3,0,40);
[X, Y] = meshgrid(u,v)

Z = a*Y.^2 + b * X.*Y + c * X + d * Y + e -X.^2;
err = norm(x.^2 - a*y.^2 - b * x.*y - c*x - d * y - e)

clf
figure(1)
plot(x,y,'o');
hold on
[C,h] = contour(X,Y,Z,[0,0],'Linecolor',[1 0 0]);


A = [y ones(size(x))];
coef = A \ bv;
a = coef(1);
b = coef(2);

Z = a*Y + b - X.^2;
err = norm(x.^2 - a*y - b)
figure(2)
plot(x,y,'o');
contour(X,Y,Z,[0,0],'Linecolor',[1 0 0])