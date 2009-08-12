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
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.bayberry.core.spi.Extension;
import org.bayberry.extension.binder.ExtensionsBinder;
import org.bayberry.extension.binder.internal.ProvidedExtensions;
import org.bayberry.extension.injection.InjectionExtension;
import org.bayberry.extension.scope.PerTest;
import org.bayberry.extension.scope.internal.PerTestScope;
import org.bayberry.extension.scope.ScopeExtension;
import org.bayberry.fixture.FixtureModule;

import java.util.Set;

/**
 * @author taowen
 */
public class DefaultModule extends AbstractModule {

    protected void configure() {
        new ExtensionsBinder(binder())
                .init()
                .add(InjectionExtension.class)
                .insert(InjectionExtension.class, ScopeExtension.class);
        bindScope(PerTest.class, PerTestScope.INSTANCE);
        install(new FixtureModule());
    }
}
