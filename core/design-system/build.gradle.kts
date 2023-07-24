plugins {
    id("android.lib")
}

android {
    namespace = "com.hwaryun.design_system"
}

dependencies {
    api(platform(libs.compose.bom))

    api(libs.androidx.ktx)

    api(libs.lifecycle.runtime.ktx)
    api(libs.lifecycle.runtime.compose)

    api(libs.activity.compose)

    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling)
    api(libs.ui.tooling.preview)

    api(libs.material)
    api(libs.material.icons)
    api(libs.material.placeholder)

    api(libs.navigation.animation)
    api(libs.navigation.compose)
    api(libs.navigation.runtime.ktx)

    api(libs.systemuicontroller)
    api(libs.coil)

    debugApi(libs.ui.tooling)
    debugApi(libs.ui.test.manifest)
}