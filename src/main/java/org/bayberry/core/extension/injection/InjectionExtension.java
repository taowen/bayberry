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
package org.bayberry.core.extension.injection;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bayberry.core.extension.spi.TestExtension;

import java.lang.reflect.Method;

/**
 * Call injectMembers before every test starts.
 *
 * @author taowen
 */
public class InjectionExtension implements TestExtension {

    private final Injector injector;

    @Inject
    public InjectionExtension(Injector injector) {
        this.injector = injector;
    }

    public void before(Object testCase, Method testMethod) throws Throwable {
        injector.injectMembers(testCase);
    }

    public void after(Object testCase, Method testMethod) throws Throwable {
    }
}
