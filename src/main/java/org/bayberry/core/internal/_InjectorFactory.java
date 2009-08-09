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

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.internal.Function;
import com.google.inject.internal.MapMaker;
import com.google.inject.internal.Nullable;
import org.bayberry.core.internal._ModuleFactory;

import java.util.concurrent.ConcurrentMap;

/**
 * @author taowen
 */
public class _InjectorFactory {

    private final ConcurrentMap<Module, Injector> injectors = new MapMaker()
            .softValues()
            .makeComputingMap(new Function<Module, Injector>() {
                public Injector apply(@Nullable Module module) {
                    return Guice.createInjector(module);
                }
            });

    private final _ModuleFactory moduleFactory;

    public _InjectorFactory(_ModuleFactory moduleFactory) {
        this.moduleFactory = moduleFactory;
    }

    public Injector fromTestCase(Object testCase) {
        return injectors.get(moduleFactory.fromTestCase(testCase));
    }
}