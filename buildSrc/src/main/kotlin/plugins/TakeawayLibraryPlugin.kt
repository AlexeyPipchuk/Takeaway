package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import config.Application
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin

@Deprecated("Не понятно, как заюзать с Gradle DSL")
class TakeawayLibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.apply(LibraryPlugin::class.java)
        project.extensions.configure<LibraryExtension> {
            compileSdkVersion(Application.maxApi)
            buildToolsVersion(Application.tools)

            defaultConfig {
                minSdkVersion(Application.minApi)
                targetSdkVersion(Application.maxApi)
                versionCode = Application.versionCode
                versionName = Application.versionName
                missingDimensionStrategy("full", "develop", "live")
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
        }
    }
}