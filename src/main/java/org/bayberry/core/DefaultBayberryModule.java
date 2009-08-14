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
import org.bayberry.core.fixture.FixtureModule;
import org.bayberry.core.helper.HelperModule;
import org.bayberry.core.test.TestExtensionModule;
import org.bayberry.core.test.injection.InjectionExtension;
import org.bayberry.core.test.scope.ScopeExtension;
import org.bayberry.core.test.scope.api.PerTest;
import org.bayberry.core.test.scope.internal.PerTestScope;

/**
 * @author taowen
 */
public class DefaultBayberryModule extends AbstractModule {

    protected void configure() {
        bindScope(PerTest.class, PerTestScope.INSTANCE);
        install(new FixtureModule());
        install(new HelperModule());
        install(new TestExtensionModule() {
            protected void configure() {
                add(ScopeExtension.class, InjectionExtension.class);
            }
        });
    }
}
