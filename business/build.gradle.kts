plugins {
    id("java")
}

group = "com.github.psinalberth"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("jakarta.validation:jakarta.validation-api:2.0.2")
    implementation("org.projectlombok:lombok:1.18.26")
    implementation("io.smallrye.reactive:mutiny:2.1.0")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.glassfish:jakarta.el:3.0.2")
    testImplementation("org.hibernate.validator:hibernate-validator:6.2.3.Final")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.1.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.register<Copy>("copy") {
    from("${projectDir}/src/main/resources/META-INF")
    into("${buildDir}/classes/java/main/META-INF")
}