package com.mantovi.MyFlux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //Define essa classe como uma classe de configuração.
@EnableWebSecurity //Ativa o sistema de segurança web do spring boot
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)  throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // = csrf -> csrf.disable() -- Desabilita o csrf
                .authorizeHttpRequests(auth -> auth //define as regras de acessora para as rotas da api
                        .requestMatchers("/users/**").permitAll() //libera rota como publica
                        .anyRequest().authenticated()); //outras rotas, fora as que estão configuradas nesse arquivo, precisam de autenticação
        return http.build(); //Finaliza a configuração e cria o objeto de segurança que o Spring vai usar.
    }
}
