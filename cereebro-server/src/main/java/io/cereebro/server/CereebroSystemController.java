/*
 * Copyright © 2017 the original authors (http://cereebro.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.cereebro.server;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.cereebro.core.System;
import io.cereebro.core.SystemService;
import io.cereebro.server.graph.d3.wheel.DependencyWheel;
import io.cereebro.server.graph.sigma.Graph;

@Controller
@RequestMapping("${cereebro.server.system.path:/cereebro/system}")
public class CereebroSystemController {

    private final SystemService systemService;

    @Autowired
    public CereebroSystemController(SystemService systemService) {
        this.systemService = Objects.requireNonNull(systemService, "SystemService required");
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView systemPage() {
        System system = systemService.get();
        ModelAndView mav = new ModelAndView("cereebro/system");
        mav.getModelMap().put("system", system);
        Graph graph = Graph.of(system);
        DependencyWheel wheel = DependencyWheel.of(system);
        // Will be serialized to JSON objects by thymeleaf
        mav.addObject("graph", graph);
        mav.addObject("wheel", wheel);
        return mav;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public System systemJson() {
        return systemService.get();
    }

}
