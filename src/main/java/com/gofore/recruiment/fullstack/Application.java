package com.gofore.recruiment.fullstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@SpringBootApplication
public class Application {
    public static final String PUBLIC_DIR = "public";

    @Bean
    RouterFunction<ServerResponse> publicResourceRouter() {
        return RouterFunctions.resources("/**", new FileSystemResource(PUBLIC_DIR + "/"));
    }

    @Bean
    RouterFunction<ServerResponse> indexRouter() {
        FileSystemResource indexHtml = new FileSystemResource(PUBLIC_DIR + "/index.html");
        return RouterFunctions.route().GET(
                "/",
                request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml)
        ).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
