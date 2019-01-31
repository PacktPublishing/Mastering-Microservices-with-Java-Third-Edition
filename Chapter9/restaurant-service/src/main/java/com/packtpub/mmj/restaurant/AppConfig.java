package com.packtpub.mmj.restaurant;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer {
 
   @Bean
   public LocaleResolver localeResolver() {
	   AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
       localeResolver.setDefaultLocale(Locale.US);
       return localeResolver;
   }
 
   @Bean
   public LocaleChangeInterceptor localeChangeInterceptor() {
       LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
       localeChangeInterceptor.setParamName("lang");
       return localeChangeInterceptor;
   }
 
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(localeChangeInterceptor());
   }
}