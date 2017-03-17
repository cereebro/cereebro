package org.springframework.cloud.netflix.eureka;

import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;

import com.netflix.appinfo.InstanceInfo;

/**
 * @see EurekaServiceInstance
 * 
 * @author michaeltecourt
 */
public class EurekaServiceInstancePublic extends EurekaServiceInstance {

    /**
     * Because {@link EurekaServiceInstance}'s constructor is package private.
     * 
     * @param instance
     */
    public EurekaServiceInstancePublic(InstanceInfo instance) {
        super(instance);
    }

}
