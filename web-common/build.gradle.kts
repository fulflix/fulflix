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

dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
}
