package com.sailmi.core.secure.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

//如果没有在gateway中进行Cor过滤，可以使用此过滤器
//@Order
//@Configuration
public class BaseCorsConfiguration {
	//@Bean
	//public CorsFilter corsFilter() {

		//CorsConfiguration config = new CorsConfiguration();
		//System.out.println("add cors!");
		//config.addAllowedOrigin("*");
		//config.setAllowCredentials(true);
		//config.addAllowedMethod("*");
		//config.addAllowedHeader("*");
		//UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		//configSource.registerCorsConfiguration("/**", config);
		//return new CorsFilter(configSource);
	//}
}
