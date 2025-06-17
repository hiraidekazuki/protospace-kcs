package in.tech_camp.protospace.component; 

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ImageUrl {

    @Value("${image.url}")
    private String url;

    public String getUrl() {
        return url;
    }
}
