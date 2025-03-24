package ru.myproject.managerapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.myproject.managerapp.client.RestClientProductsRestClientImpl;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientProductsRestClientImpl productsRestClient(
            @Value("${selmag.services.catalog.url:http://localhost:8081}") String catalogBaseUrl) {
        return new RestClientProductsRestClientImpl(RestClient.builder()
                .baseUrl(catalogBaseUrl)
                .build());
    }
}
