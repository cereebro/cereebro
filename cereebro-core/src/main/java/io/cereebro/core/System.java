package io.cereebro.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class System {

    @NonNull
    private final String name;

    @NonNull
    private final Set<ComponentRelationships> componentRelationships;

    public static System of(String name, Collection<ComponentRelationships> rels) {
        return new System(name, new HashSet<>(rels));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }
        System that = (System) o;
        return Objects.equals(this.name, that.name)
                && Objects.equals(this.componentRelationships, that.componentRelationships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), name, componentRelationships);
    }

}
