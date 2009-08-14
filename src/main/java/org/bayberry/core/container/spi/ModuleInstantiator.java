/*
 * Copyright 2009 Wen Tao. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.bayberry.core.container.spi;

import com.google.inject.Module;
import com.google.inject.internal.Function;
import com.google.inject.internal.MapMaker;
import com.google.inject.internal.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * Used by module appender to create module instance
 * from module class. For one class there is only
 * one instance being created. But if the equals
 * or hashcode of module being overriden, the behavior
 * could change.
 *
 * @author taowen
 */
public class ModuleInstantiator {

    private final ConcurrentMap<Class<? extends Module>, Module> moduleInstances = new MapMaker()
            .makeComputingMap(new Function<Class<? extends Module>, Module>() {
                public Module apply(@Nullable Class<? extends Module> moduleClass) {
                    try {
                        return moduleClass.newInstance();
                    } catch (Throwable e) {
                        throw new RuntimeException("can not instantiate module " + moduleClass, e);
                    }
                }
            });


    public Set<Module> newInstances(Class<? extends Module>[] moduleClasses) {
        Set<Module> modules = new HashSet<Module>();
        for (Class<? extends Module> moduleClass : moduleClasses) {
            modules.add(newInstance(moduleClass));
        }
        return modules;
    }

    private Module newInstance(Class<? extends Module> moduleClass) {
        return moduleInstances.get(moduleClass);
    }
}
