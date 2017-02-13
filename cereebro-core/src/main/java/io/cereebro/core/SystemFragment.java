package io.cereebro.core;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * Subset of a given System.
 * 
 * @author michaeltecourt
 */
@Data
@AllArgsConstructor(staticName = "of")
public class SystemFragment {

    @NonNull
    private final Set<ComponentRelationships> componentRelationships;

}
