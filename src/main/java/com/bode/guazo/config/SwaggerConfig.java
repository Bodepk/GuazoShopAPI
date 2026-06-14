package com.bode.guazo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI guazoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GuazoAPI")
                        .description("API REST para la gestión de tienda Guazo. Permite administrar productos, clientes y compras.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Bode")
                                .url("https://github.com/Bodepk/GuazoAPI")))
                .servers(List.of(
                        new Server().url("/apig").description("Servidor local")
                ));
    }
}
