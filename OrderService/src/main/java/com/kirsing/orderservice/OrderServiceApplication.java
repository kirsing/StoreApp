package com.kirsing.orderservice;

import com.kirsing.orderservice.external.interceptors.RestTemplateInterceptor;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Order Microservice REST API Documentation",
				description = "StoreApp Order microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Kirill Yudaev",
						email = "kirsing98@gmail.com"
				),
				license = @License(
						name = "Apache 2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "StoreAPP Order microservice REST API Documentation"
		)
)
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Autowired
	private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		RestTemplate restTemplate
				= new RestTemplate();
		restTemplate.setInterceptors(
				Arrays.asList(
						new RestTemplateInterceptor(
								clientManager(clientRegistrationRepository,
										oAuth2AuthorizedClientRepository)
						)
				)
		);

		return restTemplate;


	}

	@Bean
	public OAuth2AuthorizedClientManager clientManager(ClientRegistrationRepository clientRegistrationRepository,
													   OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository)
	{
		OAuth2AuthorizedClientProvider oAuth2AuthorizedClientProvider =
				OAuth2AuthorizedClientProviderBuilder
						.builder()
						.clientCredentials()
						.build();

		DefaultOAuth2AuthorizedClientManager oAuth2AuthorizedClientManager =
				new DefaultOAuth2AuthorizedClientManager(
						clientRegistrationRepository, oAuth2AuthorizedClientRepository
				);

		oAuth2AuthorizedClientManager.setAuthorizedClientProvider(
				oAuth2AuthorizedClientProvider);

		return oAuth2AuthorizedClientManager;
	}

}
