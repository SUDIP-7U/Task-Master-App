![sanse](https://github.com/user-attachments/assets/2a760c21-d25f-4795-9511-5f291b958b42)

   
   
   buildFeatures {
       viewBinding = true
   }
}


//id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false


//id("com.google.devtools.ksp")


dependencies {


   implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0")


   val room_version = "2.7.1"


   implementation("androidx.room:room-runtime:$room_version")


   ksp("androidx.room:room-compiler:$room_version")


