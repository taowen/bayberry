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

import org.bayberry.core.spi.Extension;

import java.util.List;
import java.lang.reflect.Method;

/**
* @author taowen
*/
public class StubExtension implements Extension {

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
}
