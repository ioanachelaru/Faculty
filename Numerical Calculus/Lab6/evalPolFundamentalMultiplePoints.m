function [ val ] = evalPolFundamentalMultiplePoints( nodes, k, x )
    val = zeros(size(x));
    [l, c] = size(x);
    for i = 1 : c 
       val(1, i) = evalPolFundamental(nodes, k, x(1, i)); 
    end
end
