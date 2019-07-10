package com.llj.lib.base.compiler.utils;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-11
 */
public class Constants {


    // Java type
    private static final String LANG         = "java.lang";
    public static final  String BYTE         = LANG + ".Byte";
    public static final  String SHORT        = LANG + ".Short";
    public static final  String INTEGER      = LANG + ".Integer";
    public static final  String LONG         = LANG + ".Long";
    public static final  String FLOAT        = LANG + ".Float";
    public static final  String DOUBLE       = LANG + ".Double";
    public static final  String BOOLEAN      = LANG + ".Boolean";
    public static final  String CHAR         = LANG + ".Character";
    public static final  String STRING       = LANG + ".String";
    public static final  String SERIALIZABLE = "java.io.Serializable";
    public static final  String PARCELABLE   = "android.os.Parcelable";

    public static final String NO_MODULE_NAME_TIPS = "These no module name, at 'build.gradle', like :\n" +
            "android {\n" +
            "    defaultConfig {\n" +
            "        ...\n" +
            "        javaCompileOptions {\n" +
            "            annotationProcessorOptions {\n" +
            "                arguments = [AROUTER_MODULE_NAME: project.getName()]\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}\n";


    public static final String KEY_MODULE_NAME = "AROUTER_MODULE_NAME";

}
