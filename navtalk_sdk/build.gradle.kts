plugins {
    alias(libs.plugins.android.library)
    `maven-publish`
}

android {
    namespace = "com.navtalk.navtalk_sdk"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //布局必要的SDK
    api("androidx.recyclerview:recyclerview:1.3.1")
    api("androidx.constraintlayout:constraintlayout:2.1.4")
    //网络
    //implementation("com.squareup.okhttp3:okhttp:4.12.0")
    api("com.squareup.okhttp3:okhttp:4.12.0")
    //图片加载
    //implementation ("com.github.bumptech.glide:glide:4.11.0")
    //annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")
    api("com.github.bumptech.glide:glide:4.11.0")
    api("com.github.bumptech.glide:compiler:4.11.0")
    //提示语
    //implementation("com.github.GrenderG:Toasty:1.5.2")
    api("com.github.GrenderG:Toasty:1.5.2")
    //WebRTC
    //implementation("com.mesibo.api:webrtc:1.0.5") //有问题--界面显示问题
    //implementation("com.mesibo.api:webrtc:1.1.10")//有问题--重新Call问题
    //implementation("io.github.webrtc-sdk:android:137.7151.05")
    api("io.github.webrtc-sdk:android:137.7151.05")
    //摄像头预览和截取帧画面相关
    // CameraX 核心库
    //implementation("androidx.camera:camera-core:1.2.3")
    //implementation("androidx.camera:camera-camera2:1.2.3")
    api("androidx.camera:camera-core:1.2.3")
    api("androidx.camera:camera-camera2:1.2.3")
    // CameraX 预览用例和生命周期绑定
    //implementation("androidx.camera:camera-lifecycle:1.2.3")
    //implementation("androidx.camera:camera-view:1.2.3")
    api("androidx.camera:camera-lifecycle:1.2.3")
    api("androidx.camera:camera-view:1.2.3")
}
// 上传源码包的任务
tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}
//声明release
extensions.configure<com.android.build.api.dsl.LibraryExtension> {
    publishing {
        singleVariant("release") {}
    }
}
// afterEvaluate 保证 Android plugin 配置完成后再注册发布任务
afterEvaluate {
    extensions.configure<org.gradle.api.publish.PublishingExtension>("publishing") {
        //当前依赖库的版本号，方便我们开发者自己查看，同时发布到 MavenLocal 也是用的这个版本号（Jitpack不会使用到）
        val versionName = "1.0.5"
        publications {
            create<MavenPublication>("release") {
                //自定义属性：这里头是artifacts的配置信息，不填会采用默认的
                groupId = "com.navtalk"           // 依赖库的 Group Id（Jitpack不会使用到）
                artifactId = "navtalk_sdk"        // 依赖库的名称，单组件发布时随意填写，多组件时即为此组件的 ArtifactId（Jitpack不会使用到）
                version = versionName             // 当前SDK库的版本号
                //增加上传源码的 task
                artifact(tasks.named("sourcesJar"))
                //必须有这个 否则不会上传AAR包  将bundleReleaseAar任务的单个输出文件作为发布的AAR文件。
                //这样，该AAR文件就会成为 MavenPublication 的 artifact 并进行发布
                from(components["release"])
            }
        }
    }
}


