% Dandu-se x, f, m si nodurile, aproximati f(x) utilizand 
%interpolarea Lagrage

function [ val ] = lab6_problema4( x, f, m, nodes )
    
    considered = nodes(1 : m);
    consideredVals = f(considered);
    
    val = lab6_problema1(considered, consideredVals, x);
end