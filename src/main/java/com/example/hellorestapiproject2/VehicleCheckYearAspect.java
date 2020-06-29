package com.example.hellorestapiproject2;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class VehicleCheckYearAspect {
    @Pointcut("execution(public * com.example..*(Vehicle))")
    public void publicMethod() {}


    @Around( "publicMethod() && @annotation(CheckYear)" )
    public void checkYear( final ProceedingJoinPoint joinPoint) throws Throwable {
        Vehicle v = (Vehicle) joinPoint.getArgs()[0];
        System.out.println("Checking " + v);
        if(v.getYear()<1990)
            System.out.println("Vehicle too old");
    }

}
