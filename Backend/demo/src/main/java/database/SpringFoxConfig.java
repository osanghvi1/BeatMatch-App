package database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("database"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Beatmatch_server")
                .description("Welcome to the backend of the Coms 309 project. Here is the API Documentation")
                .contact(new Contact("Lawson Port", "https://git.las.iastate.edu/cs309/2024fall/3_swarna_2", "lport@iastate.edu"))
                .contact(new Contact("Om Sanghvi", "https://git.las.iastate.edu/cs309/2024fall/3_swarna_2", "omsanghvi@iastate.edu"))
                .version("0.0.1")
                .build();

    }
}
