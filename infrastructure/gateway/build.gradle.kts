object DependencyVersions {
    const val JJWT_VERSION = "0.12.6"
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("io.jsonwebtoken:jjwt-api:${DependencyVersions.JJWT_VERSION}")
    implementation("io.jsonwebtoken:jjwt-jackson:${DependencyVersions.JJWT_VERSION}")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${DependencyVersions.JJWT_VERSION}")

}
