plugins {
    id("java")
}

group = "com.helpchoice.kotlin"
version = "2.1.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.helpchoice.kotlin:KUrlet-jvm:2.1.1")
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
