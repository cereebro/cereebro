package io.cereebro.spring.boot.autoconfigure;

import io.cereebro.core.Consumer;
import lombok.Data;

@Data
public final class ConsumerProperties {

    private ComponentProperties component;

    public Consumer toConsumer() {
        return Consumer.by(component.toComponent());
    }

}
