package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// This is the main configuration file for security
public class SecuriryConfig {

    public UserDetailsService userDetailsService;

    @Bean
    public UserDetailsService getUserDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    /*
    Its main purpose is to authenticate users against a predefined set of credentials stored in a database or any other
    persistent storage. The "Dao" in DaoAuthenticationProvider stands for "Data Access Object," which signifies that it
    interacts with a data source to retrieve user details for authentication.
    */
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       http
               .csrf().disable()
               /* jab / fire hoga toh use koi bhi access kr skta hai toh vo index page return krega toh index page koi
                  bhi access kr skta hai but profile and about page ke liye vo login maangega */
               .authorizeHttpRequests().requestMatchers("/").permitAll().anyRequest().authenticated().and()
               .formLogin();

       return http.build();
    }

}
