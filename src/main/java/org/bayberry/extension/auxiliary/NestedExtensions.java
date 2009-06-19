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
package org.bayberry.extension.auxiliary;

import org.bayberry.core.spi.Extension;

import java.lang.reflect.Method;

/**
 * @author taowen
 */
public class NestedExtensions implements Extension {

    private final Extension first;
    private final Extension last;

    public NestedExtensions(Extension first, Extension last) {
        this.first = first;
        this.last = last;
    }

    public void before(Object testCase, Method testMethod) throws Throwable {
        try {
            first.before(testCase, testMethod);
        } finally {
            last.before(testCase, testMethod);
        }
    }

    public void after(Object testCase, Method testMethod) throws Throwable {
        try {
            last.after(testCase, testMethod);
        } finally {
            first.after(testCase, testMethod);
        }
    }

    public static Extension of(Extension... extensions) {
        Extension nested = new Extension() {

            public void before(Object testCase, Method testMethod) throws Throwable {
            }

            public void after(Object testCase, Method testMethod) throws Throwable {
            }
        };
        for (Extension extension : extensions) {
            nested = new NestedExtensions(nested, extension);
        }
        return nested;
    }
}
