package br.com.unisul.verbo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import br.com.unisul.verbo.helper.GsonCreate;

@Configuration
public class ApplicationConfig {

	@Bean
	public Logger getLog() {
		return LoggerFactory.getLogger("VERBO-API");
	}

	@Bean
	public Gson getGson() {
		return GsonCreate.register();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public MessageSource messageSource() {

		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.addBasenames("org/springframework/security/messages");
		return messageSource;
	}

	@Configuration
	class SecurityConfig extends WebSecurityConfigurerAdapter {

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}
}
