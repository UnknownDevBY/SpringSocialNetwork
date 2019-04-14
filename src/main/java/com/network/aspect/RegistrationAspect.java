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
import java.util.Date;
import java.util.List;

@Slf4j
@Aspect
@Component
public class RegistrationAspect {

    @Autowired
    private Path path;

    @SneakyThrows
    @After("registrationPointcut()")
    private void aroundAuthorization(JoinPoint joinPoint) {
        User user = (User) joinPoint.getArgs()[0];
        String info = String.format("Successfully registered user with username '%s'", user.getEmail());
        log.info(info);
        Files.write(path, List.of(new Date().toString() + " " + info), StandardOpenOption.APPEND);
    }

    @Pointcut("@annotation(com.network.aspect.annotation.Registration)")
    private void registrationPointcut() {}
}
