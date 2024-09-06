val jar: Jar by tasks
jar.enabled = true

plugins {
    `java-library`
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
}
