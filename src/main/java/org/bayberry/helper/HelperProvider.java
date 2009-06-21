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
package org.bayberry.helper;

import com.google.inject.Provider;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.lang.reflect.Proxy;

/**
 * @author taowen
 */
public class HelperProvider<T> implements Provider<T> {

    private final Class<T> helperClass;
    private final ClassLoader classLoader;

    @Inject
    Injector injector;

    public HelperProvider(Class<T> helperClass) {
        this.helperClass = helperClass;
        classLoader = helperClass.getClassLoader();
    }

    public HelperProvider(Class<T> helperClass, ClassLoader classLoader) {
        this.helperClass = helperClass;
        this.classLoader = classLoader;
    }

    public T get() {
        return (T) Proxy.newProxyInstance(classLoader, new Class[]{helperClass}, new HelperInvocationHandler(injector));
    }
}
