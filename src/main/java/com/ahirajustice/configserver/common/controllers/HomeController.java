package com.ahirajustice.configserver.common.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("")
@Hidden
public class HomeController {

    @RequestMapping(path = "", method = RequestMethod.GET)
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/config-server/index.html");
    }

}
