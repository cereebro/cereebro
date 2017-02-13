package io.cereebro.core;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Subset of a given System.
 * 
 * @author michaeltecourt
 */
@Data
@AllArgsConstructor(staticName = "of")
public class SystemFragment {

    private final Set<ComponentRelationships> componentRelationships;

}
