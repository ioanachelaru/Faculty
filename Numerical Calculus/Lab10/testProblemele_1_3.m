function  test()
    
    dreptunghi = dreptunghi(@sin,0,pi,10)
    
    trapez = trapez(@sin,0,pi,10)
    
    simpson = simpson(@sin,0,pi,10)
    
    adaptive_quad_dreptunghi = problema_3(@sin,0,pi,eps,@dreptunghi)
    
    adaptive_quad_simpson = problema_3(@sin,0,pi,eps,@simpson)
    
    adaptive_quad_trapez = problema_3(@sin,0,pi,eps,@trapez)
end
