package com.network.config;

import com.network.security.JwtConfigurer;
import com.network.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/registration", "/swagger-ui.**", "/img/**", "/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()

                .authorizeRequests()
                .antMatchers( "/", "/login", "/communities/public/**", "/users/**", "/photos/**", "/albums/**").permitAll()
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                .usernameParameter("itech_login")
                .passwordParameter("itech_pass")
                .successForwardUrl("/news")
                .permitAll()
                .and()

                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()

                .rememberMe()
                .rememberMeParameter("itech_remember")
                .tokenValiditySeconds(86400)
                .and()

                .apply(new JwtConfigurer(jwtTokenProvider));
    }

}
