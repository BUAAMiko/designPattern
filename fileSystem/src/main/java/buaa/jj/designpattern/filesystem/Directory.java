package buaa.jj.designpattern.filesystem;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import buaa.jj.designpattern.Iterator.Iterator;
import buaa.jj.designpattern.factory.FileSystemFactory;

public class Directory implements FileSystem {

    private List<FileSystem> childs;
    private FileSystem parent;
    private String name;

    private class FileSystemIterator implements Iterator {

        private Queue<FileSystem> queue = new LinkedList<FileSystem>();
        private File.Type type;

        public FileSystemIterator(Directory root, String type){
            queue.add(root);
            if (type != null)
                this.type = Enum.valueOf(File.Type.class,type);
            else
                this.type = null;
        }

        @Override
        public boolean hasNext() {
            if (type == null)
                return !queue.isEmpty();
            else {
                FileSystem tmp = queue.peek();
                while (tmp != null && tmp.getClass() == File.class && ((File)tmp).getType() != type) {
                    queue.poll();
                    tmp = queue.peek();
                }
                return !queue.isEmpty();
            }
        }

        @Override
        public Object next() {
            FileSystem ret = queue.poll();
            if (type != null) {
                while (ret.getClass() == File.class && ((File)ret).getType() != type) {
                    if (queue.isEmpty())
                        return null;
                    ret = queue.poll();
                }
            }
            if (ret != null && ret.getChilds() != null)
                queue.addAll(ret.getChilds());
            return ret;
        }
    }

    public Directory(String name) {
        this.name = name;
        this.parent = null;
        this.childs = new ArrayList<FileSystem>();
        if (parent != null)
            parent.addChild(this);
    }

    public Directory(String name, FileSystem parent) {
        this.name = name;
        this.parent = parent;
        this.childs = new ArrayList<FileSystem>();
        if (parent != null)
            parent.addChild(this);
    }

    @Override
    public void addChild(FileSystem fileSystem) {
        this.childs.add(fileSystem);
    }

    @Override
    public void addFile(Queue<String> path, FileSystem fileSystem) {
        if (path.isEmpty()) {
            if (fileSystem != null) {
                fileSystem.setParent(this);
                addChild(fileSystem);
            }
            FileSystemFactory.saveFileSystemChange();
        } else {
            String name = path.poll();
            for (FileSystem child : childs) {
                if (child.getName().equals(name)) {
                    child.addFile(path,fileSystem);
                    return;
                }
            }
            FileSystem tmp = new Directory(name,this);
            tmp.setParent(this);
            tmp.addFile(path,fileSystem);
        }
    }

    @Override
    public FileSystem getFile(Queue<String> path, String name) {
        if (path.isEmpty()) {
            for (FileSystem child : childs) {
                if (child.getName().equals(name))
                    return child;
            }
            return null;
        } else {
            String s = path.poll();
            for (FileSystem child : childs) {
                if (child.getName().equals(s)) {
                    return child.getFile(path, name);
                }
            }
            return null;
        }
    }

    @Override
    public void removeFile(Queue<String> path, String name) {
        if (path.isEmpty()) {
            for (FileSystem child : childs) {
                if (child.getName().equals(name)) {
                    childs.remove(child);
                    FileSystemFactory.saveFileSystemChange();
                    return;
                }
            }
            throw new RuntimeException();
        } else {
            String s = path.poll();
            for (FileSystem child : childs) {
                if (child.getName().equals(s)) {
                    child.removeFile(path,name);
                    return;
                }
            }
            throw new RuntimeException();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public FileSystem getParent() {
        return parent;
    }

    @Override
    public void setParent(FileSystem fileSystem) {
        this.parent = fileSystem;
    }

    @Override
    public List<FileSystem> getChilds() {
        return childs;
    }

    public void setChilds(List<FileSystem> childs) {
        this.childs = childs;
    }

    @Override
    public Iterator getIterator(String type) {
        return new FileSystemIterator(this, type);
    }
}
