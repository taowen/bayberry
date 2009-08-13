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
package org.bayberry.fixture;

import org.bayberry.fixture.api.FixtureProvider;
import org.bayberry.fixture.api.Fixture;
import org.junit.Test;
import org.junit.Assert;
import com.google.inject.Singleton;

/**
 * @author taowen
 */
public class singleton_fixture extends _fixture_module_feature {

    @Fixture(SomeFixtureProvider.class)
    Object obj1;
    @Fixture(SomeFixtureProvider.class)
    Object obj2;

    @Test
    public void should_share_same_instance() {
        Assert.assertSame(obj1, obj2);
    }

    @Singleton
    public static class SomeFixtureProvider extends FixtureProvider {

        protected Object createFixture() {
            return new Object();
        }
    }
}
