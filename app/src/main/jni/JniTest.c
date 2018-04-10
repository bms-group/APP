#include <JniTest.h>

JNIEXPORT jstring
JNICALL
Java_com_asyf_app_jni_JniTest_getStrFromC(JNIEnv *env,jclass clazz,jobject instance){
    return (*env)->NewStringUTF(env,"I am from native C");
}