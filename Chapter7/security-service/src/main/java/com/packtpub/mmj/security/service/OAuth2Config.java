package com.packtpub.mmj.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

  static final String CLIENT_ID = "client";
  static final String CLIENT_SECRET = "secret123";
  static final String GRANT_TYPE_PASSWORD = "password";
  static final String AUTHORIZATION_CODE = "authorization_code";
  static final String REFRESH_TOKEN = "refresh_token";
  static final String IMPLICIT = "implicit";
  static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
  static final String SCOPE_API = "apiAccess";
  static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 60 * 60;
  static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6 * 60 * 60;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  @Override
  public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
    configurer
        .inMemory()
        .withClient(CLIENT_ID)
        .secret(CLIENT_SECRET)
        .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT,
            GRANT_TYPE_CLIENT_CREDENTIALS)
        .scopes(SCOPE_API)
        .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
        .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS)
        .redirectUris("http://localhost:8765/");
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.tokenStore(tokenStore)
        .authenticationManager(authenticationManager)
        .reuseRefreshTokens(false);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    // security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").passwordEncoder(passwordEncoder);
    security.passwordEncoder(passwordEncoder);
  }
}



