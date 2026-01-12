import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp.android) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.jacoco)
}

jacoco {
    toolVersion = libs.versions.jacoco.get()
}

apply(from = "$rootDir/jacoco.gradle.kts")

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    extensions.configure<DetektExtension> {

        toolVersion = "1.23.8"

        buildUponDefaultConfig = true
        allRules = false
        autoCorrect = true
        ignoreFailures = true
        config.setFrom("$rootDir/detekt.yml")

        basePath = rootDir.absolutePath
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        setSource(
            files(
                "src/main/java",
                "src/main/kotlin"
            )
        )
        exclude(
            "**/build/**",
            "**/generated/**"
        )
        outputs.upToDateWhen { false }
        reports {
            html.required.set(true)
            sarif.required.set(true)
        }
    }
}
