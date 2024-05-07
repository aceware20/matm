

pluginManagement {
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
        maven {
            url = uri("https://jitpack.io")
            credentials {
                val authToken="jp_h0pvj9i1tf4j12ljjguuil49ni"
                username=authToken
            }
        }
    }
}

rootProject.name = "matmtest"
include(":app")
include(":matm")
include (":fingpaymicroatm-release")
