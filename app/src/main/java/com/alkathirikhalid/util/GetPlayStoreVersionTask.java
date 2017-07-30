/*
 * Copyright 2015 Al-Kathiri Khalid www.alkathirikhalid.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alkathirikhalid.util;

/**
 * <p>Get Play Store Version Task Call Back.</p>
 * An <code>Interface</code> that provides a Call Back Mechanism to be notified when
 * Get Play Store Version Task has a result.
 *
 * @author alkathirikhalid
 * @version 1.01
 */

public interface GetPlayStoreVersionTask {
    /**
     * Notify when <code>GetPlayStoreVersionTask</code> is completed.
     *
     * @param result The <code>Result</code> with Play Store Version and Current App Version.
     */
    void onTaskCompletion(Result result);
}
