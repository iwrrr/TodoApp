pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "TodoApp"
include(":app")
include(":core:common")
include(":core:design-system")
include(":core:database")
include(":core:data")
include(":core:domain")
include(":feature:todo-list")
include(":feature:todo-add-edit")
