# NavTalk Android SDK (navtalk_sdk)

NavTalk Android SDK allows developers to quickly integrate AI-powered voice and chat interfaces into Android applications.
## Get Project License and Custom Avatar

Before using the SDK, you need to obtain a **project license** and configure a **custom avatar**.

Please apply for them here: 
[NavTalk Console](https://console.navtalk.ai/#/)

## Android Configuration

1. Android Gradle Plugin (AGP): 8.0+

2. Gradle Version: Min 8.0, Recommended 8.2+

3. Compile SDK: 36 (Android 13)

4. Min SDK: 24 (Android 7.0)

5. Java / Kotlin: Java 11, Kotlin 1.9+

6. Target SDK: Same as compileSdk (36)

**Note:** Ensure your project versions match the requirements, otherwise build errors may occur or the SDK may not function properly.
## Installation

1.Add it in your settings.gradle.kts at the end of repositories:
```kotlin
dependencyResolutionManagement {
      repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
      repositories {
            google()
            mavenCentral()
            maven("https://jitpack.io")
      }
}
```
2.Add the dependency in your app/build.gradle.kts:
```kotlin
dependencies {
    implementation("com.github.navtalk:Android-SDK:1.0.5")
}
```

3.Config NavTalk ChatActivity(required):
```text
  3.1.Add ChatActivity in AndroidManifest.xml
    <activity
        android:name="com.navtalk.navtalk_sdk.ChatActivity"
        android:exported="true" />
        
  3.2.Create a theme for ChatActivity in res/values/themes.xml
    <style name="Theme.ChatActivity" parent="Theme.AppCompat.Light.NoActionBar" />
    
  3.3.Set ChatActivity's theme in AndroidManifest.xml
    <activity
         android:name="com.navtalk.navtalk_sdk.ChatActivity"
         android:exported="true"
         android:theme="@style/Theme.ChatActivity" />
```
  

## Usage
![Chat Interface Screenshot](showImage/NavTalk_First_Shot.PNG)

1.NavTalk License (required)
```kotlin
  NavTalkManager.license = "*******"
```

2.NavTalk Avatar Name Or Id (required)
```kotlin
  NavTalkManager.characterName = "*******"
  NavTalkManager.characterId = "*******"
```
  - Note: name: The name of the digital human character (query method 1)
  - Note: avatarId: Direct avatar ID for precise lookup (query method 2, higher priority)
  - Note: Query Priority: If both avatarId and name are provided, avatarId takes precedence.
  - Note: When the system role provider is 11Labs, function call and image recognition are not supported.
  - Custom roles support function call and image recognition only when OpenAIRealtime is selected.

3.Save chat history locally (optional)
```kotlin
  NavTalkManager.isOrNotSaveHistoryChatMessages = false
```

4.Function Call (Optional)

  Example: A function call that closes the chat when triggered.
  
  (1) Add Function Call
  ```kotlin
  val functions = listOf(
    mapOf(
        "type" to "function",
        "name" to "function_call_close_talk",
        "description" to "Please trigger this method when you receive a message or when the conversation is closed.",
        "parameters" to mapOf(
            "type" to "object",
            "properties" to mapOf(
                "userInput" to mapOf(
                    "type" to "string",
                    "description" to "Raw user request content to be processed"
                )
            ),
            "required" to listOf("userInput")
        )
    )
)
val functionsJsonString = org.json.JSONObject(mapOf("functions" to functions)).getJSONArray("functions").toString()
NavTalkManager.functionsJsonString = functionsJsonString
```
  
  (2) Trigger Function Call
```text
    import FunctionCallListener
    import org.json.JSONObject
    import com.navtalk.navtalk_sdk.ChatActivity
```
```kotlin
    NavTalkManager.functionCallListener = object : FunctionCallListener{
      override fun onFunctionCalled(message: String) {
        println("Function_Call:${message}")
        val jsonObject = JSONObject(message)
        val data = jsonObject.optJSONObject("data")
        val function_name = data.getString("function_name")
        if (function_name == "function_call_close_talk"){
            ChatActivity.closeCall()
        }
      }
    }
 ```
    
6.Navigate to the chat interface in your Activity (required)
  ```kotlin
  NavTalkManager.showChatActivity(this)
 ```
  
## Specific usage demo

NavTalk Android Demo Code | Fully open source | Kotlin code available on GitHub | [GitHub](https://github.com/navtalk/Samples/tree/main/Android)

## Related Projects

If you want to learn more about AI or chat-related projects, check out:

[NavTalk Samples](https://github.com/navtalk/Samples)

## Author

Frank Fu, fuwei007@gmail.com




