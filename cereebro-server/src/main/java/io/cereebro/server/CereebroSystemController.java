package io.cereebro.server;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.cereebro.core.System;
import io.cereebro.core.SystemService;
import io.cereebro.server.graph.sigma.Graph;

@Controller
public class CereebroSystemController {

    private final SystemService systemService;

    @Autowired
    public CereebroSystemController(SystemService systemService) {
        this.systemService = Objects.requireNonNull(systemService, "SystemService required");
    }

    @RequestMapping(path = "${cereebro.server.path:/cereebro/system}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView systemPage() {
        System system = systemService.get();
        ModelAndView mav = new ModelAndView("cereebro/system");
        mav.getModelMap().put("system", system);
        Graph graph = Graph.of(system);
        // Will be serialized as a JSON object by thymeleaf
        mav.getModelMap().put("graph", graph);
        return mav;
    }

    @RequestMapping(path = "${cereebro.server.path:/cereebro/system}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public System systemJson() {
        return systemService.get();
    }

}
