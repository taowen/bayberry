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

import org.bayberry.core.api.ConfiguredWith;
import org.bayberry.fixture.api.UsingFixture;
import org.bayberry.junit4.Bayberry;
import org.junit.runner.RunWith;

/**
 * @author taowen
 */
@RunWith(Bayberry.class)
@ConfiguredWith(FixtureModule.class)
public abstract class _fixture_module_feature implements UsingFixture {
}
