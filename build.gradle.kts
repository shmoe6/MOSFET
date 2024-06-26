plugins {
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
    java
}

application.mainClass = "com.musicallyanna.mosfet.Bot" //


group = "org.example"
version = "1.0"

val jdaVersion = "5.0.0-beta.22" //



repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:$jdaVersion")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("io.github.cdimascio:dotenv-java:3.0.0")

    testImplementation("junit:junit:4.13.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.10.2")
}

tasks {
    // Use the native JUnit support of Gradle.
    "test"(Test::class) {
        useJUnitPlatform()
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.isIncremental = true

    // Set this to the version of java you want to use,
    // the minimum required for JDA is 1.8
    sourceCompatibility = "17"
}