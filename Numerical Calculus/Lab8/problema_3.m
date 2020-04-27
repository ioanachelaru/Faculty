function problema_3()
    %testSplineBoor();
    testSplineComplete();
    %testSplineDer2();
    %testSplineNaturale();
end

function testSplineBoor()
    x = 0:10;
    y = sin(x);
    xi = 0:0.1:10;
    yi = splineBoor(x,y,xi);
    plot(x,y,'o', xi, yi, '-')
end

function testSplineComplete()
    x = 0:10;
    y = sin(x);
    xi = 0:0.1:10;
    m = cos(x(1));
    n = cos(x(length(x)));
    yi = splineComplete(x,y,m,n,xi);
    dxSageata = (x(length(x))-x(1))/10;
    plot(x,y,'o', xi, yi, '-', [x(1), x(1)+dxSageata], [y(1), y(1)+dxSageata*m], '-r',[x(length(x)), x(length(x))-dxSageata], [y(length(x)), y(length(x))-dxSageata*n], '-r')
end

function testSplineDer2()
   x = 0:10;
   y = sin(x);
   xi = 0:0.1:10;
   m = -sin(x(1));
   n = -sin(x(length(x)));
   yi = splineDer2(x,y,m,n,xi);
   plot(x,y,'o', xi, yi, '-')
end

function testSplineNaturale()
    x = 0:10;
    y = sin(x);
    xi = 0:0.1:10;
    yi = splineNaturale(x,y,xi);
    plot(x,y,'o', xi, yi, '-')
    x=[-1,0,1];
    y = cos(pi/2 *x);
    xi =-2*pi:0.1:2*pi;
    [yi,coef] = splineNaturale(x,y,xi);
    
    figure(1)
    plot(x,y,'o',xi,yi,'-');
    figure(2)
    plot(x,y,'o');
    coef
end