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
package org.bayberry.fixture.internal;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author taowen
 */
public class UsingFixtureTypeListener implements TypeListener {

    public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
        Class clazz = typeLiteral.getRawType();
        Set<FixtureField> fields = new HashSet<FixtureField>();
        collectFixtureFields(fields, typeEncounter, clazz);
        typeEncounter.register(new FixtureMembersInjector<I>(fields));
    }

    private <I> void collectFixtureFields(Set<FixtureField> fields, TypeEncounter<I> typeEncounter, Class clazz) {
        if (clazz == null) {
            return;
        }
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            FixtureField fixtureField = FixtureField.create(field, typeEncounter);
            if (fixtureField != null) {
                fields.add(fixtureField);
            }
        }
        collectFixtureFields(fields, typeEncounter, clazz.getSuperclass());
    }
}
