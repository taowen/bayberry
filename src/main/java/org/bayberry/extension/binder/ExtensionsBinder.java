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

import com.google.inject.*;
import com.google.inject.multibindings.Multibinder;
import org.bayberry.core.spi.Extension;
import org.bayberry.extension.binder.internal.*;
import static org.bayberry.extension.binder.internal.Head.Impl.headOf;
import static org.bayberry.extension.binder.internal.Tail.Impl.tailOf;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author taowen
 */
public class ExtensionsBinder {

    private final Binder binder;
    private static final Module MODULE = new AbstractModule() {
        protected void configure() {
            Provider<Set<Extension>> extensionsProvider = getProvider(Key.get(new TypeLiteral<Set<Extension>>() {
            }));
            bind(Extension.class).toInstance(new ProvidedExtensions(extensionsProvider));
        }
    };

    public ExtensionsBinder(Binder binder) {
        this.binder = binder;
        binder.install(MODULE);
    }

    public ExtensionsBinder add(Class<? extends Extension> extensionClass) {
        Multibinder.newSetBinder(binder, Extension.class)
                .addBinding()
                .toInstance(wrapExtension(extensionClass));
        return this;
    }

    public ExtensionsBinder add(Class<? extends Extension> firstExtensionClass,
                                Class<? extends Extension>... extensionClasses) {
        add(firstExtensionClass);
        Class<? extends Extension> lastOne = firstExtensionClass;
        for (Class<? extends Extension> extensionClass : extensionClasses) {
            insert(extensionClass).after(lastOne);
        }
        return this;
    }

    public InsertClause insert(Class<? extends Extension> extensionClass) {
        return new InsertClause(extensionClass);
    }

    public static ExtensionsBinder extensionsIn(Binder binder) {
        return new ExtensionsBinder(binder);
    }

    private Extension wrapExtension(Class<? extends Extension> extensionClass) {
        Head before = headOf(extensionClass);
        Multibinder.newSetBinder(binder, Extension.class, before);
        Tail after = tailOf(extensionClass);
        Multibinder.newSetBinder(binder, Extension.class, after);
        return NestedExtensions.of(
                getExtensions(before),
                new ProvidedExtension(binder.getProvider(extensionClass)),
                getExtensions(after));
    }

    private ProvidedExtensions getExtensions(Annotation annotation) {
        return new ProvidedExtensions(binder.getProvider(Key.get(new TypeLiteral<Set<Extension>>() {
        }, annotation)));
    }

    public class InsertClause {

        private final Class<? extends Extension> extensionClass;

        public InsertClause(Class<? extends Extension> extensionClass) {
            this.extensionClass = extensionClass;
        }

        public ExtensionsBinder before(Class<? extends Extension> beforeExtensionClass) {
            Multibinder.newSetBinder(binder, Extension.class, headOf(beforeExtensionClass))
                    .addBinding()
                    .toInstance(wrapExtension(extensionClass));
            return ExtensionsBinder.this;
        }

        public ExtensionsBinder after(Class<? extends Extension> afterExtensionClass) {
            Multibinder.newSetBinder(binder, Extension.class, tailOf(afterExtensionClass))
                    .addBinding()
                    .toInstance(wrapExtension(extensionClass));
            return ExtensionsBinder.this;
        }
    }
}
