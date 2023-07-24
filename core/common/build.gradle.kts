plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.common"
}

dependencies {
    implementation(libs.androidx.ktx)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.bundles.datastore)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
}