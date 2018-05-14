package com.llj.lib.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liulj
 */
public class AGsonUtils {
    /**
     * 1.解析成指定对象
     *
     * @param jsonString 里面必须是一个对象
     * @param cls
     * @return
     */
    public static <T> T getObject(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> T getObject(String jsonString, Type type) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 2.
     *
     * @param jsonString
     * @param typeOfT
     * @param <T>
     * @return
     */
    public static <T> List<T> getList(String jsonString, Type typeOfT) {
        List<T> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, typeOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 3.
     *
     * @param context
     * @param fileName
     * @param typeOfT
     * @param <T>
     * @return
     */
    public static <T> List<T> getList(Context context, String fileName, Type typeOfT) {
        String json = AResourceUtils.geFileFromAssets(context, fileName);
        List<T> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(json, typeOfT);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 3.解析成字符串集合
     *
     * @param jsonString
     * @return
     */
    public static List<String> getStringList(String jsonString) {
        if (TextUtils.isEmpty(jsonString))
            return null;
        List<String> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {
        }
        return list;

    }

    /**
     * 4.解析成hashmap
     *
     * @param jsonString
     * @return
     */
    public static Map<String, Object> getHashMap(String jsonString) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            Gson gson = new Gson();
            map = gson.fromJson(jsonString, new TypeToken<HashMap<String, Object>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 5.
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> listKeyMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 返回long类型的集合
     *
     * @param jsonString
     * @return
     */
    public static List<Long> getLongList(String jsonString) {
        List<Long> list = new ArrayList<>();
        if (TextUtils.isEmpty(jsonString)) {
            return list;
        }
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<Long>>() {
            }.getType());
        } catch (Exception e) {
        }
        return list;
    }

    public static Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        }).setDateFormat(DateFormat.LONG).create();
    }

    /**
     * 将一个实体类转换为json字符串
     *
     * @param object
     * @return
     */
    public static String getObject2Json(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getTime());
            }
        }).setDateFormat(DateFormat.LONG);
        return gsonBuilder.create().toJson(object);
    }

    /**
     * 将有@Expose注释的指定字段转换为json字符串
     *
     * @param object
     * @return
     */
    public static String getExposeObject2Json(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getTime());
            }
        }).setDateFormat(DateFormat.LONG);
        return gsonBuilder.excludeFieldsWithoutExposeAnnotation().create().toJson(object);
    }


    public static JsonElement getObject2JsonElement(Object object) {
        Gson gson = new Gson();
        return gson.toJsonTree(object);
    }

}
