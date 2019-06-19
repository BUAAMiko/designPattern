package buaa.jj.designpattern.factory;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import buaa.jj.designpattern.filesystem.Directory;
import buaa.jj.designpattern.filesystem.File;
import buaa.jj.designpattern.filesystem.FileSystem;

public class FileSystemFactory {

    private static FileSystem fileSystem;
    private final static String savePath = "/home/jj/file/";

    public FileSystem getFileSystem(String userId, boolean state) {
        if (state || fileSystem == null) {
            fileSystem = new Directory(userId,null);
            java.io.File file = new java.io.File(savePath + userId);
            if (file.isFile())
                file2Object(userId);
            else
                saveFileSystemChange();
        }
        return fileSystem;
    }

    public void file2Object(String userId) throws JSONException {
        java.io.File file = new java.io.File(savePath + userId);
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
        Queue<JSONObject> queue = new LinkedList<JSONObject>(JSONObject.fromObject(json.toString()).getJSONArray("childs"));
        LinkedList path = new LinkedList();
        while (!queue.isEmpty()) {
            JSONObject tmp = queue.poll();
            if (tmp.has("path")) {
                path = new LinkedList();
                path.addAll(tmp.getJSONArray("path"));
            } else {
                if (tmp.has("suffix")) {
                    File tmpfile = new File(tmp.getString("name"),tmp.getString("suffix"),Enum.valueOf(File.Type.class,tmp.getString("type")));
                    fileSystem.addFile(path,tmpfile);
                } else {
                    path.add(tmp.getString("name"));
                    JSONObject tmp1 = new JSONObject();
                    tmp1.put("path",path);
                    fileSystem.addFile((Queue<String>) path.clone(),null);
                    path.pollLast();
                    queue.add(tmp1);
                    queue.addAll(tmp.getJSONArray("childs"));
                }
            }
        }
    }

    private static void object2File(String userId) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.setExcludes(new String[]{"parent"});
        java.io.File file = new java.io.File(savePath + userId);
        String json = JSONObject.fromObject(fileSystem,jsonConfig).toString();
        try {
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
