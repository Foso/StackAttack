plugins{
    id("com.android.application")
}


android {
    lint {
        baseline = file("lint-baseline.xml")
    }
    compileSdkVersion(16)
    namespace = "com.wagoodman.stackattack"
    defaultConfig{
        multiDexEnabled = true
        minSdkVersion(9)
        targetSdkVersion(14)
    }
    buildTypes {
        debug {

        }
        release {

        }
    }

}

dependencies{
    implementation(project(":StackAttackLibrary"))
    implementation("com.android.support:multidex:1.0.3")
}
