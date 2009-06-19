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
import com.google.inject.Key;
import org.bayberry.core.internal.InjectorFactory;
import org.bayberry.core.internal.ModuleFactory;
import org.bayberry.core.spi.Extension;

/**
 * @author taowen
 */
public class ExtensionFactory {

    private final static InjectorFactory injectorFactory = new InjectorFactory(new ModuleFactory());

    public static Extension fromTestCase(Object testCase, Key<? extends Extension> defaultExtensionKey) {
        Injector injector = injectorFactory.fromTestCase(testCase);
        if (injector.getBindings().containsKey(Key.get(Extension.class))) {
            return injector.getInstance(Extension.class);
        }
        return injector.getInstance(defaultExtensionKey);
    }
}
