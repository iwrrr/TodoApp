plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.note_add_edit"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:domain"))
}