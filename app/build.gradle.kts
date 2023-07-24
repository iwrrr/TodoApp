plugins {
    id("android.app")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.noteapp"
}

dependencies {
    implementation(project(":core:design-system"))
}