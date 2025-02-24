
tasks.named<Jar>("jar") {
	manifest {
		attributes["Main-Class"] = "dev.atne.convertit.app.Entry"
	}
}

dependencies {
	implementation(project(":core"))
}

