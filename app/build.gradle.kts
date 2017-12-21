plugins {
	id("com.android.application")
	kotlin("android")
	kotlin("android.extensions")
}
android {
	compileSdkVersion(26)
	buildToolsVersion("26.0.3")
	defaultConfig {
		applicationId = "com.steven.wakealarm"
		minSdkVersion(15)
		targetSdkVersion(26)
		versionCode = 1
		versionName = "1.0"
	}

	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			isShrinkResources = false
			proguardFiles("proguard-rules.pro")
		}
		getByName("debug") {
			applicationIdSuffix = ".debug"
		}
	}

	compileOptions {
		setSourceCompatibility(JavaVersion.VERSION_1_8)
		setTargetCompatibility(JavaVersion.VERSION_1_8)
	}
}

dependencies {
	implementation("com.android.support:appcompat-v7:26.1.0")
	implementation("com.takisoft.fix:preference-v7:26.1.0.3")
	implementation("com.takisoft.fix:preference-v7-ringtone:26.1.0.3")
	implementation("com.android.support.constraint:constraint-layout:1.0.2")
	implementation("com.google.zxing:android-integration:3.3.0")
	implementation(kotlin("stdlib-jdk7", "1.2.10"))
}
