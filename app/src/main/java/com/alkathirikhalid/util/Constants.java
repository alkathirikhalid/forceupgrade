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
 * <p>Local Library Constants.</p>
 * These are the values required by <code>Jsoup</code> to make a connection and get the
 * current app version in play store, by extracting a div with a value of softwareVersion.
 * <code>Jsoup</code> is an Open source Java HTML parser, with DOM, CSS, and jquery-like
 * methods for easy data extraction.
 *
 * @author alkathirikhalid
 * @version 1.01
 */

class Constants {
    // The request Referrer header, where the request is coming from.
    static final String REF = "http://www.google.com";
    // The Selector to select the specific element.
    static final String DIV = "div[itemprop=softwareVersion]";
    // The request user-agent header, where the request is by.
    static final String USER_AGENT = "Mozilla/5.0 (Windows; U; WindowsNT 6.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6";
    // The Play Store URL.
    static final String PLAY_STORE_URL = "http://play.google.com/store/apps/details?id=%s&hl=en";
}
