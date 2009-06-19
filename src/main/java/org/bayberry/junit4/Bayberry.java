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
package org.bayberry.junit4;

import org.bayberry.core.spi.Extension;
import org.bayberry.core.ExtensionFactory;
import org.bayberry.extension.injection.InjectionExtension;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;

import com.google.inject.Key;

/**
 * @author taowen
 */
public class Bayberry extends BlockJUnit4ClassRunner {

    public Bayberry(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected Statement withAfters(FrameworkMethod frameworkMethod, final Object testCase, Statement statement) {
        final Extension extension = ExtensionFactory.fromTestCase(testCase, Key.get(InjectionExtension.class));
        final Method testMethod = frameworkMethod.getMethod();
        final Statement next = super.withAfters(frameworkMethod, testCase, statement);
        return new Statement() {
            public void evaluate() throws Throwable {
                try {
                    extension.before(testCase, testMethod);
                    next.evaluate();
                } finally {
                    extension.after(testCase, testMethod);
                }
            }
        };
    }
}