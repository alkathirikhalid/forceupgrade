[![](https://jitpack.io/v/alkathirikhalid/forceupgrade.svg)](https://jitpack.io/#alkathirikhalid/forceupgrade)

# Force Upgrade
An Android library to detect current installed app version and current published Play Store version and perform an automatic redirect to Play Store for an update if required for API 16 and above. This library has a fail safe mechanisim in the event no network is not available or <code>timeout()</code> occured in <i>10 Sec</i> a <code>0</code> is set to <code>playStoreVersion</code> resulting <code>isNewVersion()</code> to <code>False</code>. Removing the need to check for network for the actual implimentation of <code>ForceUpgrade</code> library.

The only thing you need to pass from your App <code>Activity</code> is the <code>Context</code> of that <code>Activity</code>. It can be used in any Android project either using build script or adding aar file.
Passing your <code>Activity</code> <code>Context</code> as such <code>ForceUpgrade(this)</code>.

# Usage
<strong>It is strogly advisable to to check <code>isNewVersion()</code> prior to invoking <code>goToPlayStore()</code> inorder to provide UI option or force redirect for upgrade, the former will improve UX while the latter improve your application logic to only redirect when there is an actual updated version in Play Store.</strong>

The code has been intentionally made to allow a redirect in the event the developer requires other implementaions of this library. Example:
You may have an About App Screen and use <code>ForceUpgrade</code> to dynamically display the fields:-
<ul>
<li>Current Version: 1.5</li>
<li>Play Store Version: 1.7</li>
<li>Go To Play Store: Update</li>
</ul>
Oher than update, Since <code>ForceUpgrade</code> dynamically displays Play Store Location, <code>goToPlayStore()</code> can be used for redirect to Play Store page:-
<ul>
<li>Release Notes</li>
<li>Rating</li>
<li>Commenting</li>
</ul>

# Usage Types
There are two ways you can use the library either one will work fine, depending on your implementation.

### Delegation Pattern

```
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
	// Pass the Context and the Listener
        new ForceUpgrade(this).setOnTaskCompletionListener(new GetPlayStoreVersionTask() {
            @Override
            public void onTaskCompletion(Result result) {
		// Manipulate result as required when received.
                result.isNewVersion();
                result.getCurrentVersion();
                result.getPlayStoreVersion();
		// Check if an exception occurred resulting to 0 or there is no new version, prior redirect
                if (result.isNewVersion()) {
                   result.goToPlayStore();
                  }
            }
        }).execute();
}
```
### Observer Pattern

```
public class MainActivity extends AppCompatActivity implements GetPlayStoreVersionTask{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	// Pass the Context and the Listener
        ForceUpgrade forceUpgrade = new ForceUpgrade(this, this);
        forceUpgrade.execute();
    }

    @Override
    public void onTaskCompletion(Result result) {
		// Manipulate result as required when received
                result.isNewVersion();
                result.getCurrentVersion();
                result.getPlayStoreVersion();
		// Check if an exception occurred resulting to 0 or there is no new version, prior redirect
                if (result.isNewVersion()) {
                   result.goToPlayStore();
                  }
    }
}
```

### Accesible and usable Methods
<p>1. <code>isNewVersion()</code> Differentiates between the <code>currentVersion</code> installed in the device with <code>playStoreVersion</code> App Version in Play Store. <code>true</code> If the Play Store version is new, <code>false</code> If the Play Store version is old or connection failed.</p>
<p>2. <code>getCurrentVersion()</code> The current App Version installed.</p>
<p>3. <code>getPlayStoreVersion()</code> The current App Version in Play Store.</p>
<p>4. <code>goToPlayStore()</code> Broadcast App Link location for Interception by Device Google Play Store, inorder to redirect users to the current app in Play Store.If you have multiple application installed in the device that can handle the URI, all of them will receive the broadcast.</p>

# Installation
### Gradle
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```
dependencies {
	        compile 'com.github.alkathirikhalid:forceupgrade:v1.01'
	}
  ```
### Maven
  ```
  <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
  ```
  ```
  <dependency>
	    <groupId>com.github.alkathirikhalid</groupId>
	    <artifactId>forceupgrade</artifactId>
	    <version>v1.01</version>
	</dependency>
  ```
  
# Further Resources
<ul>
<li>Document download: https://github.com/alkathirikhalid/forceupgrade/releases/download/v1.01/docs.zip</li>
<li>AAR download: https://github.com/alkathirikhalid/forceupgrade/releases/download/v1.01/forceupgrade.aar</li>
</ul>
  
# License

Copyright 2015 Al-Kathiri Khalid www.alkathirikhalid.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
