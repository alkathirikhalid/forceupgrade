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

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * <p><code>Result</code> holds Play Store Version and Current App Version.</p>
 * A plain old java object (POJO) that is passed as a result from <code>Jsoup</code>,
 * when <code>onTaskCompletion</code> in <code>GetPlayStoreVersionTask</code> gets Invoked.
 *
 * @author alkathirikhalid
 * @version 1.01
 */

public class Result {
    // Android Context.
    private Context context;
    // Current App Version in Play Store.
    private String playStoreVersion;
    // Current App Version installed.
    private String currentVersion;

    /**
     * The Only Constructor when Instantiating <code>Result</code>.
     *
     * @param context the Android Context is the only thing required,
     *                to get an Instance of <code>Result</code>.
     */
    public Result(Context context) {
        this.context = context;
    }

    /**
     * Getter Method.
     *
     * @return The current App Version in Play Store.
     */
    public String getPlayStoreVersion() {
        return playStoreVersion;
    }

    /**
     * Protected Setter Method, Internally used by <code>ForceUpgrade</code>.
     *
     * @param playStoreVersion The current App Version in Play Store.
     */
    protected void setPlayStoreVersion(String playStoreVersion) {
        this.playStoreVersion = playStoreVersion;
    }

    /**
     * Getter Method.
     *
     * @return The current App Version installed.
     */
    public String getCurrentVersion() {
        return currentVersion;
    }

    /**
     * Protected Setter Method, Internally Used by <code>ForceUpgrade</code>.
     *
     * @param currentVersion The current App Version installed.
     */
    protected void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    /**
     * Differentiates between the <code>currentVersion</code> installed in the device
     * with <code>playStoreVersion</code> App Version in Play Store.
     *
     * @return <code>true</code> If the Play Store version is new.
     */
    public boolean isNewVersion() {
        return extractVersion(getCurrentVersion()) < extractVersion(getPlayStoreVersion());
    }

    /**
     * Broadcast App Link location for Interception by Device Google Play Store,
     * inorder to redirect users to the current app in Play Store.
     * <p>
     * If you have multiple application installed in the device that can handle the URI,
     * all of them will receive the broadcast.
     */
    public void goToPlayStore() {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Constants.PLAY_STORE_URL, context.getPackageName()))));
    }

    /**
     * Extracts numerals from a version string, such as 1.0 to 10.
     *
     * @param value The version value in dot notation format.
     * @return The numeric format.
     */
    private long extractVersion(String value) {
        try {
            // Replace all escape digits 0-9
            return Long.valueOf(value.replaceAll("\\D+", ""));
            // Provide at least 0 in the event Play Store value is not accessible.
        } catch (IllegalArgumentException | NullPointerException exception) {
            return 0;
        }
    }
}
