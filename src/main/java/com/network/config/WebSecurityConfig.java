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
                .antMatchers("/swagger-ui.**", "/img/**", "/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()

                .authorizeRequests()
                .antMatchers( "/registration/1", "/registration/2", "/login", "/communities/public/**", "/users/**", "/photos/**", "/activation/**", "/albums/**", "/friends/**", "/search/**").permitAll()
                .antMatchers("/log").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginPage("/login")
                .usernameParameter("itech_login")
                .passwordParameter("itech_pass")
                .successForwardUrl("/users")
                .permitAll()
                .and()

                .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()

                .apply(new JwtConfigurer(jwtTokenProvider));
    }

}
