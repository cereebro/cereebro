/*
 * Copyright © 2017 the original authors (http://cereebro.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
     *            Eureka instance info.
     */
    public EurekaServiceInstancePublic(InstanceInfo instance) {
        super(instance);
    }

}
