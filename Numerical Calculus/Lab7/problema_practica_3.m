% Un automobil care se deplaseaza pe un drum drept este cronometrat in mai multe puncte. Datele de observatie se dau in tabela de mai jos. Utilizati interpolarea Hermite pentru a prevedea pozitia si viteza automobilului la momentul t = 10.

function problema_prcatica_3()

    disp("Distanta in t = 10:");
    interpolareHermite([0 3 5 8 13], [0 225 383 623 993], [75 77 80 74 72], 10)
    
    disp("Viteza in t = 10:");
    interpolareHermite([0 3 5 8 13], [75 77 80 74 72], [0 225 383 623 993], 10)
    
end