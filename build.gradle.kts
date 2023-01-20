import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    kotlin("kapt") version "1.7.21"
    kotlin("plugin.jpa") version "1.7.21" apply false
    kotlin("plugin.spring") version "1.7.21" apply false
    kotlin("plugin.allopen") version "1.7.21" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0" apply false
    id("org.springframework.boot") version "2.7.5" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
}

allprojects {
    group = "com.skplanet"
    version = "1.0.0"

    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    project(":seller") {
        subprojects {
            apply {
                plugin("com.skplanet.convention.domain-module")
            }

            extensions.configure(AllOpenExtension::class.java) {
                annotations("javax.persistence.Entity")
                annotations("javax.persistence.Embeddable")
                annotations("javax.persistence.MappedSuperClass")
            }

            dependencies {
                api(project(":modules:common"))
                implementation ("org.mapstruct:mapstruct:1.3.1.Final")
                annotationProcessor ("org.mapstruct:mapstruct-processor:1.3.1.Final")
                testImplementation("com.ninja-squad:springmockk:4.0.0")
            }
        }
    }

    project(":camapaign") {
        subprojects {
            apply {
                plugin("com.skplanet.convention.domain-module")
            }

            extensions.configure(AllOpenExtension::class.java) {
                annotations("javax.persistence.Entity")
                annotations("javax.persistence.Embeddable")
                annotations("javax.persistence.MappedSuperClass")
            }

            dependencies {
                api(project(":modules:common"))
                implementation ("org.mapstruct:mapstruct:1.3.1.Final")
                annotationProcessor ("org.mapstruct:mapstruct-processor:1.3.1.Final")
                testImplementation("com.ninja-squad:springmockk:4.0.0")
            }
        }
    }

}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}