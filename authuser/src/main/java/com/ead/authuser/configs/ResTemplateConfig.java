package com.ead.authuser.configs;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ResTemplateConfig {

    // Metodo que retorna um rest template para fazer a chamada a outro microsservico
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Aqui podemos inserir configuracoes adicionais
        return builder.build();
    }

}
