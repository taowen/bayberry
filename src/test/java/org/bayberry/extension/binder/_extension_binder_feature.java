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
package org.bayberry.extension.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Guice;
import org.bayberry.core.spi.Extension;
import org.junit.Before;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author taowen
 */
public class _extension_binder_feature {

    protected ArrayList<Extension> called;
    protected Extension1 extension1;
    protected Extension2 extension2;
    protected Extension3 extension3;

    @Before
    public void create_extensions() {
        called = new ArrayList<Extension>();
        extension1 = new Extension1(called);
        extension2 = new Extension2(called);
        extension3 = new Extension3(called);
    }


    public static class StubExtension implements Extension {
        private final List<Extension> called;

        public StubExtension(List<Extension> called) {
            this.called = called;
        }

        public void before(Object testCase, Method testMethod) throws Throwable {
            called.add(this);
        }

        public void after(Object testCase, Method testMethod) throws Throwable {
            called.add(this);
        }
    }

    public static class Extension1 extends StubExtension {

        public Extension1(List<Extension> called) {
            super(called);
        }
    }

    public static class Extension2 extends StubExtension {

        public Extension2(List<Extension> called) {
            super(called);
        }
    }

    public static class Extension3 extends StubExtension {

        public Extension3(List<Extension> called) {
            super(called);
        }
    }

    protected void call(Module module) throws Throwable {
        Guice.createInjector(module, extensionsModule()).getInstance(Extension.class).before(null, null);
    }

    private Module extensionsModule() {

        return new AbstractModule() {
            protected void configure() {
                bind(Extension1.class).toInstance(extension1);
                bind(Extension2.class).toInstance(extension2);
                bind(Extension3.class).toInstance(extension3);
            }
        };

    }
}