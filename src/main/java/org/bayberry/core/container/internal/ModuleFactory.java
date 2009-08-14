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
package org.bayberry.core.container.internal;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.bayberry.core.container.spi.ModuleAppender;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taowen
 */
public class ModuleFactory {

    private final static Module DUMMY_MODULE = new Module() {
        public void configure(Binder binder) {
        }
    };

    private final ModuleAppender moduleAppender;

    public ModuleFactory(ModuleAppender moduleAppender) {
        this.moduleAppender = moduleAppender;
    }

    public Module fromTestCase(Object testCase) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        collectClasses(classes, testCase.getClass());
        Module module = DUMMY_MODULE;
        for (Class<?> clazz : classes) {
            module = moduleAppender.append(module, testCase, clazz);
        }
        return module;
    }

    private void collectClasses(List<Class<?>> classes, Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        classes.add(0, clazz);
        collectClasses(classes, clazz.getSuperclass());
    }
}
