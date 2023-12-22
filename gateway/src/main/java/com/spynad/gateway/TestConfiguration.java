package com.spynad.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration

public class TestConfiguration {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        //marshaller.setContextPath("com.spynad.gateway.wsdl");
        marshaller.setPackagesToScan("com.spynad.gateway.wsdl");
        return marshaller;
    }

    @Bean
    public TestClient testClient(Jaxb2Marshaller marshaller) {
        TestClient client = new TestClient();
        client.setDefaultUri("http://localhost:8081/service-1.0-SNAPSHOT/TicketSOAPServiceImpl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
