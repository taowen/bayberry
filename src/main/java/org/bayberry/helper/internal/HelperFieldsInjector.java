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
package org.bayberry.helper.internal;

import com.google.inject.Injector;
import com.google.inject.MembersInjector;
import com.google.inject.Provider;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Set;

/**
 * @author taowen
 */
public class HelperFieldsInjector implements MembersInjector {

    private final Set<Field> helperFields;
    private final HelperInvocationHandler invocationHandler;

    public HelperFieldsInjector(Set<Field> helperFields, Provider<Injector> injectorProvider) {
        this.helperFields = helperFields;
        invocationHandler = new HelperInvocationHandler(injectorProvider.get());
    }

    public void injectMembers(Object o) {
        for (Field helperField : helperFields) {
            try {
                helperField.set(o, createHelper(helperField.getType()));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Object createHelper(Class<?> helperClass) {
        ClassLoader classLoader = helperClass.getClassLoader();
        Object helper = Proxy.newProxyInstance(classLoader, new Class[]{helperClass}, invocationHandler);
        return helper;
    }
}
