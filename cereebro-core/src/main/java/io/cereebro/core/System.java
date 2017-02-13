package io.cereebro.core;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class System {

    final String name;

    final Set<ComponentRelationships> componentRelationships;

}
