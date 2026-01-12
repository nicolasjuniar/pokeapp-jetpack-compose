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
        "**/*State*.*",
        "**/*Event*.*",
        "**/*Signal*.*",
        "**/*Content*.*",
        "**/*Screen*.*",
        "**/*BottomSheet*.*"
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

    val coreDataExclude = listOf(
        "**/core/data/api/**",
        "**/core/data/dto/**",
        "**/core/data/datastore/**",
        "**/core/data/local/**",
        "**/core/data/paging/**",
        "**/*FavoriteMapper*.*",
        "**/*UserMapper*.*",
        "**/*ThemeRepositoryImpl*.*",
        "**/*SessionRepositoryImpl*.*"
    )

    val featureLoginExclude = listOf(
        "**/feature/login/di/**"
    )

    val combineExclude = generalExclude +
            coreDiExclude +
            coreCommonExclude +
            coreUiExclude +
            coreDomainExclude +
            coreDataExclude +
            featureLoginExclude

    val classDirs = subprojects.filter { it.name != "app" }
        .map { project ->
            project.layout.buildDirectory.dir("tmp/kotlin-classes/debug").map { dir ->
                project.fileTree(dir) {
                    exclude(combineExclude)
                }
            }
        }

    val sourceDirs = subprojects.filter { it.name != "app" }
        .map { project ->
            listOf(
                "${project.projectDir}/src/main/java",
                "${project.projectDir}/src/main/kotlin"
            )
        }

    val execData = files(
        subprojects.filter { it.name != "app" }
            .map { project ->
                project.layout.buildDirectory.asFileTree.matching {
                    include(
                        "jacoco/testDebugUnitTest.exec",
                        "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec"
                    )
                }
            }
    )

    classDirectories.setFrom(files(classDirs))
    sourceDirectories.setFrom(files(sourceDirs.flatten()))
    executionData.setFrom(files(execData))
}