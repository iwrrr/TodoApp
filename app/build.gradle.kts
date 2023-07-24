plugins {
    id("android.app")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.noteapp"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":feature:note-list"))
    implementation(project(":feature:note-add-edit"))
}