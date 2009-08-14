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
package org.bayberry.core.test.binder.internal;

import org.bayberry.core.test.spi.TestExtension;
import org.bayberry.core.test.binder.internal.NestedExtensions;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author taowen
 */
public class nest_three_extensions {

    @Test
    public void should_be_invoked_one_by_one() throws Throwable {
        InvokedAtAwareExtension extension1 = new InvokedAtAwareExtension();
        InvokedAtAwareExtension extension2 = new InvokedAtAwareExtension();
        InvokedAtAwareExtension extension3 = new InvokedAtAwareExtension();
        TestExtension extension = NestedExtensions.of(extension1, extension2, extension3);
        extension.before(null, null);
        extension.after(null, null);
        Assert.assertTrue(extension1.beforeInvokedAt < extension2.beforeInvokedAt);
        Assert.assertTrue(extension2.beforeInvokedAt < extension3.beforeInvokedAt);
        Assert.assertTrue(extension1.afterInvokedAt > extension2.afterInvokedAt);
        Assert.assertTrue(extension2.afterInvokedAt > extension3.afterInvokedAt);
    }

    private static class InvokedAtAwareExtension implements TestExtension {

        public Long beforeInvokedAt;
        public Long afterInvokedAt;

        public void before(Object testCase, Method testMethod) throws Throwable {
            beforeInvokedAt = System.nanoTime();
        }

        public void after(Object testCase, Method testMethod) throws Throwable {
            afterInvokedAt = System.nanoTime();
        }
    }
}
