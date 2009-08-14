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
package org.bayberry.core.test;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.bayberry.core.test.internal.ExtensionsBinder;
import org.bayberry.core.test.spi.TestExtension;

/**
 * Extend from this class to bind extensions in multiple
 * modules and with order specified. Example usage:
 * <pre>
 * install(new TestExtensionModule(){
 * &nbsp;&nbsp;protected void configure() {
 * &nbsp;&nbsp;&nbsp;&nbsp;add(SomeExtension.class);
 * &nbsp;&nbsp;}
 * });
 * </pre>
 *
 * @author taowen
 */
public abstract class TestExtensionModule implements Module {

    private ExtensionsBinder binder;

    public void configure(Binder binder) {
        this.binder = new ExtensionsBinder(binder);
        configure();
    }

    protected abstract void configure();

    protected void add(Class<? extends TestExtension> extensionClass) {
        binder.add(extensionClass);
    }

    protected void add(Class<? extends TestExtension> firstExtensionClass,
                       Class<? extends TestExtension>... extensionClasses) {
        binder.add(firstExtensionClass, extensionClasses);
    }

    protected InsertClause insert(Class<? extends TestExtension> extensionClass) {
        return new InsertClause(extensionClass);
    }

    protected class InsertClause {

        private final Class<? extends TestExtension> extensionClass;

        public InsertClause(Class<? extends TestExtension> extensionClass) {
            this.extensionClass = extensionClass;
        }

        public void before(Class<? extends TestExtension> beforeExtensionClass) {
            binder.insert(extensionClass, beforeExtensionClass);
        }

        public void after(Class<? extends TestExtension> afterExtensionClass) {
            binder.append(afterExtensionClass, extensionClass);
        }
    }
}
