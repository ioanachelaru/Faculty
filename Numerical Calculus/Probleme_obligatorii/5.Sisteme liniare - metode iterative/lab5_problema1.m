function x = lab5_problema1(A,x, eroare)
	% rezolva iterativ sistemul A*x = b prin metoda Gauss-Seidel
	D = diag(diag(A));
	L = -tril(A,-1);
	U = -triu(A,1);
	%D=A+L+U
	
	M = D;
	N = L+U;
	
	% Minv = inv(M);
	Minv = diag(1./diag(A)); %mai eficient in acest caz particular
	
	T = Minv * N;
	c = Minv *x;
	
	nIteratii = 0;
	x1 = x;
	x2 = T*x1 + c;
	while norm(x2-x1)>(1-norm(T))/norm(T)*eroare
		x1 = x2;
		x2 = T*x1 + c;
		nIteratii = nIteratii+1;
		if (nIteratii > 100)
			error('Nu converge');
		end
	end 
	x = x1;
end