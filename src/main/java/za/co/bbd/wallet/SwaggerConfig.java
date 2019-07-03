package za.co.bbd.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@ConditionalOnWebApplication
@ConditionalOnProperty(value = "application.swagger.enabled", havingValue = "true")
@Configuration("wallet.SwaggerConfig")
@EnableSwagger2
public class SwaggerConfig {
    private final ApplicationConfig.Swagger swagger;

    @Autowired
    public SwaggerConfig(
            @Qualifier("wallet.ApplicationConfig") ApplicationConfig applicationConfig) {
        this.swagger = applicationConfig.getSwagger();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/wallet/.*"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(new ApiInfo(
                        this.swagger.getTitle(),
                        this.swagger.getDescription(),
                        this.swagger.getVersion(),
                        "",
                        new springfox.documentation.service.Contact(
                                this.swagger.getContact().getName(),
                                this.swagger.getContact().getUrl(),
                                this.swagger.getContact().getEmail()
                        ),
                        this.swagger.getLicense(),
                        ""
                ));
    }
}