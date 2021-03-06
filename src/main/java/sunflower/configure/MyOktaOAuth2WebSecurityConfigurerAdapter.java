package sunflower.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.net.URI;


@EnableWebSecurity
public class MyOktaOAuth2WebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{

    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler oidcClientInitiatedLogoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        oidcClientInitiatedLogoutSuccessHandler.setPostLogoutRedirectUri(URI.create("http://sunflower.linko.top"));
        return oidcClientInitiatedLogoutSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                // allow anonymous access to the root page
                .antMatchers("/").permitAll()

                // all other requests
                .anyRequest().authenticated()

                // RP-initiated logout
                .and().logout().logoutSuccessHandler(oidcLogoutSuccessHandler())

                // enable OAuth2/OIDC
                .and().oauth2Login();
        http.cors();
    }
}