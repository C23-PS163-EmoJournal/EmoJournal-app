# EmoJournal (C23-PS163)- Speech Emotion Recognition for Mental Health Detection -Bangkit Capstone Company Project

## App Description
With the development of data-driven tools such as machine learning, audio signal processing has achieved a remarkable level of accuracy in speech recognition and emotion analysis. This feature is particularly valuable for identifying mental health issues, as many people who have them are uneasy to discuss them openly. Traditional methods of examination and diagnosis can be time-consuming and uncomfortable. To address this challenge, we propose a technology that analyzes an individual's speech patterns, including tone, intonation, pitch, and pauses, to identify signs of mental health problems. By simply speaking about their day, as they would in a diary, individuals can receive a non-invasive and effective assessment of their mental health. Our approach seeks to detect emotions in speech that may indicate conditions such as anxiety or depression.

Download Apk : https://github.com/C23-PS163-EmoJournal/EmoJournal-app/releases/download/v1.0.0/app-debug.apk

### Screenshots
<p>
<img src ="https://github.com/C23-PS163-EmoJournal/EmoJournal-app/assets/93020273/40dffeca-23f9-43f7-95a1-a844843445e2" height="510">
<img src ="https://github.com/C23-PS163-EmoJournal/EmoJournal-app/assets/93020273/c5662e87-80ab-4a91-a9d1-b697b17fd6fb" height="510">
<img src ="https://github.com/C23-PS163-EmoJournal/EmoJournal-app/assets/93020273/06eb1307-ba33-42b8-8e19-bfcb2abce893" height="510">
<img src ="https://github.com/C23-PS163-EmoJournal/EmoJournal-app/assets/93020273/a5394aee-3730-40d1-94e3-dbc311139fc3" height="510">
<p>

## Development

#### Requirements
* A Windows computer.
* Android Studio
* Android Virtual Device (AVD)

#### Depedencies
```Gradle
dependencies {

    implementation 'androidx.core:core-ktx:1.8.0'
    implementation platform('org.jetbrains.kotlin:kotlin-bom:1.8.0')
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.1'
    implementation 'androidx.activity:activity-compose:1.5.1'
    implementation platform('androidx.compose:compose-bom:2022.10.00')
    implementation 'androidx.compose.ui:ui'
    implementation 'androidx.compose.ui:ui-graphics'
    implementation 'androidx.compose.ui:ui-tooling-preview'
    implementation 'androidx.compose.material3:material3'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    androidTestImplementation platform('androidx.compose:compose-bom:2022.10.00')
    androidTestImplementation 'androidx.compose.ui:ui-test-junit4'
    debugImplementation 'androidx.compose.ui:ui-tooling'
    debugImplementation 'androidx.compose.ui:ui-test-manifest'

    // Compose dependencies
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0"

    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:okhttp:5.0.0-alpha.3'
    implementation 'com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3'
}
```

#### Plugins
```Gradle
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}
```

### Clone this App

**Clone**
```bash
$ git [clone https://github.com/robertheo15/company-project.git](https://github.com/C23-PS163-EmoJournal/EmoJournal-app.git)https://github.com/C23-PS163-EmoJournal/EmoJournal-app.git
```

**Open in Android Studio**
* `File -> Open -> Navigate to folder that you clone this repo -> Open`

**Run this project on AVD**
* `Start AVD -> Run 'app'`

**Build this app**
* `Build -> Build Bundle(s)/APK(s) -> Build APK(s)`
