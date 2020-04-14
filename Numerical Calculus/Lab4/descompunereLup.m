function [L,U,P]=descompunereLup(A)
% apel [L,U,P]=descompunereLup(A)
% permuta efectiv liniile

[m,~] = size(A);
piv = (1:m)';
for i=1:m-1
  
  %pivotare
  [~,kp] = max(abs(A(i:m,i))); %kp = pozitie pivot
  kp = kp+i-1;
  
  %interschimbare
  if i ~= kp
    A([i,kp],:) = A([kp,i],:);
    piv([i,kp],:) = piv([kp,i],:);
  end
  
  %complementul Schur
  lin = i+1:m;
  A(lin,i) = A(lin,i)/A(i,i);
  A(lin,lin) = A(lin,lin) - A(lin,i)*A(i,lin);
end

%extrag rezultatele
U = triu(A);
L = tril(A,-1)+eye(m);
P = eye(m);
P = P(piv,:);  
  
end