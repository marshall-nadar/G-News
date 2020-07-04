object Config {
    const val appId = "com.adam.gnews"

    const val debug_minifyEnabled = false
    const val debug_shrinkResources = false

    const val release_minifyEnabled = true
    const val release_shrinkResources = true

}

const val kotlinVersion = "1.3.72"

object BuildPlugins {
    private object Versions {
        const val androidBuildToolsVersion = "3.6.3"
    }

    const val androidGradlePlugin =
        "com.android.tools.build:gradle:${Versions.androidBuildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "android"
    const val kotlinKapt = "kapt"

}

object AndroidSdk {
    const val min = 19
    const val compile = 29
    const val target = compile
    const val buildToolsVersion = "29.0.3"
}

object Urls {
    const val baseUrl = "https://newsapi.org/"
}

object Keys {
    const val apiKey = "60a1e5a61143401f8d26423f0463f984"
}

object Libraries {
    private object Versions {
        const val jetpack = "1.1.0"
        const val constraintLayout = "1.1.3"
        const val swiperefreshlayout = "1.0.0"
        const val recyclerview = "1.1.0"
        const val materialComponent = "1.1.0"
        const val preference = "1.1.0"
        const val ktx = "1.1.0"
        const val ktxFragment = "1.2.4"
        const val dagger = "2.25.2"
        const val okhttp = "4.2.2"
        const val retrofit = "2.6.3"
        const val picasso = "2.5.2"
        const val rxjava = "2.2.15"
        const val rxAndroid = "2.1.1"
        const val gson = "2.8.6"
        const val gsonConverter = "2.6.3"
        const val retrofitRxAdapter = "2.6.3"
        const val picassoDownloader = "1.1.0"
        const val ktxLifecycleViewmodel = "2.2.0"
        const val reactivestreamsViewModel = ktxLifecycleViewmodel
        const val ktxViewModelRuntime = ktxLifecycleViewmodel
        const val ktxViewModelLifeCycleCompiler = ktxLifecycleViewmodel
        const val ktxViewModelLifecycleLiveData = ktxLifecycleViewmodel
        const val pagingRuntime = "2.1.2"
        const val pagingRX = pagingRuntime
        const val stetho = "1.5.0"


        const val aacRoom = "2.2.5"
        const val accRoomRX = aacRoom

    }

    const val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    const val stethoNetworkInterceptor = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.jetpack}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val swiperefreshlayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val materialComponent =
        "com.google.android.material:material:${Versions.materialComponent}"
    const val preference = "androidx.preference:preference-ktx:${Versions.preference}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    const val ktxFragment = "androidx.fragment:fragment-ktx:${Versions.ktxFragment}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroidCompiler =
        "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
    const val picassoDownloader =
        "com.jakewharton.picasso:picasso2-okhttp3-downloader:${Versions.picassoDownloader}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    const val retrofitRxAdapter =
        "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitRxAdapter}"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}"
    const val reactivestreamsViewModel =
        "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.reactivestreamsViewModel}"
    const val ktxViewModelLifecycleLiveData =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.ktxViewModelLifecycleLiveData}"
    const val ktxViewModelLifeCycleCompiler =
        "androidx.lifecycle:lifecycle-compiler:${Versions.ktxViewModelLifeCycleCompiler}"
    const val ktxLifecycleViewmodel =
        "androidx.lifecycle:lifecycle-extensions:${Versions.ktxLifecycleViewmodel}"
    const val ktxViewModelRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ktxViewModelRuntime}"

    const val pagingRuntime = "androidx.paging:paging-runtime-ktx:${Versions.pagingRuntime}"
    const val pagingRX = "androidx.paging:paging-rxjava2-ktx:${Versions.pagingRX}"

    const val aacRoomRuntime = "androidx.room:room-runtime:${Versions.aacRoom}"
    const val accRoomRX = "androidx.room:room-rxjava2:${Versions.accRoomRX}"
    const val kapt_aacRoomCompiler = "androidx.room:room-compiler:${Versions.aacRoom}"
}

object TestLibraries {
    private object Versions {
        const val junit4 = "4.12"
        const val extJunit = "1.1.1"
        const val test_okhttp = "4.2.2"
        const val test_liveData = "2.1.0"
        const val espresso = "3.1.0"
        const val testAacRoom = "2.2.2"
        const val mockito = "1.+"
        const val paging_text = "2.1.2"
    }

    const val junit4 = "junit:junit:${Versions.junit4}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    const val test_okhttp = "com.squareup.okhttp3:mockwebserver:${Versions.test_okhttp}"
    const val test_liveData = "androidx.arch.core:core-testing:${Versions.test_liveData}"
    const val paging_text = "androidx.paging:paging-common-ktx:${Versions.paging_text}"
    const val espresso =
        "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val test_Room = "androidx.room:room-testing:${Versions.testAacRoom}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"

}