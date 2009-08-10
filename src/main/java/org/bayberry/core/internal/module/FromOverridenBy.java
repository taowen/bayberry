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
package org.bayberry.core.internal.module;

import com.google.inject.Module;
import org.bayberry.core.api.OverriddenBy;

/**
 * @author taowen
 */
public class FromOverridenBy implements ModuleAppender {

    private final ModuleInstantiator instantiator;

    public FromOverridenBy(ModuleInstantiator instantiator) {
        this.instantiator = instantiator;
    }

    public Module append(Module appendTo, Object testCase, Class<?> clazz) {
        OverriddenBy overriddenBy = clazz.getAnnotation(OverriddenBy.class);
        if (overriddenBy != null) {
            return new OverridenModule(appendTo, instantiator.newInstances(overriddenBy.value()));
        }
        return appendTo;
    }
}
