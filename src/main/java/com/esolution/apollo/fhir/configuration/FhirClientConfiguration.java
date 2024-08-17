package com.esolution.apollo.fhir.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;

@Configuration
public class FhirClientConfiguration {
	@Value("${fhir.server.url}")
    private String serverUrl;

    @Bean
    FhirContext fhirContext() {
        return FhirContext.forR4();
    }

    @Bean
    IGenericClient fhirClient(FhirContext fhirContext) {
        return fhirContext.newRestfulGenericClient(serverUrl);
    }
}
