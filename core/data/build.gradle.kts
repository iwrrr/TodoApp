plugins {
    id("android.lib")
    id("android.hilt")
}

android {
    namespace = "com.hwaryun.data"
}

dependencies {
    api(project(":core:common"))
    api(project(":core:database"))
}