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

import org.bayberry.core.DefaultBayberryModule;
import org.bayberry.core.container.api.ConfiguredWith;
import org.bayberry.core.fixture.api.UsingFixture;
import org.bayberry.core.helper.api.UsingHelper;
import org.bayberry.integration.junit4.spi.BayberryRunner;
import org.junit.runner.RunWith;

/**
 * Base class for JUnit4 tests who want to use Bayberry.
 *
 * @author taowen
 */
@RunWith(BayberryRunner.class)
@ConfiguredWith(DefaultBayberryModule.class)
public abstract class UsingBayberry implements UsingFixture, UsingHelper {
}
