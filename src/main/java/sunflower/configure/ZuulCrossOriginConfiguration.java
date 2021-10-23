package sunflower.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class ZuulCrossOriginConfiguration {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setMaxAge(3600L); // 表明在3600秒内，不需要再发送预检验请求
//        corsConfiguration.addAllowedOrigin("http://localhost:8081"); // 允许http://localhost:7777域名使用
//        corsConfiguration.addAllowedOrigin("http://localhost:8080"); // 允许http://localhost:7777域名使用
//        corsConfiguration.addAllowedOrigin("http://sunflower.linko.top"); // 允许http://localhost:7777域名使用
//        corsConfiguration.addAllowedOrigin("http://sunflower.linko.top:80"); // 允许http://localhost:7777域名使用
//        corsConfiguration.addAllowedOrigin("http://www.linko.top"); // 允许http://localhost:8080域名使用
//        corsConfiguration.addAllowedOrigin("http://www.linko.top:80"); // 允许http://localhost:8080域名使用
//        corsConfiguration.addAllowedOrigin("http://8.140.110.215"); // 允许http://localhost:8080域名使用
//        corsConfiguration.addAllowedOrigin("http://8.140.110.215:80"); // 允许http://localhost:8080域名使用
        corsConfiguration.addAllowedOrigin("*"); // 允许http://localhost:8080域名使用
        corsConfiguration.addAllowedHeader("*"); // 允许任何头
        corsConfiguration.addAllowedMethod("*"); // 允许任何方法（post、get等）
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 对接口配置跨域设置
        return new CorsFilter(source);
    }
}
