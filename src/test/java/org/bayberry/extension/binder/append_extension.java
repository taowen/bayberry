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
package org.bayberry.extension.binder;

import com.google.inject.AbstractModule;
import static org.bayberry.extension.binder.ExtensionsBinder.extensionsIn;
import org.junit.Test;

/**
 * @author taowen
 */
public class append_extension extends _extension_binder_feature {
    @Test
    public void should_append_after_the_target() throws Throwable {
        call(new Module());
        assertCalledSequence(extension1, extension3);
    }

    public static class Module extends AbstractModule {
        protected void configure() {
            extensionsIn(binder())
                    .add(Extension1.class)
                    .insert(Extension3.class).after(Extension1.class);
        }
    }
}
