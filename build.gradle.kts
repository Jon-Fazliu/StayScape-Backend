import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4" apply (false)
    // https://plugins.gradle.org/plugin/io.spring.dependency-management
    id("io.spring.dependency-management") version "1.1.0" apply (false)

    // https://mvnrepository.com/artifact/org.jetbrains.kotlin.jvm/org.jetbrains.kotlin.jvm.gradle.plugin
    kotlin("jvm") version "1.9.0" apply (false)
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin.plugin.spring/org.jetbrains.kotlin.plugin.spring.gradle.plugin
    kotlin("plugin.spring") version "1.9.0" apply (false)
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin.plugin.jpa/org.jetbrains.kotlin.plugin.jpa.gradle.plugin
    kotlin("plugin.jpa") version "1.9.0" apply (false)

    // https://plugins.gradle.org/plugin/org.sonarqube
    id("org.sonarqube") version "4.2.1.3168"
    id("jacoco")
    id("io.sentry.jvm.gradle") version "3.11.1"
}

val javaVersion = JavaVersion.VERSION_17
val javaVersionStr = "17"

allprojects {
    apply(plugin = "io.spring.dependency-management")
    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
        resolutionStrategy {
            cacheChangingModulesFor(0, "seconds")
        }
        dependencies {
            // https://mvnrepository.com/artifact/io.swagger.core.v3/swagger-annotations
            dependency("io.swagger.core.v3:swagger-annotations:2.2.2")
            // https://mvnrepository.com/artifact/io.swagger/swagger-annotations
            dependency("io.swagger:swagger-annotations:1.6.7")
            // https://mvnrepository.com/artifact/com.google.code.findbugs/jsr305
            dependency("com.google.code.findbugs:jsr305:3.0.2")
            // https://mvnrepository.com/artifact/org.openapitools/jackson-databind-nullable
            dependency("org.openapitools:jackson-databind-nullable:0.2.3")
            // https://mvnrepository.com/artifact/com.github.scribejava/scribejava-core
            dependency("com.github.scribejava:scribejava-core:8.3.1")
            // https://mvnrepository.com/artifact/jakarta.annotation/jakarta.annotation-api
            dependency("jakarta.annotation:jakarta.annotation-api:2.1.1")

            // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core-jvm
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.4")
            // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-reactor
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")
            // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-reactive
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.6.4")
            // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
            dependency("org.apache.commons:commons-lang3:3.12.0")

            dependency("org.apache.commons:commons-lang3:3.12.0")
            dependency("commons-io:commons-io:2.13.0")

            dependency("io.jsonwebtoken:jjwt-api:0.11.5")
            dependency("io.jsonwebtoken:jjwt-impl:0.11.5")
            dependency("io.jsonwebtoken:jjwt-jackson:0.11.5")

            // https://mvnrepository.com/artifact/org.flywaydb/flyway-mysql
            dependency("org.flywaydb:flyway-mysql:9.22.3")

            // https://mvnrepository.com/artifact/mysql/mysql-connector-java
            dependency("mysql:mysql-connector-java:8.0.33")

            dependency("commons-io:commons-io:2.13.0")
            // https://mvnrepository.com/artifact/io.ktor/ktor-client-okhttp
            dependency("io.ktor:ktor-client-okhttp:2.3.2")
            // https://mvnrepository.com/artifact/org.jsoup/jsoup
            dependency("org.jsoup:jsoup:1.16.1")
            // https://mvnrepository.com/artifact/io.hypersistence/hypersistence-utils-hibernate-60
            dependency("io.hypersistence:hypersistence-utils-hibernate-60:3.5.1")

            // https://mvnrepository.com/artifact/com.github.tomakehurst/wiremock
            // 3.0.0-beta-10: test only so commons-fileupload security issue shouldn't be a problem
            dependency("com.github.tomakehurst:wiremock:3.0.0-beta-10")

            // for Excel format file export
            dependency("org.apache.poi:poi-ooxml:5.2.5")

        }
    }

    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = javaVersion.toString()
            targetCompatibility = javaVersion.toString()
        }

        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = javaVersionStr
            }
        }

        withType<Copy> {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "7.5.1"
    distributionType = Wrapper.DistributionType.BIN
}