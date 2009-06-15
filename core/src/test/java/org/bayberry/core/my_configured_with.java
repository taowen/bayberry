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
import com.google.inject.Module;
import org.junit.Assert;
import org.junit.Test;
import org.bayberry.core.ModuleFactory;
import org.bayberry.core.api.ConfiguredWith;

/**
 * @author taowen
 */
@ConfiguredWith({my_configured_with.Module1.class, my_configured_with.Module2.class})
public class my_configured_with {

    @Test
    public void should_be_used_to_create_modules() {
        Module module = ModuleFactory.fromTestCase(this);
        Injector injector = Guice.createInjector(module);
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
