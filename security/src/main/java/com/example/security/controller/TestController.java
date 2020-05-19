package com.example.security.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class TestController {
    @GetMapping("hello")
    public String hello() {
        return "hello spring security";
    }

    //Spring Security提供的用于缓存请求的对象
    private RequestCache requestCache = new HttpSessionRequestCache();
    //Spring Security提供的用于处理重定向的方法。
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 代码获取了引发跳转的请求，根据请求是否以.html为结尾来对应不同的处理方法。
     * 如果是以.html结尾，那么重定向到登录页面，否则返回”访问的资源需要身份认证！”信息，
     * 并且HTTP状态码为401（HttpStatus.UNAUTHORIZED）。
     * <p>
     * 这样当我们访问http://localhost:8080/hello的时候页面便会跳转到http://localhost:8080/authentication/require，
     * 并且输出”访问的资源需要身份认证！”，当我们访问http://localhost:8080/hello.html的时候，页面将会跳转到登录页面。
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/authentication/require")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            System.out.println("red:" + targetUrl);
            if (StringUtils.isNotBlank(targetUrl) && !StringUtils.endsWithIgnoreCase(targetUrl, "/session/invalid")) {
                redirectStrategy.sendRedirect(request, response, "/login.html");
            }
        }
        return "访问的资源需要身份认证！";
    }

    /**
     * 获取到Authentication对象信息
     *
     * @return
     */
    @GetMapping("/home")
    public Object home() {
        System.out.println("home被调用");
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/session/invalid")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String sessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "session已失效，请重新认证";
    }

    @GetMapping("/signout/success")
    public String signout() {
        return "退出成功，请重新登录";
    }

    @GetMapping("/auth/admin")
    @PreAuthorize("hasAuthority('admin')")
//    @PreAuthorize("hasRole()")
    public String authenticationTest() {
        return "您拥有admin权限，可以查看";
    }
}
