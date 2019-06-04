package buaa.jj.designpattern.factory;

import java.util.Arrays;
import java.util.LinkedList;

import buaa.jj.designpattern.filesystem.Directory;
import buaa.jj.designpattern.filesystem.FileSystem;

public class FileSystemFactory implements AbstractFactory {

    private FileSystem fileSystem;

    @Override
    public FileSystem getFileSystem() {
        if (fileSystem == null) {
            fileSystem = new Directory("root",null);
            LinkedList linkedList = new LinkedList();
            linkedList.addAll(Arrays.asList( "/root/1/2".split("/")));
            fileSystem.addFile(linkedList,null);
        }
        return fileSystem;
    }
}
