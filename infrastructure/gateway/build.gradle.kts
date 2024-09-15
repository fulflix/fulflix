plugins { id("com.google.cloud.tools.jib") version "3.2.0" }

object DependencyVersions {
    const val JJWT_VERSION = "0.12.6"
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:${DependencyVersions.JJWT_VERSION}")
    implementation("io.jsonwebtoken:jjwt-jackson:${DependencyVersions.JJWT_VERSION}")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${DependencyVersions.JJWT_VERSION}")
}

jib {
    from {
        image = "openjdk:17-jdk-slim"
    }
    to {
        image = "${project.name}:latest"
    }
    container {
        mainClass = "io.fulflix.gateway.GatewayApp"
        creationTime = "USE_CURRENT_TIMESTAMP"
    }
}
