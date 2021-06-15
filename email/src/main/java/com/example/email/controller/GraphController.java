package com.example.email.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/myapp")
public class GraphController {
    final Logger logger = LoggerFactory.getLogger(GraphController.class);

    @RequestMapping("/graph/me")
    public String getUserFromGraph(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
            throws Throwable {
        logger.info("回调getUserFromGraph:" + httpRequest.getRequestURL().toString());
        return "success";
    }

    @GetMapping("/permission")
    public String permissions(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        logger.info("回调permission:" + httpRequest.getRequestURL().toString());
        return "success";
    }
}
