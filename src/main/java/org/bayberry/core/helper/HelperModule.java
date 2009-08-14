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
package org.bayberry.core.helper;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.bayberry.core.helper.api.UsingHelper;
import org.bayberry.core.helper.internal.HelperFieldsCollector;

/**
 * Inject test helpers to classes implementing UsingHelper.
 *
 * @author taowen
 * @see org.bayberry.core.helper.api.UsingHelper
 */
public class HelperModule extends AbstractModule {

    protected void configure() {
        bindListener(new AbstractMatcher<TypeLiteral>() {
            public boolean matches(TypeLiteral typeLiteral) {
                return UsingHelper.class.isAssignableFrom(typeLiteral.getRawType());
            }
        }, new TypeListener() {
            public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
                new HelperFieldsCollector(typeLiteral.getRawType()).registerMembersInjector(typeEncounter);
            }
        });

    }
}
