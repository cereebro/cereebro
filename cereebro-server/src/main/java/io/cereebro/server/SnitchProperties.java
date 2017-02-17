package io.cereebro.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;

import lombok.Data;

@Data
public final class SnitchProperties {

    /**
     * Simple snitch resources. Spring does most of the work here.
     */
    private List<Resource> resources = new ArrayList<>();

}
