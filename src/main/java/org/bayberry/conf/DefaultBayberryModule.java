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
import static org.bayberry.extension.binder.ExtensionsBinder.extensionsIn;
import org.bayberry.extension.injection.InjectionExtension;
import org.bayberry.extension.scope.PerTest;
import org.bayberry.extension.scope.ScopeExtension;
import org.bayberry.extension.scope.internal.PerTestScope;
import org.bayberry.fixture.FixtureModule;
import org.bayberry.helper.HelperModule;

/**
 * @author taowen
 */
public class DefaultBayberryModule extends AbstractModule {

    protected void configure() {
        extensionsIn(binder()).add(ScopeExtension.class, InjectionExtension.class);
        bindScope(PerTest.class, PerTestScope.INSTANCE);
        install(new FixtureModule());
        install(new HelperModule());
    }
}
