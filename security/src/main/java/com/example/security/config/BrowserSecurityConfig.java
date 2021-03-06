package com.example.security.config;

import com.example.security.config.image.ImageCodeFilter;
import com.example.security.config.redis.RedisTokenRepositoryImpl;
import com.example.security.config.sms.SmsAuthenticationConfig;
import com.example.security.config.sms.SmsCodeFilter;
import com.example.security.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//权限控制
@Order(1)
//@EnableAuthorizationServer//认证服务器
/**
 * 在同时定义了认证服务器和资源服务器后，再去使用授权码模式获取令牌可能会遇到
 * Full authentication is required to access this resource 的问题，这时候只要确保认证服务器先于资源服务器配置即可，
 * 比如在在BrowserSecurityConfig上使用@Order(1)，认证服务器的配置类上使用@Order(2)标注，在资源服务器的配置类上使用@Order(3)标注。
 * 实际使用Order没有效果，所以是通过类名来进行排序的，所以将AuthorizationServerConfig改名为MyAuthorizationServerConfig
 *  实际可以看这里:https://blog.csdn.net/ieen_csdn/article/details/86612492
 */
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    public BrowserSecurityConfig() {
        System.out.println("BrowserSecurityConfig------");
    }

    @Autowired
    private MyAuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;
    @Autowired
    private MyAuthenticationSucessHandler authenticationSucessHandler;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFaildHandler;
    @Autowired
    private ImageCodeFilter imageCodeFilter;
    @Autowired
    private SmsCodeFilter smsCodeFilter;
    @Autowired
    private UserDetailService userDetailsService;
    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;
    @Autowired
    private MySessionExpiredStrategy sessionExpiredStrategy;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http
                .addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)// 添加验证码校验过滤器
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)// 添加验证码校验过滤器
                .formLogin() // 表单登录
                // http.httpBasic() // HTTP Basic
//                .loginPage("/login.html")//指定了跳转到登录页面的请求URL
                .loginPage("/authentication/require")//指定了跳转到登录页面的请求URL
                .loginProcessingUrl("/login")//对应登录页面form表单的action="/login"
                .successHandler(authenticationSucessHandler)//登录成功处理
                .failureHandler(authenticationFaildHandler)//处理登录失败
                .and()
                .exceptionHandling()
                .accessDeniedHandler(authenticationAccessDeniedHandler)//权限不足处理器
                .and()
                .logout()
                .logoutUrl("/signout")//退出登录的URL
                .logoutSuccessUrl("/signout/success")
                .deleteCookies("JSESSIONID")//退出成功后删除名称为JSESSIONID的cookie
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository()) // 配置 token 持久化仓库
                .tokenValiditySeconds(3600)//remember 过期时间，单为秒
                .userDetailsService(userDetailsService)//处理自动登录逻辑
                .and()
                .authorizeRequests() // 授权配置
//在未登录的情况下，当用户访问html资源的时候跳转到登录页，否则返回JSON格式数据，状态码为401。
//要实现这个功能我们将loginPage的URL改为/authentication/require，并且在antMatchers方法中加入该URL，让其免拦截
                .antMatchers("/authentication/require",
                        "/css/*",
                        "/favicon.ico",
                        "/code/image",
                        "/code/sms",
                        "/signout/success",
                        "/login.html").permitAll()//表示跳转到登录页面的请求不被拦截
                .anyRequest()  // 所有请求
                .authenticated() // 都需要认证
//                .and()
//                .sessionManagement() // 添加 Session管理器
//                .invalidSessionUrl("/session/invalid") // Session失效后跳转到这个链接
//                .maximumSessions(1)
//                .expiredSessionStrategy(sessionExpiredStrategy)
//                .and()
                .and().csrf().disable()//CSRF攻击防御关了
                .apply(smsAuthenticationConfig);//将短信验证码认证配置加到 Spring Security 中
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        RedisTokenRepositoryImpl redisTokenRepository = new RedisTokenRepositoryImpl();
        return redisTokenRepository;
    }

    //    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
