package io.cereebro.core;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

/**
 * Component that is dependent.
 * <p>
 * Nothing more than a typed Relationship at this point, but leaves room to add
 * specific attributes.
 * </p>
 * 
 * @author michaeltecourt
 */
@ToString(callSuper = true)
public class Consumer extends Relationship {

    /**
     * Component that is dependent.
     * 
     * @param component
     *            Dependant component.
     */
    @JsonCreator
    public Consumer(@JsonProperty("component") Component component) {
        super(component);
    }

    /**
     * Component that is dependent.
     * 
     * @param consumer
     *            Dependant component.
     * @return Consumer
     */
    public static Consumer by(Component consumer) {
        return new Consumer(consumer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), getComponent());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }
        Consumer that = (Consumer) o;
        return Objects.equals(this.getComponent(), that.getComponent());
    }

}
