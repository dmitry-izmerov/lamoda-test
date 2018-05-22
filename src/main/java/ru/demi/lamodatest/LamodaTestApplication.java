package ru.demi.lamodatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class LamodaTestApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LamodaTestApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LamodaTestApplication.class);
    }

}

// change max post size for wildfly:
// bin/jboss-cli.sh /subsystem=undertow/server=default-server/http-listener=default:write-attribute(name=max-post-size, value=107374182400)
