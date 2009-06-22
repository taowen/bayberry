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

import com.google.inject.Provider;
import org.bayberry.core.spi.Extension;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author taowen
 */
public class ProvidedExtensions implements Extension {

    private final Provider<Set<Extension>> extensionsProvider;
    private Extension extension;

    public ProvidedExtensions(Provider<Set<Extension>> extensionsProvider) {
        this.extensionsProvider = extensionsProvider;
    }

    public void before(Object testCase, Method testMethod) throws Throwable {
        getExtension().before(testCase, testMethod);
    }

    public void after(Object testCase, Method testMethod) throws Throwable {
        getExtension().after(testCase, testMethod);
    }

    private Extension getExtension() {
        if (extension == null) {
            extension = NestedExtensions.of(extensionsProvider.get());
        }
        return extension;
    }
}
