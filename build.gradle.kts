
plugins {
	id("application")
}

application {
	mainClass = "dev.atne.convertit.Entry"
}

allprojects {
	plugins.apply("java")

	repositories {
		mavenCentral()
	}
}

dependencies {
	implementation(files("app/build/libs/app.jar"))
}

