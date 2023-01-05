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
        implementation 'com.github.DonMat:searchablespinner:v1.0.1'
    }
```

# Usage
```xml

<pl.utkala.searchablespinner.SearchableSpinner android:layout_width="wrap_content" android:layout_height="wrap_content"

        app:closeText="Zamknij" app:dialogTitle="Wybierz z listy" />

```

```java
searchableSpinner.setDialogTitle("Wybierz z listy");
        searchableSpinner.setDismissText("Zamknij");
```

## Set hint for SearchableSpinner

To set hint on spinner you can use prepared **StringHintArrayAdapter** or create your own spinner adapter to handle hint for custom object.

Hint value can be passed directly to _StringHintArrayAdapter_ after set **showHint** to true

```xml
app:showHint="true"
```

or

```java
searchableSpinner.showHint=true
        searchableSpinner.adapter=StringHintArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,users,"Select Item")
```

## Set custom OnSearchableItem listener

You can set your own listener when user select filtered result from dialog.

```java
searchableSpinner.onSearchableItemClick=object:OnSearchableItemClick<Any?>{
        override fun onSearchableItemClicked(item:Any?,position:Int){
        if(position>0){
        searchableSpinner.setSelection(position)
        }else{
        searchableSpinner.setSelection(Spinner.INVALID_POSITION)
        }
        }
        }
```

## Change dialog background color

You can set your own dialog background passing _Drawable_ to **setDialogBackground**

```java
searchableSpinner.setDialogBackground(ColorDrawable(Color.RED))
```

## Set custom dialog adapter

You can set your own list adapter. It can be used to add custom filters or custom list item views.

```java
searchableSpinner.setCustomDialogAdapter(T:ArrayList<*>)
```

# Changelog

* **1.1.0**
  * Add hint for spinner
  * Add OnSearchableItem setter
  * Add custom dialog background setter
  * Add custom dialog adapter setter
* **1.0.1**
  * Fix lost state after screen rotate
* **1.0.0**
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
