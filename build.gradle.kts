
plugins {
	java
}

tasks.named<Jar>("jar") {
	manifest {
		attributes["Main-Class"] = "dev.atne.convertit.Entry"
	}
}

repositories {
	mavenCentral()
}

dependencies {
	testImplementation("junit:junit:4.13.2")
}
