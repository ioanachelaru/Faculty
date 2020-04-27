% Scrieti o rutina care reprezinta grafic o cubica parametrica Hermite (o curba care trece prin doua puncte date si are in acele puncte tangente date)

function problema_3()

    % functia x^2 pe intervalul [-2, 2]
    x = [-2 2]; % punctele -2 si 2
    f = [4 4]; 
    fd = [-4 4];
    
    t = -2 : 0.01 : 2;
    res = interpolareHermiteMultiplePoints(x, f, fd, t);
    
    plot(t, res);
end