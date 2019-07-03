package za.co.bbd.wallet;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("wallet.ApplicationConfig")
@ConfigurationProperties("application")
@Getter
@Setter
public class ApplicationConfig {
    private Swagger swagger;

    @Getter
    @Setter
    public static class Swagger {
        private String regexPathExclusion;
        private String version;
        private String title;
        private String description;
        private String license;
        private Contact contact;

        @Getter
        @Setter
        public static class Contact {
            private String name;
            private String url;
            private String email;
        }
    }
}
