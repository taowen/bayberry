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
package org.bayberry.core.extension.scope.internal;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * @author taowen
 */
public class PerTestScope implements Scope {

    public final static PerTestScope INSTANCE = new PerTestScope();

    private final ThreadLocal<Map<Key, Object>> objects = new ThreadLocal<Map<Key, Object>>() {
        @Override
        protected Map<Key, Object> initialValue() {
            return new HashMap<Key, Object>();
        }
    };

    public <T> Provider<T> scope(final Key<T> key, final Provider<T> provider) {
        return new Provider<T>() {
            public T get() {
                if (!objects.get().containsKey(key)) {
                    objects.get().put(key, provider.get());
                }
                return (T) objects.get().get(key);
            }
        };
    }

    public void clear() {
        objects.remove();
    }
}
