package io.cereebro.snitch.spring.boot.actuate.endpoint;

import java.net.URI;

import org.springframework.boot.actuate.endpoint.Endpoint;

import io.cereebro.core.Snitch;
import io.cereebro.core.SystemFragment;

/**
 * TODO
 * 
 * @author michaeltecourt
 */
public class SnitchEndpoint implements Endpoint<Object>, Snitch {

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

    @Override
    public URI getLocation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SystemFragment snitch() {
        // TODO Auto-generated method stub
        return null;
    }

}
