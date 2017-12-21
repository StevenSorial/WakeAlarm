buildscript {
	repositories {
		google()
		jcenter()
	}
	dependencies{
		classpath("com.android.tools.build:gradle:3.0.1")
		classpath(kotlin("gradle-plugin", "1.2.10"))
	}
}
allprojects {
	repositories {
		google()
		jcenter()
	}
}
