package com.soa.labs.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClientsProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	@ConditionalOnMissingBean
	public LoadBalancerClientFactory loadBalancerClientFactory(LoadBalancerClientsProperties properties) {
		return new LoadBalancerClientFactory(properties) {
			@Override
			protected AnnotationConfigApplicationContext createContext(String name) {
				// FIXME: temporary switch classloader to use the correct one when creating the context
				ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
				Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
				AnnotationConfigApplicationContext context = super.createContext(name);
				Thread.currentThread().setContextClassLoader(originalClassLoader);
				return context;
			}
		};
	}

}
