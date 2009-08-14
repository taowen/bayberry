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
package org.bayberry.integration.fest.spi;

import org.fest.assertions.Assertions;
import org.fest.assertions.ImageAssert;

import java.awt.image.BufferedImage;

import com.google.inject.ImplementedBy;
import com.google.inject.Singleton;

/**
 * @author taowen
 */
@ImplementedBy(ImageAssertHelper.Impl.class)
public interface ImageAssertHelper {

    ImageAssert assertThat(BufferedImage actual);

    @Singleton
    public static class Impl implements ImageAssertHelper {

        public ImageAssert assertThat(BufferedImage actual) {
            return Assertions.assertThat(actual);
        }
    }
}
