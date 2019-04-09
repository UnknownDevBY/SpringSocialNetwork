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

@Slf4j
@Aspect
@Component
public class AuthorizationAspect {

    @Autowired private Path path;

    @SneakyThrows
    @After("authorizationPointcut()")
    private void aroundAuthorization(JoinPoint joinPoint) {
        User currentUser = (User) joinPoint.getArgs()[0];
        String logStr = String.format("Successfully authorized user with username '%s'", currentUser.getEmail());
        log.info(logStr);
        Files.write(path,
                    Collections.singletonList(logStr),
                    StandardOpenOption.APPEND);
    }

    @Pointcut("@annotation(com.network.aspect.annotation.Authorization)")
    private void authorizationPointcut() {}
}
