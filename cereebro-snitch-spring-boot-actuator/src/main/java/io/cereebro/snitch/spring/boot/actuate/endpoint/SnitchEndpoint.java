package io.cereebro.snitch.spring.boot.actuate.endpoint;

import org.springframework.boot.actuate.endpoint.Endpoint;

/**
 * TODO
 * 
 * @author michaeltecourt
 */
public class SnitchEndpoint implements Endpoint<Object> {

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSensitive() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public Object invoke() {
        // TODO Auto-generated method stub
        return null;
    }

}
