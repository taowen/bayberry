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
import org.bayberry.core.api.OverridenWith;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author taowen
 */
public class overriden_with extends _core_module_feature {

    @Test
    public void should_override_my_and_super_configured_with_module() {
        Injector injector = Guice.createInjector(moduleFactory.fromTestCase(new SomeTestCase()));
        Assert.assertEquals("OverridenHello", injector.getInstance(String.class));
        Assert.assertEquals("OverridenWorld", injector.getInstance(Object.class));
    }


    @ConfiguredWith(Module1.class)
    public static class AbstractTestCase {

    }

    @ConfiguredWith(Module2.class)
    @OverridenWith(Module3.class)
    public static class SomeTestCase extends AbstractTestCase {
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

    public static class Module3 extends AbstractModule {

        protected void configure() {
            bind(String.class).toInstance("OverridenHello");
            bind(Object.class).toInstance("OverridenWorld");
        }
    }
}
