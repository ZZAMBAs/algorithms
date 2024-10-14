plugins {
    id("java")
}

group = "org.zzamba"
version = "1.0-SNAPSHOT"

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("io.github.vsfe:boj-boot-plugin:1.0.3")
    }
}

apply(plugin = "io.github.vsfe.boj-boot-plugin")

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.vsfe:boj-commons:1.0.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
