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
package org.bayberry.core.container.internal.module;

import com.google.inject.Module;

import java.util.Set;
import java.util.HashSet;

import org.bayberry.core.container.api.ConfiguredWith;
import org.bayberry.core.container.spi.ModuleAppender;
import org.bayberry.core.container.spi.ModuleInstantiator;

/**
 * @author taowen
 */
public class FromConfiguredWith implements ModuleAppender {

    private final ModuleInstantiator instantiator;

    public FromConfiguredWith(ModuleInstantiator instantiator) {
        this.instantiator = instantiator;
    }

    public Module append(Module appendTo, Object testCase, Class<?> clazz) {
        return new CombinedModules(appendTo, fromConfiguredWith(clazz));
    }

    private Set<Module> fromConfiguredWith(Class<?> clazz) {
        ConfiguredWith configuredWith = clazz.getAnnotation(ConfiguredWith.class);
        if (configuredWith == null) {
            return new HashSet<Module>();
        }
        Class<? extends Module>[] moduleClasses = configuredWith.value();
        return instantiator.newInstances(moduleClasses);
    }
}
