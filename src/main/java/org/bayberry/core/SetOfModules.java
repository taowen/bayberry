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

import java.util.HashSet;
import java.util.Set;

/**
 * @author taowen
 */
public class SetOfModules implements Module {

    private final Set<Module> modules = new HashSet<Module>();

    public SetOfModules(Module module, Set<Module> modules) {
        this.modules.add(module);
        this.modules.addAll(modules);
    }

    public void configure(Binder binder) {
        for (Module module : modules) {
            module.configure(binder);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetOfModules that = (SetOfModules) o;

        if (!modules.equals(that.modules)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
