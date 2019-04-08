package com.network.aspect;

import com.network.model.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RegistrationAspect {

    @After("registrationPointcut()")
    private void aroundAuthorization(JoinPoint joinPoint) {
        User currentUser = (User) joinPoint.getArgs()[0];
        log.info(String.format("Successfully registered user with username '%s'", currentUser.getEmail()));
    }

    @Pointcut("@annotation(com.network.aspect.annotation.Registration)")
    private void registrationPointcut() {}
}
