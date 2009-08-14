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
package org.bayberry.core.helper.internal;

import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.spi.TypeEncounter;
import org.bayberry.core.helper.api.Helper;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author taowen
 */
public class HelperFieldsCollector {

    private final Set<Field> helperFields = new HashSet<Field>();
    private Provider<Injector> injectorProvider;

    public HelperFieldsCollector(Class clazz) {
        collectHelperFields(clazz);
    }

    private void collectHelperFields(Class clazz) {
        if (clazz == null) {
            return;
        }
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Helper.class)) {
                helperFields.add(field);
            }

        }
        collectHelperFields(clazz.getSuperclass());
    }

    public void registerMembersInjector(TypeEncounter typeEncounter) {
        if (helperFields.isEmpty()) {
            return;
        }
        injectorProvider = typeEncounter.getProvider(Injector.class);
        typeEncounter.register(new HelperFieldsInjector(helperFields, injectorProvider));
    }
}
