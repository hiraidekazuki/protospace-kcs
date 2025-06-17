package in.tech_camp.protospace;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /uploads/** に対して静的リソースを提供する
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/C:/upload_images/");  // アップロードされた画像が保存されるディレクトリのパス
    }
}
