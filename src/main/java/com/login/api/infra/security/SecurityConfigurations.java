package com.login.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

   @Autowired
   SecurityFilter securityFilter;


   @Bean
   SecurityFilterChain SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
      return httpSecurity
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()))
            .authorizeHttpRequests(authorize -> authorize
                  .requestMatchers(HttpMethod.POST, "/api/signup").permitAll()
                  .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                  .requestMatchers("/h2/**").permitAll()
                  .requestMatchers(HttpMethod.DELETE, "/api/{id}").hasRole("ADMIN")
                  .requestMatchers(AUT_WHITELIST).permitAll()
                  .anyRequest().authenticated()
                  )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
   }

   private static final String[] AUT_WHITELIST = {
      "/api/v1/auth/**",
      "/v3/api-docs/**",
      "/v3/api-docs.yalm",
      "/swagger-ui/**",
      "/swagger-ui.html"
   };

   @Bean
   AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
         throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
   }

   @Bean
   PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

}
