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

import com.google.inject.MembersInjector;
import com.google.inject.spi.TypeEncounter;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author taowen
 */
public class FixtureFieldsCollector {

    private final Set<FixtureField> fixtureFields = new HashSet<FixtureField>();
    private final TypeEncounter typeEncounter;

    public FixtureFieldsCollector(TypeEncounter typeEncounter) {
        this.typeEncounter = typeEncounter;
    }

    public <I> void collectFixtureFields(Class clazz) {
        if (clazz == null) {
            return;
        }
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            FixtureField fixtureField = FixtureField.create(field, typeEncounter);
            if (fixtureField != null) {
                fixtureFields.add(fixtureField);
            }
        }
        collectFixtureFields(clazz.getSuperclass());
    }

    public void registerMembersInjector() {
        if (fixtureFields.isEmpty()) {
            return;
        }
        typeEncounter.register(new MembersInjector() {
            public void injectMembers(Object obj) {
                for (FixtureField field : fixtureFields) {
                    field.inject(obj);
                }
            }
        });
    }
}
