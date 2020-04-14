% Datele de mai jos dau populatia SUA ın perioada 
% 1900 – 2000 (ın milioane de locuitori)
% Approximati populatia din 1975 si 2018

function lab6_probl_prac_1()
  vals = [1900 75.995
          1910 91.972
          1920 105.711
          1930 123.203
          1940 131.669
          1950 150.697
          1960 179.323
          1970 203.212
          1980 226.505
          1990 249.633
          2000 281.422
          2010 308.786] ;
  
  x = vals(:,1); y = vals(:,2);
  disp(x);
  disp(y);
  
  disp("1975 ");
  disp(lab6_problema1(x, y, 1975));
  
  disp("2018 ");
  disp(lab6_problema1(x, y, 2018));
  
  disp("1975       2018");
  disp(interpolareLagrangeMultiplePoints(x, y, [1975 2018]));
 
end