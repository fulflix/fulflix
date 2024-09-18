plugins {
    `java-library`
}

tasks.named<Jar>("jar") {
    enabled = true
}

val testJar by tasks.registering(Jar::class) {
    enabled = true
    archiveClassifier.set("tests")
    from(sourceSets["test"].output)
}

artifacts {
    add("archives", testJar)
}

val queryDslVersion = dependencyManagement.importedProperties["querydsl.version"]
val webCore = ":web-core"

dependencies {
    api(project(webCore))
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation(project(path = webCore, configuration = "archives"))
}

// query-dsl auditable q-class generate and delete configurations
tasks.withType<JavaCompile>().configureEach {
    options.generatedSourceOutputDirectory.set(file("src/main/generated"))
    options.compilerArgs.addAll(
        listOf(
            "-Aquerydsl.generatedAnnotationClass=javax.annotation.Generated"
        )
    )
}

tasks.named<Delete>("clean") {
    delete(file("src/main/generated"))
}
