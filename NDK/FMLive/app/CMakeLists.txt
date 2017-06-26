# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.


# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.


# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

#======================================================================
set(PROJECT_DIR /Users/mtelnet/Documents/CHIANG/CHIANG_PROS/NDK/FMLive/app)

add_library(x264 STATIC IMPORTED)
set_target_properties(x264 PROPERTIES IMPORTED_LOCATION ${PROJECT_DIR}/src/main/jniLibs/libx264.a )

add_library(faac STATIC IMPORTED)
set_target_properties(faac PROPERTIES IMPORTED_LOCATION ${PROJECT_DIR}/src/main/jniLibs/libfaac.a )

add_library(rtmpdump STATIC IMPORTED)
set_target_properties(rtmpdump PROPERTIES IMPORTED_LOCATION ${PROJECT_DIR}/src/main/jniLibs/librtmp.a )

add_library(FMLive SHARED  src/main/cpp/FMLive.c  src/main/cpp/queue.c)

include_directories(src/main/cpp src/main/cpp/faac/include/ src/main/cpp/rtmpdump/include/ src/main/cpp/x264/include/)


find_library(log-lib log)

target_link_libraries( FMLive x264 faac rtmpdump ${log-lib})