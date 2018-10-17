package org.lamb.lambframework.core.config;

import org.lamb.lambframework.core.security.LambServerAccessDeniedHandler;
import org.lamb.lambframework.core.security.LambServerAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.annotation.Resource;


/**
 * @program: decisionsupportsystem
 * @description: spring权限框架配置
 * @author: Mr.WangGang
 * @create: 2018-08-29 17:08
 **/

public abstract class LambSpringSecurityConfig {

    @Resource
    private LambServerAccessDeniedHandler lambServerAccessDeniedHandler;

    @Resource
    private LambServerAuthenticationEntryPoint lambServerAuthenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        http.httpBasic().disable();
        http.formLogin().disable();
        http.csrf().disable();
        http.logout().disable();
        http.headers().disable();
        http.requestCache().disable();
        http.exceptionHandling().authenticationEntryPoint(lambServerAuthenticationEntryPoint);
        http.exceptionHandling().accessDeniedHandler(lambServerAccessDeniedHandler);
        http.authorizeExchange().pathMatchers("/css/**","/image/*").permitAll();
        http.authorizeExchange().pathMatchers("/v2/api-docs").permitAll();
        http.authorizeExchange().pathMatchers("/v2/api-docs/**").permitAll();
        http.authorizeExchange().pathMatchers("/swagger-ui.html").permitAll();
        http.authorizeExchange().pathMatchers("/swagger-ui.html/**").permitAll();
        http.authorizeExchange().pathMatchers("/swagger-resources").permitAll();
        http.authorizeExchange().pathMatchers("/swagger-resources/**").permitAll();
        http.authorizeExchange().pathMatchers("/configuration/ui").permitAll();
        http.authorizeExchange().pathMatchers("/configuration/security").permitAll();
        http.authorizeExchange().pathMatchers("/webjars/**").permitAll();
        strategy(http.authorizeExchange());
        http.authorizeExchange().anyExchange().permitAll();
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public abstract ServerHttpSecurity.AuthorizeExchangeSpec strategy(ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec);


}
