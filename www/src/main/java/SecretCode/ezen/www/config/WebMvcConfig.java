package SecretCode.ezen.www.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
//    String uploadPath="file:////Users/kimnoa/Desktop/noa/_myProject/_java/_fileUpload/";
      String uploadPath="file:////C:\\_secretFileUpload/";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/upload/**")
                .addResourceLocations(uploadPath);

    }
}
