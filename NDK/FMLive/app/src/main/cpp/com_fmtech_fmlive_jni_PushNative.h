/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_fmtech_fmlive_jni_PushNative */

#ifndef _Included_com_fmtech_fmlive_jni_PushNative
#define _Included_com_fmtech_fmlive_jni_PushNative
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_fmtech_fmlive_jni_PushNative
 * Method:    fireVideo
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_com_fmtech_fmlive_jni_PushNative_fireVideo
  (JNIEnv *, jobject, jbyteArray);

/*
 * Class:     com_fmtech_fmlive_jni_PushNative
 * Method:    fireAudio
 * Signature: ([BI)V
 */
JNIEXPORT void JNICALL Java_com_fmtech_fmlive_jni_PushNative_fireAudio
  (JNIEnv *, jobject, jbyteArray, jint);

/*
 * Class:     com_fmtech_fmlive_jni_PushNative
 * Method:    startPush
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_fmtech_fmlive_jni_PushNative_startPush
  (JNIEnv *, jobject, jstring);

/*
 * Class:     com_fmtech_fmlive_jni_PushNative
 * Method:    stopPush
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_fmtech_fmlive_jni_PushNative_stopPush
  (JNIEnv *, jobject);

/*
 * Class:     com_fmtech_fmlive_jni_PushNative
 * Method:    release
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_fmtech_fmlive_jni_PushNative_release
  (JNIEnv *, jobject);

/*
 * Class:     com_fmtech_fmlive_jni_PushNative
 * Method:    setVideoOptions
 * Signature: (IIII)V
 */
JNIEXPORT void JNICALL Java_com_fmtech_fmlive_jni_PushNative_setVideoOptions
  (JNIEnv *, jobject, jint, jint, jint, jint);

/*
 * Class:     com_fmtech_fmlive_jni_PushNative
 * Method:    setAudioOptions
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_com_fmtech_fmlive_jni_PushNative_setAudioOptions
  (JNIEnv *, jobject, jint, jint);

#ifdef __cplusplus
}
#endif
#endif
