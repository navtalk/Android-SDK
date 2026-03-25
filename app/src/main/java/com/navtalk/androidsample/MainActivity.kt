package com.navtalk.androidsample;
import android.app.Activity;
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.graphics.Color
import android.view.WindowInsetsController
import androidx.core.view.WindowCompat
import FunctionCallListener
import org.json.JSONObject

// MainActivity 继承 Activity，表示这是一个 Android 页面
class MainActivity: Activity(){
    //Activity 第一次创建时调用
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Activity--当活动第一次被创建时调用")
        setContentView(R.layout.main_activity)
        setupStatusBarUI()
        initUI()
    }
    //设置状态栏UI：
    fun setupStatusBarUI(){
        // ① 内容延伸到状态栏
        WindowCompat.setDecorFitsSystemWindows(window, false)
        // ② 状态栏透明（必须手动设）
        window.statusBarColor = Color.TRANSPARENT
        // ③ 设置状态栏字体颜色（根据背景选）：把“状态栏样式”设置为“深色文字模式”
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }else{
            // 兼容旧版本
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
    //设置UI：
    fun initUI(){
        val chatButton: Button = findViewById(R.id.ChatButton)
        chatButton.setOnClickListener {
            //必要参数
            NavTalkManager.license = "sk_navtalk_tcDB9SaqHKKe7pXc5tyt0Z7aNB0SgI3R"
            NavTalkManager.characterName = "Freya"
            //选填参数
            NavTalkManager.isOrNotSaveHistoryChatMessages = true
            //选填参数-function call
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
            NavTalkManager.functionCallListener = object : FunctionCallListener{
                override fun onFunctionCalled(message: String) {
                    println("Function_Call:${message}")
                    val jsonObject = JSONObject(message)
                    val data = jsonObject.optJSONObject("data")
                    val function_name = data.getString("function_name")
                    if (function_name == "function_call_close_talk"){
                        ChatActivity. closeCall()
                    }
                }
            }
            //展示页面
            NavTalkManager.showChatActivity(this)
        }

    }
}