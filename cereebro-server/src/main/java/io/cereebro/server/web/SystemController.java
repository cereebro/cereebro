package io.cereebro.server.web;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import io.cereebro.core.System;
import io.cereebro.server.SystemService;

@Controller
public class SystemController {

    private final SystemService systemService;

    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = Objects.requireNonNull(systemService);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView system() {
        System system = systemService.getSystem();
        ModelAndView mav = new ModelAndView("system", "system", system);
        return mav;
    }

}
