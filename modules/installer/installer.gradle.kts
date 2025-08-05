import com.install4j.gradle.Install4jTask

plugins {
    id("com.install4j.gradle") version "11.0.5"
}

val winCertPath: String? by project
val macCertPath: String? by project
val macProvisioningProfile: String? by project
val appStoreOnly = project.findProperty("appStoreOnly").toString().toBoolean()
val appleIssuerId: String? by project
val appleKeyId: String? by project
val applePrivateApiKey: String? by project
val digestSigningCommandLine: String? by project

tasks {
    register<Delete>("clean") {
        delete(rootProject.file("media"))
    }

    register<Install4jTask>("media") {
        dependsOn(":dist", ":clean")

        inputs.dir(rootProject.file("modules"))
        inputs.files(rootProject.file("license"))

        group = "Build"
        description = "Build all media files"

        projectFile = file("resources/jclasslib.install4j")
        release = version as String
        disableSigning = !project.hasProperty("macCertPath")

        if (appleIssuerId == null || appleKeyId == null || applePrivateApiKey == null) {
            disableNotarization = true
        }

        variables = mapOf(
            "winCertPath" to (winCertPath ?: ""),
            "macCertPath" to (macCertPath ?: ""),
            "macProvisioningProfile" to (macProvisioningProfile ?: ""),
            "digestSigningCommandLine" to (digestSigningCommandLine ?: ""),
            "appleIssuerId" to (appleIssuerId ?: ""),
            "appleKeyId" to (appleKeyId ?: ""),
            "applePrivateApiKey" to (applePrivateApiKey ?: ""),
        )

        if (appStoreOnly) {
            buildIds = listOf("2047307322")
        }
    }
}
