package petfriends.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addCorsMappings(registry);
		registry.addMapping("/**")
		.allowedOrigins("http://k8s-default-petfrien-e22d96b656-549281536.us-west-2.elb.amazonaws.com:80", "http://localhost:3000")
		.allowedMethods(HttpMethod.GET.name(),
				        HttpMethod.POST.name(),
				        HttpMethod.PUT.name(),
				        HttpMethod.DELETE.name()
				       );
	}
	
}
