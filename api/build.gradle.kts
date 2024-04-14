plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")

    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("kapt")

    jacoco
}

group = "com.stayscape.backend"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    implementation("io.jsonwebtoken:jjwt-api")
    implementation("io.jsonwebtoken:jjwt-impl")
    implementation("io.jsonwebtoken:jjwt-jackson")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.apache.commons:commons-pool2")
    implementation("commons-io:commons-io")
    implementation("org.freemarker:freemarker")

    developmentOnly("org.springframework.boot:spring-boot-devtools")


    // https://mvnrepository.com/artifact/org.flywaydb/flyway-mysql
    implementation("org.flywaydb:flyway-mysql")
    implementation("mysql:mysql-connector-java")

    implementation("io.hypersistence:hypersistence-utils-hibernate-60")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")

    implementation("org.apache.poi:poi-ooxml")
}


tasks {

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    withType<Wrapper> {
        // https://gradle.org/releases/
        gradleVersion = "8.2.1"
        distributionType = Wrapper.DistributionType.BIN
    }

    withType<Copy> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        this.archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
        exclude(
            "application-secure.yml",
            "application-prod.yml"
        )
    }
}