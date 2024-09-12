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

val webCore = ":web-core"

dependencies {
    api(project(webCore))
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.cloud:spring-cloud-starter-openfeign")

    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation(project(path = webCore, configuration = "archives"))
}
