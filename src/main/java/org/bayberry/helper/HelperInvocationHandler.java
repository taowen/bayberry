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

import com.google.inject.Injector;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author taowen
 */
public class HelperInvocationHandler implements InvocationHandler {

    private final Injector injector;

    public HelperInvocationHandler(Injector injector) {
        this.injector = injector;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = injector.getInstance(method.getDeclaringClass());
        return method.invoke(obj, args);
    }
}
