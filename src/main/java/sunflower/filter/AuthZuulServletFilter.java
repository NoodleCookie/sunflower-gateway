package sunflower.filter;

import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.ZuulServletFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sunflower.dto.AuthResponse;
import sunflower.exception.AuthFailedException;
import sunflower.util.WhiteUrl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
@RefreshScope
public class AuthZuulServletFilter extends ZuulServletFilter {

    private final RestTemplate restTemplate;

    @Value("${auth-server-host}")
    private String authEntryPoint;


    public AuthZuulServletFilter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String authentication = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        String url = ((HttpServletRequest) servletRequest).getRequestURI();
        if (HttpMethod.OPTIONS.name().equals(((HttpServletRequest) servletRequest).getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (WhiteUrl.routeCheck(url)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ResponseEntity<AuthResponse> authResponseResponseEntity = null;
                try {
                    authResponseResponseEntity = restTemplate.postForEntity(authEntryPoint, authentication, AuthResponse.class);
                } catch (Exception e) {
                    throw new AuthFailedException("Authentic failed: " + e.getMessage());
                }

                if (Objects.requireNonNull(authResponseResponseEntity.getBody()).getCode() == 200) {
                    RequestContext ctx = RequestContext.getCurrentContext();
                    ctx.addZuulRequestHeader("server-user", authResponseResponseEntity.getBody().getPrinciple());
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    throw new AuthFailedException("Authentic failed: Authentic is illegal");
                }
            }
        }
    }
}
