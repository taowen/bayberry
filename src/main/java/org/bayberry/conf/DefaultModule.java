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
package org.bayberry.conf;

import com.google.inject.AbstractModule;
import org.bayberry.core.spi.Extension;
import org.bayberry.extension.auxiliary.NestedExtensions;
import org.bayberry.extension.auxiliary.ProvidedExtension;
import org.bayberry.extension.injection.InjectionExtension;
import org.bayberry.extension.scope.ScopeExtension;
import org.bayberry.extension.scope.PerTest;
import org.bayberry.extension.scope.PerTestScope;
import org.bayberry.fixture.FixtureModule;

/**
 * @author taowen
 */
public class DefaultModule extends AbstractModule {

    protected void configure() {
        bind(Extension.class).toInstance(NestedExtensions.of(
                getExtension(InjectionExtension.class),
                getExtension(ScopeExtension.class)));
        bindScope(PerTest.class, PerTestScope.INSTANCE);
        install(new FixtureModule());
    }

    private ProvidedExtension getExtension(Class<? extends Extension> extensionClass) {
        return new ProvidedExtension(getProvider(extensionClass));
    }
}
