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
package org.bayberry.integration.fest;

import org.bayberry.core.helper.api.Helper;
import org.bayberry.integration.junit4.UsingBayberry;
import org.junit.Test;

/**
 * @author taowen
 */
public class assert_using_fest extends UsingBayberry {

    @Helper
    FestAssertHelper i;

    @Test
    public void assert_1_is_equal_to_1() {
        i.assertThat(1).isEqualTo(1);
    }
}
