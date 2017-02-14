package io.cereebro.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(staticName = "of")
public class Component {

    @NonNull
    private final String name;

    @NonNull
    private final String type;

}
