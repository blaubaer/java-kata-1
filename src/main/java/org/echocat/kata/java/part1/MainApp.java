package org.echocat.kata.java.part1;

import org.echocat.kata.java.part1.model.EmailAddress;
import org.echocat.kata.java.part1.model.Isbn;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@Configuration
public class MainApp implements WebMvcConfigurer {

    public static void main(String[] args) {
        run(MainApp.class, args);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, Isbn.class, Isbn::isbnOf);
        registry.addConverter(String.class, EmailAddress.class, EmailAddress::emailAddressOf);
    }

}
