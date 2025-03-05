
dependencies {
	testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.json:json:20250107")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

