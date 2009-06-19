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
package org.bayberry.core.internal;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.internal.Function;
import com.google.inject.internal.MapMaker;
import com.google.inject.internal.Nullable;
import org.bayberry.core.api.ConfiguredWith;
import org.bayberry.core.api.OverridenWith;
import org.bayberry.core.internal.SetOfModules;
import org.bayberry.core.internal.OverridenModule;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * @author taowen
 */
public class ModuleFactory {

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

    private final static Module DUMMY_MODULE = new Module() {
        public void configure(Binder binder) {
        }
    };

    public Module fromTestCase(Object testCase) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        collectClasses(classes, testCase.getClass());
        Module module = DUMMY_MODULE;
        for (Class<?> clazz : classes) {
            Set<Module> modules = fromConfiguredWith(clazz);
            modules.addAll(fromProvides(testCase, clazz));
            module = new SetOfModules(module, modules);
            OverridenWith overridenWith = clazz.getAnnotation(OverridenWith.class);
            if (overridenWith != null) {
                module = new OverridenModule(module, newInstances(overridenWith.value()));
            }
        }
        return module;
    }

    private Set<Module> fromProvides(Object testCase, Class<?> clazz) {
        Set<Module> modules = new HashSet<Module>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Provides.class)) {
                continue;
            }
            if (!method.getReturnType().equals(Module.class)) {
                continue;
            }
            if (method.getParameterTypes().length != 0) {
                continue;
            }
            method.setAccessible(true);
            try {
                modules.add((Module) method.invoke(testCase));
            } catch (Exception e) {
                throw new RuntimeException("failed to get module from method " + method, e);
            }
        }
        return modules;
    }

    private void collectClasses(List<Class<?>> classes, Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        classes.add(0, clazz);
        collectClasses(classes, clazz.getSuperclass());
    }

    private Set<Module> fromConfiguredWith(Class<?> clazz) {
        ConfiguredWith configuredWith = clazz.getAnnotation(ConfiguredWith.class);
        if (configuredWith == null) {
            return new HashSet<Module>();
        }
        Class<? extends Module>[] moduleClasses = configuredWith.value();
        return newInstances(moduleClasses);
    }

    private Set<Module> newInstances(Class<? extends Module>[] moduleClasses) {
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
