object ApplicationId {
    val id = "com.grivos.podcastsratings"
}

object Modules {
    val app = ":app"
    val cache = ":common:cache"
    val domain = ":common:domain"
    val presentation = ":common:presentation"
    val network = ":common:network"
    val main = ":features:main"
    val accessibilityservice = ":features:accessibilityservice"
    val hover = ":hover"
}

object Releases {
    val versionCode = 2
    val versionName = "1.0.1"
}

object Versions {
    val compileSdk = 29
    val minSdk = 22
    val targetSdk = 29

    val appcompat = "1.1.0"
    val design = "1.1.0-beta02"
    val constraintLayout = "2.0.0-beta3"
    val ktx = "1.2.0-rc01"

    val kotlin = "1.3.61"
    val timber = "4.7.1"
    val rxjava = "2.2.15"
    val rxAndroid = "2.1.1"
    val rxkotlin = "2.4.0"
    val loggingInterceptor = "4.2.2"
    val jsoup = "1.12.1"
    val retrofit = "2.6.2"
    val rxpaper = "1.4.0"
    val lifecycle = "2.2.0-rc02"
    val leakCanary = "2.0-alpha-3"
    val koin = "2.0.1"
    val spanomatic = "1.0.1"
    val viewPump = "2.0.3"
    val spinKit = "1.4.0"
    val coil = "0.8.0"
}

object Libraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val rxjavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    val jsoup = "org.jsoup:jsoup:${Versions.jsoup}"

    val rxpaper = "com.github.pakoito:RxPaper2:${Versions.rxpaper}"
    val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"

    val leakCanaryAndroid = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

    val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    val spanomatic = "com.grivos.spanomatic:spanomatic:${Versions.spanomatic}"
    val viewPump = "io.github.inflationx:viewpump:${Versions.viewPump}"
    val spinKit = "com.github.ybq:Android-SpinKit:${Versions.spinKit}"
    val coil = "io.coil-kt:coil:${Versions.coil}"
}

object SupportLibraries {
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val design = "com.google.android.material:material:${Versions.design}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}