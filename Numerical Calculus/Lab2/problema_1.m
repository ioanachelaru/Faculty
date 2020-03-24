% calcul epsilon-masina
epsilon = 1.0;
while (1+epsilon)~=1 
    epsilon = epsilon/2;  
end
epsilon = 2*epsilon;
display(epsilon);
epsilon-eps

% pow2(2 - eps, 1023)==realmax
%pow- Base 2 power and scale floating-point numbers
%calcul realmax fara a utiliza epsilon
 detRealMax=(1 + (1 - 2^-52))*2^1023;
 display(detRealMax);
 realmax-detRealMax
 
%calcul cel mai mic numarul nenormalizat reprezentabil
% Script to check the smallest possible power of two in Matlab 
power = 0;

while 2^power > 0
    power = power - 1;
end

power = power+53;
sprintf('Cel mai mic numar normalizat: %d', 2^power)
%2^power=realmin
realmin-2^power