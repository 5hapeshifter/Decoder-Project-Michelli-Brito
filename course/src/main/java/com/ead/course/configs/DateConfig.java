package com.ead.course.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.format.DateTimeFormatter;

// Configuracao global do formato da data e hora
//@Configuration - Não estamos utilizando esse padrao por enquanto por dar conflito com a ResolverConfig que já alguns padroes implementados
public class DateConfig {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZAR = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT));

    // Método criado para o spring sobrescrever o método default de padrão de hora
    @Bean // Anotacao para o spring gerenciar
    @Primary // Destaca um bean especifico para ser executado se existir outros
    public ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LOCAL_DATETIME_SERIALIZAR);
        return new ObjectMapper().registerModule(module);
    }
}
