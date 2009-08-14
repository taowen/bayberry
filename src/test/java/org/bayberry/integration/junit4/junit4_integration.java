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
package org.bayberry.integration.junit4;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import org.bayberry.core.container.api.ConfiguredWith;
import org.bayberry.core.helper.api.Helper;
import org.bayberry.integration.fest.FestAssertHelper;
import org.junit.Before;
import org.junit.Test;

/**
 * @author taowen
 */
@ConfiguredWith(junit4_integration.Module.class)
public class junit4_integration extends UsingBayberry {

    @Inject
    String injectedString;

    @Helper
    FestAssertHelper i;

    private String injectedBeforeBefore;

    @Before
    public void save_injected_string() {
        injectedBeforeBefore = injectedString;
    }

    @Test
    public void should_be_done_before_before() {
        i.assertThat(injectedBeforeBefore).isEqualTo("Hello");
    }

    public static class Module extends AbstractModule {

        protected void configure() {
            bind(String.class).toInstance("Hello");
        }
    }
}
