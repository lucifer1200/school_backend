package com.org.apiservices.config;

import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
public class WeakCiphers {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainerCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            ProtocolHandler protocolHandler = connector.getProtocolHandler();
            if (protocolHandler instanceof AbstractHttp11Protocol<?> httpHandler) {
                Arrays.stream(httpHandler.findSslHostConfigs()).forEach(sslHostConfig ->
                        sslHostConfig
                                .setCiphers("TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256"));
                Arrays.stream(httpHandler.findSslHostConfigs())
                        .forEach(sslHostConfig -> sslHostConfig.setProtocols("TLSv1.2"));

                Arrays.stream(httpHandler.findSslHostConfigs())
                        .forEach(sslHostConfig -> sslHostConfig.setHonorCipherOrder(true));

            }

        });
    }
}