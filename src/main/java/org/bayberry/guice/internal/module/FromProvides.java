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
package org.bayberry.guice.internal.module;

import com.google.inject.Module;
import com.google.inject.Provides;

import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.Method;

/**
 * @author taowen
 */
public class FromProvides implements ModuleAppender {

    public Module append(Module appendTo, Object testCase, Class<?> clazz) {
        return new CombinedModules(appendTo, fromProvides(testCase, clazz));
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
}
