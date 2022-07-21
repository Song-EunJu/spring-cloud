package com.example.userservice.security;

import com.example.userservice.service.UserService;
import org.apache.catalina.User;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private Environment env;
    private UserService userService;

    private BCryptPasswordEncoder bcryptPasswordEncoder;

    public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bcryptPasswordEncoder){
        this.env = env;
        this.userService = userService;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // 권한
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .and()
                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception{
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), userService, env);
//        authenticationFilter.setAuthenticationManager(authenticationManager());
        // authenticationManager :AuthenticationManager는 ProvierManager를 구현한 클래스로써,
        // 인자로 전달받은 유저에 대한 인증 정보를 담고 있으며,
        // 해당 인증 정보가 유효할 경우 UserDetailsService에서
        // 적절한 Principal을 가지고 있는 Authentication 객체를
        // 반환해 주는 역할을 하는 인증 공급자(Provider)
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 인증
        auth.userDetailsService(userService).passwordEncoder(bcryptPasswordEncoder);
                // 사용자가 가진 데이터로 로그인 처리
    }
}
