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

val webCommonModule = ":web-common"

dependencies {
    api(project(webCommonModule))
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation(project(path = webCommonModule, configuration = "archives"))
}
