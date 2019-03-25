package com.network.aspect;

import com.network.controller.RegistrationController;
import com.network.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class RegistrationAspect {

    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @After("registrationPointcut()")
    private void aroundAuthorization(JoinPoint joinPoint) {
        User currentUser = (User) joinPoint.getArgs()[0];
        logger.info(String.format("Successfully registered user with username '%s'", currentUser.getEmail()));
    }

    @Pointcut("@annotation(com.network.aspect.annotation.Registration)")
    private void registrationPointcut() {}
}