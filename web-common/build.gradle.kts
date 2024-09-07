plugins {
    `java-library`
}

val jar: Jar by tasks
jar.enabled = true

val testJar by tasks.registering(Jar::class) {
    enabled = true
    archiveClassifier.set("tests")
    from(sourceSets["test"].output)
}

artifacts {
    add("archives", testJar)
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
}
