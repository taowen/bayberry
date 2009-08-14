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
package org.bayberry.core.fixture.internal;

import com.google.inject.Provider;
import com.google.inject.spi.TypeEncounter;
import org.bayberry.core.fixture.api.Fixture;

import java.lang.reflect.Field;

/**
 * @author taowen
 */
public class FixtureField {

    private final Field field;
    private final Provider<Object> fixtureProvider;

    public FixtureField(Field field, Provider<Object> fixtureProvider) {
        this.field = field;
        this.fixtureProvider = fixtureProvider;
    }

    public void inject(Object obj) {
        try {
            field.set(obj, fixtureProvider.get());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static FixtureField create(Field field, TypeEncounter typeEncounter) {
        Fixture fixtureAnnotation = field.getAnnotation(Fixture.class);
        if (fixtureAnnotation == null) {
            return null;
        }
        final Provider<? extends Provider> fixtureProvider = typeEncounter.getProvider(fixtureAnnotation.value());
        return new FixtureField(field, new Provider<Object>() {
            public Object get() {
                return fixtureProvider.get().get();
            }
        });
    }
}
