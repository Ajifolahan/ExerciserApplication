# ExerciserApplication
An application that uses API and SQLite Database to view and save exercises

This android app provides users with various types of workouts based on their customizations. Users can choose what muscle to work out, what type of workout(strength etc) 
and what difficutly level. The user can also save favorite exercises and view it when the app is closed and reopened. This data persists because it is stored in a database. Used RestAPI from https://rapidapi.com/apininjas/api/exercises-by-api-ninjas. Also used Google Custom Search API to get images. We also used ROOM to implement 
the database connection.

## Built with

* Kotlin
* RestAPI
* SQLite
* Android Studio

## Acknowledgments

* RapidAPI

## For Developers

To get data from the Rest API, you need to create a variable in the local.properties file for your API key(gotten from RapidAPI). And call this variable when setting up your API connection. 

To get data from the google custom API. Get your apikey from google, create a variable for it in local.properties and insert it into your API connection. 

If you are using Android Studio, make your API key variable in local.properties file, add this dependency to the build.gradle project file-under plugins(
    id 'com.google.android.libraries.mapsplatform.secrets-gradle-plugin' version '2.0.1' apply false), sync your project. Then finally call BuildConfig.(your api key variable) when setting up your API connection.


