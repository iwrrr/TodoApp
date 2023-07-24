plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.data"
}

dependencies {
    api(project(":core:database"))

    implementation(libs.converter.gson)
}