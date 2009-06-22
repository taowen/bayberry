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
package org.bayberry.extension.auxiliary;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import org.bayberry.core.spi.Extension;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author taowen
 */
public class ExtensionsBinder {

    private final Binder binder;

    public ExtensionsBinder(Binder binder) {
        this.binder = binder;
    }

    public ExtensionsBinder add(Class<? extends Extension> extensionClass) {
        Multibinder.newSetBinder(binder, Extension.class)
                .addBinding()
                .toInstance(wrapExtension(extensionClass));
        return this;
    }

    public ExtensionsBinder insert(Class<? extends Extension> beforeExtensionClass,
                                   Class<? extends Extension> extensionClass) {
        Multibinder.newSetBinder(binder, Extension.class, new Before.Impl(beforeExtensionClass))
                .addBinding()
                .toInstance(wrapExtension(extensionClass));
        return this;
    }

    public ExtensionsBinder append(Class<? extends Extension> afterExtensionClass,
                                   Class<? extends Extension> extensionClass) {
        Multibinder.newSetBinder(binder, Extension.class, new AfterExtension.Impl(afterExtensionClass))
                .addBinding()
                .toInstance(wrapExtension(extensionClass));
        return this;
    }

    private Extension wrapExtension(Class<? extends Extension> extensionClass) {
        Before.Impl before = new Before.Impl(extensionClass);
        Multibinder.newSetBinder(binder, Extension.class, before);
        AfterExtension.Impl after = new AfterExtension.Impl(extensionClass);
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
}
