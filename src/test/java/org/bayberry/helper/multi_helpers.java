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
package org.bayberry.helper;

import com.google.inject.ImplementedBy;
import org.bayberry.helper.api.Helper;
import org.bayberry.junit4.UsingBayberry;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author taowen
 */
public class multi_helpers extends UsingBayberry {

    @Helper
    Helper12 helper;

    @Test
    public void should_be_merged() {
        Assert.assertEquals("Hello", helper.hello());
        Assert.assertEquals("World", helper.world());
    }

    @ImplementedBy(Helper1.Impl.class)
    public interface Helper1 {

        String hello();

        public static class Impl implements Helper1 {

            public String hello() {
                return "Hello";
            }
        }
    }

    @ImplementedBy(Helper2.Impl.class)
    public interface Helper2 {

        String world();

        public static class Impl implements Helper2 {

            public String world() {
                return "World";
            }
        }
    }

    public interface Helper12 extends Helper1, Helper2 {
    }
}
