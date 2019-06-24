package buaa.jj.designpattern.factory;

import android.os.Environment;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import buaa.jj.designpattern.filesystem.Directory;
import buaa.jj.designpattern.filesystem.File;
import buaa.jj.designpattern.filesystem.FileSystem;

public class FileSystemFactory {

    private static FileSystem fileSystem;
    public static String savePath;
    public static String userId = "";

    /**
     * 获取文件实例
     * @param state 是否要强制重新初始化，或者获取已实例化的对象，推荐切换用户时强制初始化
     * @return 返回构建好的文件系统实例
     */
    public FileSystem getFileSystem(boolean state) {
        if (state || fileSystem == null) {
            if (userId.equals(""))
                throw new RuntimeException();
            fileSystem = new Directory(userId,null);
            java.io.File file = new java.io.File(savePath + "/" + userId);
            if (file.isFile())
                file2Object(userId);
            else
                saveFileSystemChange();
        }
        return fileSystem;
    }

    public void file2Object(String userId) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        java.io.File file = new java.io.File(savePath + "/" + userId);
        StringBuilder json = new StringBuilder();
        try {
            if (!file.isFile())
                file.createNewFile();
            FileReader fileReader = new FileReader(file);
            int ch = 0;
            while ((ch = fileReader.read()) != -1) {
                json.append((char) ch);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LinkedList<JsonObject> queue = new LinkedList<JsonObject>();
        JsonObject jsonObject = new JsonParser().parse(json.toString()).getAsJsonObject();
        for (JsonElement object : jsonObject.get("childs").getAsJsonArray()) {
            queue.add(object.getAsJsonObject());
        }
        LinkedList path = new LinkedList();
        while (!queue.isEmpty()) {
            JsonObject tmp = queue.poll();
            if (tmp.has("path")) {
                path = new LinkedList<String>();
                for (JsonElement object : tmp.get("path").getAsJsonArray()) {
                    path.add(object.getAsString());
                }
            } else {
                if (tmp.has("suffix")) {
                    File tmpfile = new File(tmp.get("name").getAsString(),tmp.get("suffix").getAsString(),Enum.valueOf(File.Type.class,tmp.get("type").getAsString()));
                    fileSystem.addFile(path,tmpfile);
                } else {
                    path.add(tmp.get("name").getAsString());
                    JsonObject tmp1 = new JsonObject();
                    tmp1.add("path", gson.toJsonTree(path));
                    fileSystem.addFile((Queue<String>) path.clone(),null);
                    path.pollLast();
                    queue.add(tmp1);
                    for (JsonElement object : tmp.get("childs").getAsJsonArray()) {
                        queue.add(object.getAsJsonObject());
                    }
                }
            }
        }
    }

    private static void object2File(String userId) {
        java.io.File file = new java.io.File(savePath + "/" + userId);
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                if (f.getName().equals("parent"))
                    return true;
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
        String json = gson.toJson(fileSystem);
        try {
            if (!file.isFile())
                file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFileSystemChange() {
        String userId = fileSystem.getName();
        object2File(userId);
    }
}
