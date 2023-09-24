plugins {
    id("groovy")
}

group = "com.helpchoice.kotlin"
version = "2.1.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.groovy:groovy:4.0.15")
    implementation("com.helpchoice.kotlin:KUrlet-jvm:2.1.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}