package web.pulso.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@ConfigurationProperties("account.cors")
@Data
public class CorsProperties {

	private String allowedOriginPatterns;

}