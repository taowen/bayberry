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
package org.bayberry.core;

import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.bayberry.core.api.ConfiguredWith;
import org.bayberry.core.api.OverridenWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author taowen
 */
public class ModuleFactory {

    public static Set<Module> fromTestCase(Object testCase) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        collectClasses(classes, testCase.getClass());
        Set<Module> modules = new HashSet<Module>();
        for (Class<?> clazz : classes) {
            modules.addAll(fromConfiguredWith(clazz));
            OverridenWith overridenWith = clazz.getAnnotation(OverridenWith.class);
            if (overridenWith != null) {
                Module overriden = Modules.override(modules).with(newInstances(overridenWith.value()));
                modules.clear();
                modules.add(overriden);
            }
        }
        return modules;
    }

    private static void collectClasses(List<Class<?>> classes, Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        classes.add(0, clazz);
        collectClasses(classes, clazz.getSuperclass());
    }

    private static Set<Module> fromConfiguredWith(Class<?> clazz) {
        ConfiguredWith configuredWith = clazz.getAnnotation(ConfiguredWith.class);
        if (configuredWith == null) {
            return new HashSet<Module>();
        }
        Class<? extends Module>[] moduleClasses = configuredWith.value();
        return newInstances(moduleClasses);
    }

    private static Set<Module> newInstances(Class<? extends Module>[] moduleClasses) {
        Set<Module> modules = new HashSet<Module>();
        for (Class<? extends Module> moduleClass : moduleClasses) {
            modules.add(newInstance(moduleClass));
        }
        return modules;
    }

    private static Module newInstance(Class<? extends Module> moduleClass) {
        try {
            return moduleClass.newInstance();
        } catch (Throwable e) {
            throw new IllegalArgumentException("can not instantiate module " + moduleClass, e);
        }
    }
}
