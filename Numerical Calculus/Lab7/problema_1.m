% Implementati o rutina pentru calculul valorilor polinomului de interpolare Hermite cu noduri duble, dandu-se punctele in care se face evaluarea, nodurile, valorile functiei si ale derivatei in noduri

function problema_1()

    interpolareHermite([1 1.5 2], [exp(1) exp(1.5) exp(2)], [exp(1) exp(1.5) exp(2)], 1.2)
    exp(1.2)
    
    interpolareHermite([1.3 1.6 1.9], [0.6200860 0.4554022 0.2818186], [-0.5220232 -0.5698959 -0.5811571], 1.5)
    % aprox 0.51
end