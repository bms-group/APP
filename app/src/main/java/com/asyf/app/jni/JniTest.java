package com.asyf.app.jni;

/**
 * Created by Administrator on 2018/4/10.
 */

public class JniTest {
    /**
     * 配置加载的so库的文件名字===>如 ：libmyLib.so
     */
    static {
        //加载so库
        System.loadLibrary("myLib");
    }

    public native String getStrFromC();
}
