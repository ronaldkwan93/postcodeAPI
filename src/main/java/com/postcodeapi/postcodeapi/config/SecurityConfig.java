package com.postcodeapi.postcodeapi.config;


import com.postcodeapi.postcodeapi.jwt.AuthEntryPointJwt;
import com.postcodeapi.postcodeapi.jwt.AuthTokenFilter;
import com.postcodeapi.postcodeapi.jwt.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    private final AuthEntryPointJwt unauthorizedHandler;
    private final JwtUtils jwtUtils;

    private final UserDetailsService userDetailsService;

    public SecurityConfig(
            AuthEntryPointJwt unauthorizedHandler,
            JwtUtils jwtUtils,

            UserDetailsService userDetailsService) {

        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, AuthTokenFilter authTokenFilter) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/register", "/login", "/logout", "/postcode/find", "/signin").permitAll()
                // Authentication filter
                .anyRequest().authenticated()
                );

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http.csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

}

//.csrf(AbstractHttpConfigurer:: disable)
////                .cors(Customizer.withDefaults())
////                .authorizeHttpRequests((requests) -> requests
////                .requestMatchers("/register", "/login", "/logout", "/postcode/find").permitAll()
////                // Authentication filter
////                .anyRequest().authenticated()
////                );
//////                .logout(logout -> logout
//////                        .logoutUrl("/logout")
//////                        .logoutSuccessUrl("/login")
//////                        .invalidateHttpSession(true)
//////                        .clearAuthentication(true)
//////                        .deleteCookies("JSESSIONID")
//////                        .permitAll());