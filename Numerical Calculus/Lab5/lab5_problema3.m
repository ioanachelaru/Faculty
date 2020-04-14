% va fi rulat linie cu linie

clc

A=diag(ones(1,10)*5)-diag(ones(1, 9),-1)-diag(ones(1,9),1)
b=[4;ones(8,1)*3;4]
c=[3;ones(2,1)*2;ones(4,1);ones(2,1)*2;3]

x0=A\b

x1=lab5_problema1(A,b,1e-5)
x2=lab5_problema2(A,b,0.5,1e-5)

x5=A\c

x3=lab5_problema1(A,c,1e-5)
x4=lab5_problema2(A,c,0.5,1e-5)