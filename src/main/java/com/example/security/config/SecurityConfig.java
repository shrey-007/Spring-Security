package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// to enable method leve security, so that we can directly configure who can access which page(by using @PreAuthorize
// annotation in controller)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    @Bean
    // this class is provided by spring itself
    /* we are using this to add in memory user, toh jo spring khud ka auto generated password deta hai usse login nhi
       hoga, isse hoga */
    public UserDetailsService userDetailsService(){
        UserDetails user=User.withUsername("user").password(passwordEncoder().encode("1234")).roles("USER").build();
        UserDetails admin=User.withUsername("admin").password(passwordEncoder().encode("7777")).roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user,admin);
    }

    @Bean
    // this class is provided by spring
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    /*
    http.csrf().disable(): This disables CSRF protection. CSRF (Cross-Site Request Forgery) protection is a security feature that helps prevent unauthorized actions on behalf of a user.
    authorizeHttpRequests(): This starts configuring authorization rules for HTTP requests.
    anyRequest().authenticated(): This configures that any request coming to the application must be authenticated.
    and(): This method is used to chain multiple security configurations.
    formLogin(): This enables form-based authentication.
     */
    // This is giving error but run krne pr run ho rha hai project and is working completely fine
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable().authorizeHttpRequests().anyRequest().authenticated().and().formLogin();
        return http.build();
    }


}
