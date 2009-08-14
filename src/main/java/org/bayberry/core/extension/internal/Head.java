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
package org.bayberry.core.extension.internal;

import com.google.inject.BindingAnnotation;
import org.bayberry.core.extension.spi.TestExtension;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author taowen
 */
@Retention(RetentionPolicy.RUNTIME)
@BindingAnnotation
public @interface Head {

    public abstract Class<? extends TestExtension> value();

    static class Impl implements Head {

        private final Class<? extends TestExtension> value;

        public Impl(Class<? extends TestExtension> value) {
            this.value = value;
        }

        public Class<? extends TestExtension> value() {
            return value;
        }

        public Class<? extends Annotation> annotationType() {
            return Head.class;
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

        public static Head headOf(Class<? extends TestExtension> value) {
            return new Impl(value);
        }
    }

}
