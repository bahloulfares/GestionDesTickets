package com.csys.template.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.http.HttpMethod;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.context.request.RequestContextHolder;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
@EnableWebSecurity
@Profile({"dev", "test"})
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableRedisHttpSession
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   private final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors();
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll();
        http.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());

        http.formLogin().successHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            response.setContentType("text/html");
            String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

            response.getWriter().print(sessionId);

        });
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.requestCache()
                .requestCache(new NullRequestCache());
        http.sessionManagement().sessionFixation().none();
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }
}
