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
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Guice;
import org.junit.Test;
import org.junit.Assert;

/**
 * @author taowen
 */
public class provides {

    @Test
    public void should_be_used_to_create_module() {
        Module module = ModuleFactory.fromTestCase(this);
        Assert.assertEquals("Hello", Guice.createInjector(module).getInstance(String.class));
    }

    @Provides
    Module myModule() {
        return new AbstractModule() {
            protected void configure() {
                bind(String.class).toInstance("Hello");
            }
        };
    }
}
