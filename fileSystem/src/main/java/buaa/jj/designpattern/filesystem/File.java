package buaa.jj.designpattern.filesystem;

import java.util.List;
import java.util.Queue;

import buaa.jj.designpattern.Iterator.Iterator;

public abstract class File implements FileSystem {

    private FileSystem parent;
    private String name;

    abstract void download();
    abstract void open();
    abstract void upload();

    @Override
    public void addChild(FileSystem fileSystem) {
        throw new RuntimeException();
    }

    @Override
    public void addFile(Queue<String> path, FileSystem fileSystem) {
        throw new RuntimeException();
    }

    @Override
    public FileSystem getFile(Queue<String> path, String name) {
        throw new RuntimeException();
    }

    @Override
    public void removeFile(Queue<String> path, String name) {
        throw new RuntimeException();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public FileSystem getParent() {
        return parent;
    }

    @Override
    public List<FileSystem> getChilds() {
        return null;
    }

    @Override
    public Iterator getIterator() {
        return null;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setParent(FileSystem parent) {
        this.parent = parent;
    }
}
