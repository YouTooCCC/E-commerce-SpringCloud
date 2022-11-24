package com.atweibo.ecommerde.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @Description  安全认证
 * @Author weibo
 * @Data 2022/11/10 8:11
 */
@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
    /*private String adminContextPath;*/
    /*获取上下文路径,一定要getContextPath！！！！！！*/
    private final String adminServerProperties;
    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.adminServerProperties = adminServerProperties.getContextPath();
    }

    /**
     * @return void
     * @Describe:  重写 config方法，配置
     * @author Weibo
     * @date 2022/11/10 8:32
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        /*1.重定向*/
        successHandler.setTargetUrlParameter("redirectTo");
        /*2.默认的url*/
        successHandler.setDefaultTargetUrl(adminServerProperties+"/");
        /*3.对Security配置*/
        http.authorizeRequests()
                /*公开所有的静态资源*/
                .antMatchers(adminServerProperties + "/assets/**").permitAll()
                /*登录页可以公开访问*/
                .antMatchers(adminServerProperties + "/login").permitAll()
                /*其他请求必须要经过认证*/
                .anyRequest().authenticated()
                .and()
                /*配置登录路径*/
                .formLogin().loginPage(adminServerProperties + "/login").successHandler(successHandler)
                .and()
                /*配置注销的路径*/
                .logout().logoutUrl(adminServerProperties + "logout")
                .and()
                /*开启httpBasic支持，其他服务模块注册时需要使用*/
                .httpBasic()
                .and()
                /*开启基于Cookie的csrf保护*/
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                /*忽略这些csrf路径，以便其他模块可以进行注册*/
                .ignoringAntMatchers(
                        adminServerProperties + "/instances",
                        adminServerProperties + "/actuator/**"
                );

    }
}
