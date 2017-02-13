package io.cereebro.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Component {

    @NonNull
    private final String name;

    @NonNull
    private final String type;

    public static Component of(String name, String type) {
        return new Component(name, type);
    }

}
