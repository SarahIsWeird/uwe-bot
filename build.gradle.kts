import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    id("com.google.devtools.ksp") version "1.6.21-1.0.6"
}

group = "com.sarahisweird"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    // https://discord.com/api/oauth2/authorize?client_id=998677843275956244&permissions=8&scope=bot
}

dependencies {
    val discordKtVersion: String by project

    val exposedVersion: String by project
    val mysqlConnectorVersion: String by project
    val hikariVersion: String by project

    val okHttpVersion: String by project

    val moshiVersion: String by project

    implementation("me.jakejmattson:DiscordKt:$discordKtVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("mysql:mysql-connector-java:$mysqlConnectorVersion")
    implementation("com.zaxxer:HikariCP:$hikariVersion")

    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")

    implementation("com.squareup.moshi:moshi:$moshiVersion")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"

    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}