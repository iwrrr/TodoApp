plugins {
    id("android.app")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.noteapp"
}

dependencies {
    implementation(platform(libs.compose.bom))

    implementation(libs.androidx.ktx)

    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)

    implementation(libs.activity.compose)

    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)

    implementation(libs.material)
    implementation(libs.material.icons)
    implementation(libs.material.placeholder)

    implementation(libs.navigation.animation)
    implementation(libs.navigation.compose)
    implementation(libs.navigation.runtime.ktx)

    implementation(libs.systemuicontroller)
    implementation(libs.coil)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}