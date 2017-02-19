package io.cereebro.server.web;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import io.cereebro.core.System;
import io.cereebro.core.SystemService;
import io.cereebro.server.graph.sigma.Graph;

@Controller
public class SystemController {

    private final SystemService systemService;

    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = Objects.requireNonNull(systemService, "SystemService required");
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        System system = systemService.get();
        ModelAndView mav = new ModelAndView("system");
        mav.getModelMap().put("system", system);
        Graph graph = Graph.of(system);
        // Will be serialized as a JSON object by thymeleaf
        mav.getModelMap().put("graph", graph);
        return mav;
    }

}
