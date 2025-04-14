plugins {
    alias(libs.plugins.runPaper)
    alias(libs.plugins.shadow)

    id("paper-plugin")
}

project.group = "${rootProject.group}.paper"
project.version = rootProject.version

dependencies {
    implementation(libs.triumph.cmds)

    implementation(libs.fusion.paper)
}

tasks {
    processResources {
        inputs.properties("name" to rootProject.name)
        inputs.properties("version" to project.version)
        inputs.properties("group" to project.group)
        inputs.properties("apiVersion" to libs.versions.minecraft.get())
        inputs.properties("description" to project.description)
        inputs.properties("website" to rootProject.properties["website"].toString())

        filesMatching("paper-plugin.yml") {
            expand(inputs.properties)
        }
    }

    assemble {
        dependsOn(shadowJar)

        doLast {
            copy {
                from(shadowJar.get())
                into(rootProject.projectDir.resolve("jars"))
            }
        }
    }

    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")

        //minimize()

        listOf(
            "com.ryderbelserion.fusion.paper",
            "dev.triumphteam",
            "ch.jalu",
        ).forEach {
            relocate(it, "libs.$it")
        }
    }

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }
}