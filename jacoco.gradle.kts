tasks.register<JacocoReport>("jacocoAggregateReport") {

    dependsOn(
        subprojects.mapNotNull {
            it.tasks.findByName("testDebugUnitTest")
        }
    )

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val generalExclude = listOf(
        "**/R.class",
        "**/BuildConfig.*",
        "**/*Test*.*",
        "**/*Preview*.*",
    )

    val appExclude = listOf(
        "**/app/**"
    )

    val coreDiExclude = listOf(
        "**/core/di/**"
    )

    val coreCommonExclude = listOf(
        "**/core/common/**"
    )

    val coreUiExclude = listOf(
        "**/core/ui/**"
    )

    val coreDomainExclude = listOf(
        "**/core/domain/model/**",
        "**/core/domain/repository/**"
    )

    val combineExclude = generalExclude +
            appExclude +
            coreDiExclude +
            coreCommonExclude +
            coreUiExclude +
            coreDomainExclude

    val classDirs = subprojects.map { project ->
        fileTree("${project.buildDir}/tmp/kotlin-classes/debug") {
            exclude(combineExclude)
        }
    }

    val sourceDirs = subprojects.map { project ->
        listOf(
            "${project.projectDir}/src/main/java",
            "${project.projectDir}/src/main/kotlin"
        )
    }

    val execData = subprojects.map { project ->
        fileTree(project.buildDir) {
            include(
                "jacoco/testDebugUnitTest.exec",
                "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec"
            )
        }
    }

    classDirectories.setFrom(files(classDirs))
    sourceDirectories.setFrom(files(sourceDirs.flatten()))
    executionData.setFrom(files(execData))
}