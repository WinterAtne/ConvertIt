
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
	testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.11.4")
}

