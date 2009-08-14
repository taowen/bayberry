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
package org.bayberry.core.extension.binder;

import com.google.inject.*;
import com.google.inject.multibindings.Multibinder;
import org.bayberry.core.extension.spi.TestExtension;
import org.bayberry.core.extension.binder.internal.*;
import static org.bayberry.core.extension.binder.internal.Head.Impl.headOf;
import static org.bayberry.core.extension.binder.internal.Tail.Impl.tailOf;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author taowen
 */
public class ExtensionsBinder {

    private final Binder binder;
    private static final Module MODULE = new AbstractModule() {
        protected void configure() {
            Provider<Set<TestExtension>> extensionsProvider = getProvider(Key.get(new TypeLiteral<Set<TestExtension>>() {
            }));
            bind(TestExtension.class).toInstance(new ProvidedExtensions(extensionsProvider));
        }
    };

    public ExtensionsBinder(Binder binder) {
        this.binder = binder;
        binder.install(MODULE);
    }

    public ExtensionsBinder add(Class<? extends TestExtension> extensionClass) {
        Multibinder.newSetBinder(binder, TestExtension.class)
                .addBinding()
                .toInstance(wrapExtension(extensionClass));
        return this;
    }

    public ExtensionsBinder add(Class<? extends TestExtension> firstExtensionClass,
                                Class<? extends TestExtension>... extensionClasses) {
        add(firstExtensionClass);
        Class<? extends TestExtension> lastOne = firstExtensionClass;
        for (Class<? extends TestExtension> extensionClass : extensionClasses) {
            insert(extensionClass).after(lastOne);
        }
        return this;
    }

    public InsertClause insert(Class<? extends TestExtension> extensionClass) {
        return new InsertClause(extensionClass);
    }

    public static ExtensionsBinder extensionsIn(Binder binder) {
        return new ExtensionsBinder(binder);
    }

    private TestExtension wrapExtension(Class<? extends TestExtension> extensionClass) {
        Head before = headOf(extensionClass);
        Multibinder.newSetBinder(binder, TestExtension.class, before);
        Tail after = tailOf(extensionClass);
        Multibinder.newSetBinder(binder, TestExtension.class, after);
        return NestedExtensions.of(
                getExtensions(before),
                new ProvidedExtension(binder.getProvider(extensionClass)),
                getExtensions(after));
    }

    private ProvidedExtensions getExtensions(Annotation annotation) {
        return new ProvidedExtensions(binder.getProvider(Key.get(new TypeLiteral<Set<TestExtension>>() {
        }, annotation)));
    }

    public class InsertClause {

        private final Class<? extends TestExtension> extensionClass;

        public InsertClause(Class<? extends TestExtension> extensionClass) {
            this.extensionClass = extensionClass;
        }

        public ExtensionsBinder before(Class<? extends TestExtension> beforeExtensionClass) {
            Multibinder.newSetBinder(binder, TestExtension.class, headOf(beforeExtensionClass))
                    .addBinding()
                    .toInstance(wrapExtension(extensionClass));
            return ExtensionsBinder.this;
        }

        public ExtensionsBinder after(Class<? extends TestExtension> afterExtensionClass) {
            Multibinder.newSetBinder(binder, TestExtension.class, tailOf(afterExtensionClass))
                    .addBinding()
                    .toInstance(wrapExtension(extensionClass));
            return ExtensionsBinder.this;
        }
    }
}
