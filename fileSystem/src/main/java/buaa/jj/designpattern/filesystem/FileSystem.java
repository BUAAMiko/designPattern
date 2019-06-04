package buaa.jj.designpattern.filesystem;

import java.util.List;
import java.util.Queue;

import buaa.jj.designpattern.Iterator.Container;

public interface FileSystem extends Container {
    void addChild(FileSystem fileSystem);
    void addFile(Queue<String> path, FileSystem fileSystem);
    FileSystem getFile(Queue<String> path,String name);
    void removeFile(Queue<String> path, String name);
    String getName();
    void setName(String name);
    FileSystem getParent();
    void setParent(FileSystem fileSystem);
    List<FileSystem> getChilds();
}
