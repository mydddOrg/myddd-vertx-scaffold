import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//使用新的kotlin compile
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        useIR = true
    }
}

plugins {
    java
    kotlin("jvm") version "1.4.32"
}

group = "org.myddd"
version = "1.0.0-SNAPSHOT"
extra["myddd_vertx_version"] = "1.0.0-SNAPSHOT"
extra["vertx_version"] = "4.0.3"
extra["version"] = version

extra["log4j_version"] = "2.14.0"
extra["jackson_version"] = "2.12.1"
extra["javax_persistence_version"] = "2.2.1"
extra["mockito_version"] = "3.7.7"
extra["junit5_version"] = "5.7.1"
extra["hibernate_core_version"] = "5.4.28.Final"

allprojects {
    repositories {
        maven {
            setUrl("https://maven.myddd.org/releases/")
        }
        maven {
            setUrl("https://maven.myddd.org/snapshots/")
        }

        maven {
            setUrl("https://maven.aliyun.com/repository/public/")
        }
        maven {
            setUrl("https://maven.aliyun.com/repository/spring/")
        }

        mavenCentral()

    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            useIR = true
        }
    }
}

repositories {
    maven {
        setUrl("https://maven.myddd.org/releases/")
    }
    maven {
        setUrl("https://maven.myddd.org/snapshots/")
    }

    maven {
        setUrl("https://maven.aliyun.com/repository/public/")
    }
    maven {
        setUrl("https://maven.aliyun.com/repository/spring/")
    }

    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}