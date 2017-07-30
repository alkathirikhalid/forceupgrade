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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import org.jsoup.Jsoup;

import java.io.IOException;

import static com.alkathirikhalid.util.Constants.DIV;
import static com.alkathirikhalid.util.Constants.REF;
import static com.alkathirikhalid.util.Constants.USER_AGENT;

/**
 * <p>Executes Network Connecting in the background and returns <code>Result</code>.</p>
 * <code>ForceUpgrade</code> requires <code>Jsoup</code> to make a connection and get the
 * current app version in play store, by extracting a div with a value of softwareVersion.
 * It uses <code>AsyncTask</code> to execute in the background thread.
 * <p>
 * <code>Jsoup</code> is an Open source Java HTML parser, with DOM, CSS, and jquery-like
 * methods for easy data extraction.
 *
 * @author alkathirikhalid
 * @version 1.01
 */

public class ForceUpgrade extends AsyncTask<Void, Void, Result> {
    // The received value from play store.
    private final Result result;
    // Android Context.
    private final Context context;
    // Overall information about the contents of a package collected from AndroidManifest.xml.
    private PackageInfo packageInfo;
    /* An Interface that provides a Call Back Mechanism to notify that Get PlayStoreVersionTask
     has a result.*/
    private GetPlayStoreVersionTask getPlayStoreVersiontask;

    /**
     * Constructor to be used together with <code>setOnTaskCompletionListener</code>
     * adhering to Observer Pattern.
     *
     * @param context
     */
    public ForceUpgrade(Context context) {
        this.context = context;
        this.result = new Result(context);
    }

    /**
     * Constructor used together after <code>ForceUpgrade</code> adhering to Observer Pattern.
     *
     * @param getPlayStoreVersiontask
     * @return
     */
    public ForceUpgrade setOnTaskCompletionListener(GetPlayStoreVersionTask getPlayStoreVersiontask) {
        this.getPlayStoreVersiontask = getPlayStoreVersiontask;
        return this;
    }

    /**
     * Constructor used only after implementing <code>GetPlayStoreVersionTask</code>
     * adhering to Delegation Pattern.
     *
     * @param context
     * @param getPlayStoreVersiontask
     */
    public ForceUpgrade(Context context, GetPlayStoreVersionTask getPlayStoreVersiontask) {
        this.context = context;
        this.getPlayStoreVersiontask = getPlayStoreVersiontask;
        this.result = new Result(context);
    }

    /**
     * Performs computation on a background thread.
     *
     * @param params The parameters of the task.
     * @return The <code>Result</code>, defined by the subclass of this task.
     */
    @Override
    protected Result doInBackground(Void... params) {
        try {
            // Extracts the Package name from the context.
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            // Extracts the Version Name to currentVersion.
            result.setCurrentVersion(packageInfo.versionName);
           /* Name Not Found Exception: is thrown when a given package, application, or component
            * name cannot be found.
            * Null Pointer Exception: Thrown when attempting to use null in a
            * case where an object is required.
            */
        } catch (PackageManager.NameNotFoundException | NullPointerException exception) {
            exception.printStackTrace();
        }
        try {
            // Connects to app play store location and extracts the value from softwareVersion div.
            String playStoreVersion = Jsoup.connect(String.format(Constants.PLAY_STORE_URL, context.getPackageName()))
                    // Time Out duration 10 seconds.
                    .timeout(10000)
                    // The request user-agent header, where the request is by.
                    .userAgent(USER_AGENT)
                    // The request Referrer header, where the request is coming from.
                    .referrer(REF)
                    // Execute the request as a GET, and parse the result.
                    .get()
                    // Finds elements matching a query.
                    .select(DIV)
                    // Get the first matched element.
                    .first()
                    /* Gets the text owned by this element only,
                     * does not get the combined text of all children.
                     */
                    .ownText();
            // sets the value received from Jsoup to PlayStoreVersion.
            result.setPlayStoreVersion(playStoreVersion);
            // Provide at least empty String "" in the event Jsoup fails to connect.
        } catch (IOException exception) {
            exception.printStackTrace();
            result.setPlayStoreVersion("");
        }
        return result;
    }

    /**
     * Invoked after the background computation finishes.
     * The <code>Result</code> of the background computation is passed as a parameter.
     *
     * @param result
     */
    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        /* Ensures there is a result and getPlayStoreVersiontask has been initialized
         * before sending the result.
         */
        if (result != null && getPlayStoreVersiontask != null) {
            getPlayStoreVersiontask.onTaskCompletion(result);
        }
    }
}
