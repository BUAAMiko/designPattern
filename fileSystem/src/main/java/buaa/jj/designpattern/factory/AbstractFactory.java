package buaa.jj.designpattern.factory;

import buaa.jj.designpattern.filesystem.FileSystem;

public interface AbstractFactory {
    FileSystem getFileSystem();
}
