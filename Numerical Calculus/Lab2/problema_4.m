function s=problema_4(x)
 b = (x<0); % semn
 x = abs(x);
 s = zeros(1,53); % vectorul pentru semnificant
 [f,e] = log2(x) % x = f*(2^e); e-exponent
 %construim s pe baza lui f
 for i = 1:53
  f = 2*f;
  d = floor(f);
  f = f - d;
  s(i) = d+48;
 end
 s = ['0' s sprintf('*2^(%d)',e)];
 if b
     s = ['-' s];
 end