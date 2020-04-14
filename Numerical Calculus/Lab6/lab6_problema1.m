%Implementati o rutina pentru calculul valorilor polinomului de 
%interpolare Lagrange cand se dau punctele, nodurile si valorile 
%functiei ın noduri

function [ val ] = lab6_problema1( nodes, nodevals, point )
    % aproximeaza punctul dat prin interpolare Lagrange
    val = 0;
    
    [lin, col] = size(nodes);
    m = col;
    x = point;
    
    for k = 1 : m
       f = nodevals(1, k); % f(xk) 
       
       % calculam lk(x)
       u = 1; d = 1;
       for j = 1 : m
          if j ~= k
              u = u * (x - nodes(1, j)); % x - xj
              d = d * (nodes(1, k) - nodes(1, j)); % xk - xj
          end
       end
       
       val = val + f * (u / d);
    end
end
