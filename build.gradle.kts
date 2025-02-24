
plugins {
	id("application")
}

application {
	mainClass = "dev.atne.convertit.app.Entry"
}


tasks.named<Jar>("jar") {
	manifest {
		attributes["Main-Class"] = "dev.atne.convertit.app.Entry"
	}
}

allprojects {
	plugins.apply("java")

	repositories {
		mavenCentral()
	}
}

dependencies {
	implementation(project(":app"))
	// implementation(files("core/build/libs/core.jar"))
	// implementation(project(":core"))
}

