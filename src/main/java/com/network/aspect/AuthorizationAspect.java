package com.network.aspect;

import com.network.controller.NewsController;
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
public class AuthorizationAspect {

    private Logger logger = LoggerFactory.getLogger(NewsController.class);

    @After("authorizationPointcut()")
    private void aroundAuthorization(JoinPoint joinPoint) {
        User currentUser = (User) joinPoint.getArgs()[0];
        logger.info(String.format("Successfully authorized user with username '%s'", currentUser.getEmail()));
    }

    @Pointcut("@annotation(com.network.aspect.annotation.Authorization)")
    private void authorizationPointcut() {}
}
