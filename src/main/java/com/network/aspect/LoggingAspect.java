package com.network.aspect;

import com.network.model.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Autowired private Path path;

    @After("authorizationPointcut()")
    private void aroundAuthorization(JoinPoint joinPoint) {
        action(joinPoint, "authorized");
    }

    @After("registrationPointcut()")
    private void aroundRegistration(JoinPoint joinPoint) {
        action(joinPoint, "registered");
    }

    @SneakyThrows
    private void action(JoinPoint joinPoint, String action) {
        User user = (User) joinPoint.getArgs()[0];
        String info = String.format("Successfully %s user with username '%s'", action, user.getEmail());
        log.info(info);
        Files.write(path, Collections.singletonList(new Date().toString() + " " + info), StandardOpenOption.APPEND);
    }

    @Pointcut("@annotation(com.network.aspect.annotation.Authorization)")
    private void authorizationPointcut() {}

    @Pointcut("@annotation(com.network.aspect.annotation.Registration)")
    private void registrationPointcut() {}
}
