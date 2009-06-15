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

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import java.util.Set;

/**
 * @author taowen
 */
public class OverridenModule implements Module {

    private final int hashCode;
    private final Module module;

    public OverridenModule(Module beOverriden, Set<Module> overridenBy) {
        hashCode = beOverriden.hashCode() * 31 + overridenBy.hashCode();
        module = Modules.override(beOverriden).with(overridenBy);
    }

    public void configure(Binder binder) {
        module.configure(binder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OverridenModule that = (OverridenModule) o;

        if (hashCode != that.hashCode) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
