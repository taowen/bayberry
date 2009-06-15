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
import org.bayberry.core.api.ConfiguredWith;
import org.bayberry.core.api.OverridenWith;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author taowen
 */

public class module_indentity {

    @Test
    public void should_be_always_same_for_same_config() {
        Assert.assertEquals(
                ModuleFactory.fromTestCase(new A2()),
                ModuleFactory.fromTestCase(new B2()));
    }

    @ConfiguredWith(Module1.class)
    public static class A1 {

    }

    @ConfiguredWith(Module2.class)
    @OverridenWith(Module3.class)
    public static class A2 extends A1 {

    }

    @ConfiguredWith(Module2.class)
    public static class B1 {

    }

    @ConfiguredWith(Module1.class)
    @OverridenWith(Module3.class)
    public static class B2 extends B1 {

    }

    public static class Module1 extends AbstractModule {

        protected void configure() {
        }
    }

    public static class Module2 extends AbstractModule {

        protected void configure() {

        }
    }

    public static class Module3 extends AbstractModule {

        protected void configure() {

        }
    }
}
