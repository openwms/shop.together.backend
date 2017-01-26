package io.interface21.shop2gether;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.ameba.annotation.EnableAspects;
import org.ameba.mapping.BeanMapper;
import org.ameba.mapping.DozerMapperImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@SpringBootApplication
@EnableJpaRepositories(considerNestedRepositories = true)
@EnableJpaAuditing
@EnableTransactionManagement
@EnableAspects
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    public
    @Bean
    BeanMapper mapper() {
        return new DozerMapperImpl("META-INF/dozer/bean-mappings.xml");
    }

    public
    @Primary
    @Bean
    com.fasterxml.jackson.databind.ObjectMapper jackson2ObjectMapper() {
        com.fasterxml.jackson.databind.ObjectMapper om = new com.fasterxml.jackson.databind.ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
        om.configure(SerializationFeature.INDENT_OUTPUT, true);
        om.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        om.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        return om;
    }
}
