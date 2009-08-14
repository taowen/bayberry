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
package org.bayberry.core.test.scope;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bayberry.integration.junit4.UsingBayberry;
import org.bayberry.core.test.scope.api.PerTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author taowen
 */
public class scoped_as_per_test extends UsingBayberry {

    private final Set<SomeObject> someObjects = new HashSet<SomeObject>();

    @Inject
    Injector injector;

    @Test
    public void should_has_only_one_instance() {
        assert_different_instance_between_tests();
        Assert.assertSame(injector.getInstance(SomeObject.class), injector.getInstance(SomeObject.class));
    }

    @Test
    public void should_be_not_share_instance_between_tests() {
        assert_different_instance_between_tests();
    }

    private void assert_different_instance_between_tests() {
        int before = someObjects.size();
        someObjects.add(injector.getInstance(SomeObject.class));
        Assert.assertEquals(1 + before, someObjects.size());
    }

    @PerTest
    public static class SomeObject {
    }
}
