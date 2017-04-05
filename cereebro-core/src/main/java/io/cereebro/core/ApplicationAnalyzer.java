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
package io.cereebro.core;

/**
 * Kind of an internal, application-private Snitch. Identifies the host
 * application component and its relationship. Unlike a Snitch this class does
 * not have an URL.
 * 
 * @author michaeltecourt
 */
public interface ApplicationAnalyzer {

    /**
     * Identify only the host application component and its relationship with
     * others.
     * 
     * @return ComponentRelationships object based on the host application
     *         component (i.e. "this" application).
     */
    ComponentRelationships analyzeApplication();

    /**
     * Tries to give a limited view of the System by analyzing this application,
     * and also trying to deduce other components' relationships when possible.
     * 
     * @return Limited view of the System from the application's POV.
     */
    SystemFragment analyzeSystem();

}
