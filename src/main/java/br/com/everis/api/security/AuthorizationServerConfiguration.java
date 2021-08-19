package br.com.everis.api.security;

import br.com.everis.api.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private TokenStore tokenStore = new InMemoryTokenStore();

    private final AuthenticationManager authenticationManager;
    private final UserService usuarioService;


    public AuthorizationServerConfiguration(@Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager, UserService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        return tokenServices;
    }

    public void configure (AuthorizationServerEndpointsConfigurer configurer){
        configurer.tokenStore(tokenStore)
                .userDetailsService(usuarioService)
                .authenticationManager(authenticationManager);
    }

    public void configure (ClientDetailsServiceConfigurer clients) throws Exception{
        clients
                .inMemory()
                .withClient("mobile")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("bar", "read", "write")
                .refreshTokenValiditySeconds(42000)
                .accessTokenValiditySeconds(42000)
                .secret("{noop}123");
    }
}
