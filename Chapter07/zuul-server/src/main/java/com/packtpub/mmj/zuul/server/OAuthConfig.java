package com.packtpub.mmj.zuul.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuthConfig extends ResourceServerConfigurerAdapter {

  private static final String RESOURCE_ID = "API";

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/css/**").permitAll()
        .antMatchers("/favicon.ico").permitAll()
        .antMatchers("/js/**").permitAll()
        .antMatchers("/img/**").permitAll()
        .antMatchers("/fonts/**").permitAll()
        .antMatchers("/authapi/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/authapi/auth/login")
        .permitAll()
        .and()
        .logout()
        .logoutUrl("/authapi/auth/logout")
        .permitAll();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId(RESOURCE_ID).stateless(false);
  }
}


