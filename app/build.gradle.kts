import AndroidSdk.min
import AndroidSdk.target
import Config.appId

plugins {
    id(BuildPlugins.androidApplication)
    kotlin(BuildPlugins.kotlinAndroid)
    kotlin(BuildPlugins.kotlinKapt)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    buildToolsVersion(AndroidSdk.buildToolsVersion)
    defaultConfig {
        applicationId = appId

        minSdkVersion(min)
        targetSdkVersion(target)
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"${Urls.baseUrl}\"")
        buildConfigField("String", "API_KEY", "\"${Keys.apiKey}\"")

    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = Config.release_minifyEnabled
            isShrinkResources = Config.release_shrinkResources
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = Config.debug_minifyEnabled
            isShrinkResources = Config.debug_shrinkResources
        }
    }

    viewBinding.isEnabled = true

    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.ktxCore)

    implementation(Libraries.appCompat)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.swiperefreshlayout)
    implementation(Libraries.recyclerview)

    implementation(Libraries.materialComponent)
    implementation(Libraries.preference)

    //dagger
    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidCompiler)

    //okhttp
    implementation(Libraries.okhttp)
    androidTestImplementation(TestLibraries.test_okhttp)
    //retrofit
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitRxAdapter)
    //rxJava
    implementation(Libraries.rxAndroid)
    implementation(Libraries.rxjava)
    //picasso
    implementation(Libraries.picasso)
    implementation(Libraries.picassoDownloader)

    implementation(Libraries.gson)
    implementation(Libraries.gsonConverter)

    //viewModel
    implementation(Libraries.ktxLifecycleViewmodel)
    implementation(Libraries.ktxFragment)
    implementation(Libraries.ktxViewModelLifecycleLiveData)
    implementation(Libraries.reactivestreamsViewModel)
    implementation(Libraries.ktxViewModelRuntime)
    kapt(Libraries.ktxViewModelLifeCycleCompiler)

    //room
    implementation(Libraries.aacRoomRuntime)
    implementation(Libraries.accRoomRX)
    kapt(Libraries.kapt_aacRoomCompiler)
    testImplementation(TestLibraries.test_Room)

    //paging
    implementation(Libraries.pagingRuntime)
    implementation(Libraries.pagingRX)

    testImplementation(TestLibraries.paging_text)
    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.test_liveData)
    testImplementation(TestLibraries.extJunit)
    testImplementation(TestLibraries.espresso)

    testImplementation(TestLibraries.mockito)
    debugImplementation(Libraries.stetho)
    debugImplementation(Libraries.stethoNetworkInterceptor)
}