plugins {
    id("net.neoforged.moddev") version "2.0.141"
    id("com.almostreliable.almostgradle") version "1.5.2"
}

almostgradle.setup {
    withSourcesJar = false
}

neoForge {
    runs {
        configureEach {
            systemProperties = mapOf(
                "guideme.ae2.guide.sources" to file("guidebook").absolutePath,
                "guideme.ae2.guide.sourcesNamespace" to almostgradle.modId,
            )
        }

        create("guide") {
            client()
            systemProperty("guideme.showOnStartup", "ae2:guide!${almostgradle.modId}:${almostgradle.modId}.md")
        }
    }
}

repositories {
    mavenCentral()
    maven("https://modmaven.dev")
    mavenLocal()
}

dependencies {
    api("org.appliedenergistics:appliedenergistics2:${almostgradle.getProperty("aeVersion")}")
    compileOnly("de.mari_023:ae2wtlib_api:${almostgradle.getProperty("wtlibVersion")}") { isTransitive = false }
    localRuntime("de.mari_023:ae2wtlib:${almostgradle.getProperty("wtlibVersion")}") { isTransitive = false }
}

tasks.withType<Jar> {
    from("guidebook") {
        into("assets/${almostgradle.modId}/ae2guide")
    }
}
