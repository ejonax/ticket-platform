package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                .requestMatchers("/ticket/create").hasAuthority("admin")
                .requestMatchers("/ticket/delete").hasAuthority("admin")
                .requestMatchers("/ticket/edit/**").hasAnyAuthority("admin", "oper")
                .requestMatchers("/ticket").hasAnyAuthority("admin", "oper")
                .requestMatchers(HttpMethod.POST, "/ticket/**").hasAnyAuthority("admin", "oper")
                .requestMatchers("/operatore").hasAnyAuthority("admin", "oper")
                .requestMatchers("/category", "/category/**").hasAuthority("admin")
                .requestMatchers("/nota", "/nota/**").hasAnyAuthority("oper", "admin")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                   //   .loginPage("/login")
                   //   .defaultSuccessUrl("/ticket/index", true)
                .and().logout()
                 // .logoutUrl("/logout") // lascia così
                  //.invalidateHttpSession(true)
                 // .deleteCookies("JSESSIONID")
                //  .permitAll()
                ;
            

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
