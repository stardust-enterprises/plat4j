plugins {
    `java-library`
    id("org.jetbrains.dokka") version "1.+"
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.0.+"
    id("io.freefair.lombok") version "+"
}

group = "enterprises.stardust"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io") { // temporary
        content {
            includeGroup("com.github.stardust-enterprises")
        }
    }
}

dependencies {
    implementation("com.github.stardust-enterprises", "interstellair", "fdf3c22d66")

    implementation("net.java.dev.jna:jna:5.13.0")

    implementation("org.jetbrains:annotations:24.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

java {
    withSourcesJar()
    withJavadocJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks {
    getByName<Test>("test") {
        useJUnitPlatform()
    }

    val apiJar = create("apiJar", Jar::class.java) {
        archiveClassifier.set("api")
        from(sourceSets["main"].output)
        exclude("enterprises/stardust/plat4j/impl")
        exclude("enterprises/stardust/plat4j/impl/**/*")
        manifest.attributes(
            "Specification-Title" to project.name,
            "Specification-Version" to "1",
            "Specification-Vendor" to "Stardust Enterprises",
        )
    }

    jar {
        manifest.attributes += apiJar.manifest.attributes
        manifest.attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to "Stardust Enterprises",
        )
    }
}

artifacts {
    archives(tasks["apiJar"])
}
