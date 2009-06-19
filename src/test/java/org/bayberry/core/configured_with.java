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

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bayberry.core.api.ConfiguredWith;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author taowen
 */
@ConfiguredWith({configured_with.Module1.class, configured_with.Module2.class})
public class configured_with {

    @Test
    public void should_be_used_to_create_modules() {
        Injector injector = Guice.createInjector(ModuleFactory.fromTestCase(this));
        Assert.assertEquals("Hello", injector.getInstance(String.class));
        Assert.assertEquals("World", injector.getInstance(Object.class));
    }

    public static class Module1 extends AbstractModule {

        protected void configure() {
            bind(String.class).toInstance("Hello");
        }
    }

    public static class Module2 extends AbstractModule {

        protected void configure() {
            bind(Object.class).toInstance("World");
        }
    }
}
