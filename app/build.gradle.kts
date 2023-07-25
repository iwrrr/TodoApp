plugins {
    id("android.app")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.todoapp"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":feature:todo-list"))
    implementation(project(":feature:todo-add-edit"))
}