# SearchableSpinner
[![](https://jitpack.io/v/DonMat/searchablespinner.svg)](https://jitpack.io/#DonMat/searchablespinner)

Searchable Spinner allow you to easily search along spinner items using Dialog with SearchView.

It was inspired by [SearchableSpinner by miteshpithadiya](https://github.com/miteshpithadiya/SearchableSpinner) and rewritten to kotlin by me.

![Screenshot](https://github.com/DonMat/SearchableSpinner/raw/master/demo.gif?raw=true "Screenshot")

# Gradle
Step 1. Add it in your root build.gradle at the end of repositories:

```gradle
    allprojects {
        repositories {
        ...
        maven { url "https://jitpack.io" }
        }
    }
```

Step 2. Add the dependency

```gradle
    dependencies {
        implementation 'com.github.DonMat:searchablespinner:v1.0.0'
    }
```

# Usage
```xml
    <pl.utkala.searchablespinner.SearchableSpinner
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"

            app:closeText="Zamknij"
            app:dialogTitle="Wybierz z listy"/>

```

```java
    searchableSpinner.setDialogTitle("Wybierz z listy");
    searchableSpinner.setDismissText("Zamknij");
```

# Changelog
  * <b>1.0.0</b>
    * Initial Release

# License

    Copyright (c) 2018 Mateusz Utkała (DonMat)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
