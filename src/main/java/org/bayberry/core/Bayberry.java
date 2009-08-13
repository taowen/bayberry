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

import com.google.inject.Injector;
import org.bayberry.core.internal.InjectorFactory;
import org.bayberry.core.internal.ModuleFactory;
import org.bayberry.core.internal.module.*;

/**
 * @author taowen
 */
public class Bayberry {

    public final static ModuleInstantiator MODULE_INSTANTIATOR = new ModuleInstantiator();
    public final static ModuleFactory MODULE_FACTORY = new ModuleFactory(new ModuleAppenders(
            new FromConfiguredWith(MODULE_INSTANTIATOR),
            new FromProvides(),
            new FromOverridenBy(MODULE_INSTANTIATOR)));
    public final static InjectorFactory INJECTOR_FACTORY = new InjectorFactory(MODULE_FACTORY);

    public static Injector createInjector(Object testCase) {
        return INJECTOR_FACTORY.fromTestCase(testCase);
    }
}
