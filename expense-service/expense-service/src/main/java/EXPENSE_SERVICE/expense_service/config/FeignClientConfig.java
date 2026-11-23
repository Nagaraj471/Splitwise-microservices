package EXPENSE_SERVICE.expense_service.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class FeignClientConfig {
    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper){
        return new CustomFeignErrorDecoder(objectMapper);
    }
}
