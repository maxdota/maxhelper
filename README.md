Max Helper
=======

This library includes many helpers to help simplifying Android app development.

![](website/static/sample_screenshot.png)

Maven Installation
--------
Add to `build.gradle` at root folder
```groovy
allprojects {
    repositories {
        ...
        maven {
            name "Max Helper Library"
            url "https://gitlab.com/api/v4/projects/26576533/packages/maven"
        }
    }
}
```
Add to `build.gradle` at app folder
```groovy
compilation 'com.maxdota.maxhelper:maxhelper:1.0.7'
```

Usage
--------
(will be updated soon)

License
--------

    Copyright 2013 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.