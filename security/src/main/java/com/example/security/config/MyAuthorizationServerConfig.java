package com.example.security.config;

import com.example.security.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Order(2)
@EnableAuthorizationServer//认证服务器
/**
 * 在同时定义了认证服务器和资源服务器后，再去使用授权码模式获取令牌可能会遇到
 * Full authentication is required to access this resource 的问题，这时候只要确保认证服务器先于资源服务器配置即可，
 * 比如在在BrowserSecurityConfig上使用@Order(1)，认证服务器的配置类上使用@Order(2)标注，在资源服务器的配置类上使用@Order(3)标注。
 */
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenStore redisTokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private TokenEnhancer tokenEnhancer;

    public MyAuthorizationServerConfig() {
        System.out.println("开始创建对象");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        if (passwordEncoder != null) {
            System.out.println("---");
        }
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(tokenEnhancer);
        enhancers.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancers);

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailService)
                .tokenStore(redisTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(enhancerChain);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder)
                .tokenKeyAccess("isAuthenticated()");// 获取密钥需要身份认证

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        super.configure(clients);
//        TokenEndpoint
        clients.inMemory()
                .withClient("test1")
                .secret("test1111")
                .secret(passwordEncoder.encode("test1111"))
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(864000)
                .scopes("all", "a", "b", "c")
                .redirectUris("http://127.0.0.1:9090/app1/login")
                .autoApprove(true)//自动授权
                .authorizedGrantTypes("authorization_code","password", "refresh_token")//如果没有"refresh_token"返回的字段就没有"refresh_token"
                .and()
                .withClient("test2")
//                .secret("test2222")
                .secret(passwordEncoder.encode("test2222"))
                .autoApprove(true)
                .scopes("all", "a", "b", "c")
                .redirectUris("http://127.0.0.1:9091/app2/login")
                .accessTokenValiditySeconds(7200);
    }


//    private final BaseClientDetails details;

//    private final AuthenticationManager authenticationManager;
//
//    private final TokenStore tokenStore;
//
//    private final AccessTokenConverter tokenConverter;
//
//    private final AuthorizationServerProperties properties;
//
//    public AuthorizationServerConfig(
//                                           AuthenticationConfiguration authenticationConfiguration, ObjectProvider<TokenStore> tokenStore,
//                                           ObjectProvider<AccessTokenConverter> tokenConverter, AuthorizationServerProperties properties)
//            throws Exception {

//        this.details = details;
//        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
//        this.tokenStore = tokenStore.getIfAvailable();
//        this.tokenConverter = tokenConverter.getIfAvailable();
//        this.properties = properties;
//    }

//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        ClientDetailsServiceBuilder<InMemoryClientDetailsServiceBuilder>.ClientBuilder builder = clients.inMemory()
//                .withClient(this.details.getClientId());
//        builder.secret(this.details.getClientSecret())
//                .resourceIds(this.details.getResourceIds().toArray(new String[0]))
//                .authorizedGrantTypes(this.details.getAuthorizedGrantTypes().toArray(new String[0]))
//                .authorities(
//                        AuthorityUtils.authorityListToSet(this.details.getAuthorities()).toArray(new String[0]))
//                .scopes(this.details.getScope().toArray(new String[0]));
//
//        if (this.details.getAutoApproveScopes() != null) {
//            builder.autoApprove(this.details.getAutoApproveScopes().toArray(new String[0]));
//        }
//        if (this.details.getAccessTokenValiditySeconds() != null) {
//            builder.accessTokenValiditySeconds(this.details.getAccessTokenValiditySeconds());
//        }
//        if (this.details.getRefreshTokenValiditySeconds() != null) {
//            builder.refreshTokenValiditySeconds(this.details.getRefreshTokenValiditySeconds());
//        }
//        if (this.details.getRegisteredRedirectUri() != null) {
//            builder.redirectUris(this.details.getRegisteredRedirectUri().toArray(new String[0]));
//        }
//    }

//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        if (this.tokenConverter != null) {
//            endpoints.accessTokenConverter(this.tokenConverter);
//        }
//        if (this.tokenStore != null) {
//            endpoints.tokenStore(this.tokenStore);
//        }
//        if (this.details.getAuthorizedGrantTypes().contains("password")) {
//            endpoints.authenticationManager(this.authenticationManager);
//        }
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.passwordEncoder(NoOpPasswordEncoder.getInstance());
//        if (this.properties.getCheckTokenAccess() != null) {
//            security.checkTokenAccess(this.properties.getCheckTokenAccess());
//        }
//        if (this.properties.getTokenKeyAccess() != null) {
//            security.tokenKeyAccess(this.properties.getTokenKeyAccess());
//        }
//        if (this.properties.getRealm() != null) {
//            security.realm(this.properties.getRealm());
//        }
//    }

}
