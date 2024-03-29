import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
	kotlin("kapt") version "1.8.22"
}

group = "com.teamsparta"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

//configurations {
//	compileOnly {
//		extendsFrom(configurations.annotationProcessor.get())
//	}
//}

repositories {
	mavenCentral()
}

val queryDslVersion = "5.0.0"

dependencies {
	// WEB
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	// DB
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	// MAIL
	implementation("org.springframework.boot:spring-boot-starter-mail:3.2.2")
	// REFLECTION
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	// SECURITY
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt-api:0.12.3")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")
	// SWAGGER
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	// TEST
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	// VALIDATION
	implementation("org.springframework.boot:spring-boot-starter-validation:3.2.2")
	// THYMELEAF
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.2.2")
	// QUERYDSL
	implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
	kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
