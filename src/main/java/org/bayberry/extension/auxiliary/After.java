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
package org.bayberry.extension.auxiliary;

import org.bayberry.core.spi.Extension;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

/**
     * @author taowen
 */
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface After {

    public abstract Class<? extends Extension> value();

    static class Impl implements After {

        private final Class<? extends Extension> value;

        public Impl(Class<? extends Extension> value) {
            this.value = value;
        }

        public Class<? extends Extension> value() {
            return value;
        }

        public Class<? extends Annotation> annotationType() {
            return After.class;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Impl impl = (Impl) o;

            if (!value.equals(impl.value)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }
    }
}
