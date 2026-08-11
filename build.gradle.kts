plugins {
    id("java")
}

group = "com.longx.intelligent.lib.ltxt"
version = "1.0"

repositories {
    maven {
        name = "central-aliyun"
        url = uri("https://maven.aliyun.com/repository/central")
    }
    maven {
        name = "jcenter-central-aliyun"
        url = uri("https://maven.aliyun.com/repository/public")
    }
    maven {
        name = "google-aliyun"
        url = uri("https://maven.aliyun.com/repository/google")
    }
    mavenCentral()
    maven {
        name = "JetBrainsReleases"
        url = uri("https://www.jetbrains.com/intellij-repository/releases")
    }
    maven("https://cache-redirector.jetbrains.com/intellij-dependencies")
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}