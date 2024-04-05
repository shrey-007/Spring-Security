package com.example.security.config;

import com.example.security.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/*
   UserDetails is an interface which provides user details like its authorities,username,password,and account details
   like locked,expire,enables etc.

   It is different from Employee class in entity package coz voh user ki saari details deta hai but ye authentication
   and authorization vaali details deta hai.

   Since it is an interface so we have to implement methods like username and password kis cheej ko banana hai like isme
   humne email ko username banaya hai, and for that we need to get the user jiksi authentication details nikaalni hai toh
   Employee ka object ki need hai.
   */
public class CustomUser implements UserDetails {

    private Employee employee;

    public CustomUser(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Employee ka role get krke send kr diya
        SimpleGrantedAuthority authority=new SimpleGrantedAuthority(employee.getRole());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        /* spring boot username and password ke basis pr authenticate krta hai but apan chahte hai ki email and password
           ke basis pr kre toh email hi username hai */
        return employee.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
