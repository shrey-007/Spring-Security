package com.example.security.config;

import com.example.security.entity.Employee;
import com.example.security.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
/* UserDetailsService is a interface which is used to get the user from username , it has one method which we have to
   implement according to our application. */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    // Hume password verify krna hai user ka sahi hai ki nhi and password database mai hai toh isliye repository ka object chaiye
    private EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /* spring boot username and password ke basis pr authenticate krta hai but apan chahte hai ki email and password
           ke basis pr kre toh email hi username hai */

        // Employee ko get kiya email(usename) ke through
        Employee employee=employeeRepository.findByEmail(username);

        if (employee==null){
            // means ki esa user hai hi nhi with given email
            throw new UsernameNotFoundException("User not Found");
        }
        else{
            /* return type UserDetails hai but voh inteface hai toh uski implementation clas CustomUserDetails ka object
               banaege and usme employee ko pass kr denge */
            return new CustomUser(employee);
        }
    }
}
