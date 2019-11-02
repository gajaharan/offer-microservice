package com.gajaharan.offer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.google.common.base.Predicates.not;

@EnableSwagger2
@SpringBootApplication
public class OfferMicroserviceApplication {

	@Value("${swagger.api.version}")
	String swaggerApiVersion;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Docket swaggerApiDocumentation(){
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
				.groupName("Api")
				.ignoredParameterTypes(HttpServletRequest.class, HttpServletResponse.class)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.gajaharan.offer"))
				.build()
				.pathMapping("/");
	}

	@Bean
	public Docket swaggerInternalDocumentation(){
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Internal")
				.select()
				.apis(not(RequestHandlerSelectors.basePackage("com.gajaharan.offer")))
				.build()
				.pathMapping("/offers")
				.tags(new Tag("Offer endpoints", "Endpoints to create, retrieve and cancel offers"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Offer Microservice API Documentation")
				.description("API Documentation")
				.version(swaggerApiVersion)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(OfferMicroserviceApplication.class, args);
	}

}
