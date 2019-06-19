package buaa.jj.designpattern.filesystem;

import java.util.List;
import java.util.Queue;

import buaa.jj.designpattern.Iterator.Container;

public interface FileSystem extends Container {
    void addChild(FileSystem fileSystem);

    /**
     * 新增一个文件，途中不存在的文件夹会自动新建，根目录为用户id名，根目录
     * 没有父文件夹，根目录下为一级目录，一级目录名为文件发送者的用户id名，
     * 此结构为基础结构，默认情况下用户a向本用户发送一个文件只需要通过File的
     * getFile方法初始化文件然后路径设置为用户a的用户id即可
     * @param path 调用的文件夹和目标所在文件夹之间的相对路径，不包括调用文件
     *             夹的文件名
     * @param fileSystem null则不新建文件只新建中途目录，非null则在路径
     *                   指向的文件夹下新建文件
     */
    void addFile(Queue<String> path, FileSystem fileSystem);

    /**
     * 获取到一个文件，如果文件不存在会抛出异常，也可以返回目录
     * @param path 调用的文件夹和目标所在文件夹之间的相对路径，不包括调用文件
     *             夹的文件名
     * @param name 文件的文件名
     * @return 返回结果，如果不存在返回异常
     */
    FileSystem getFile(Queue<String> path,String name);

    /**
     * 移除一个文件，也可以是文件夹，如果为文件夹其下所有的文件都会被移除，本地文件
     * 不会被删除
     * @param path 调用的文件夹和目标所在文件夹之间的相对路径，不包括调用文件
     *             夹的文件名
     * @param name 文件的文件名
     */
    void removeFile(Queue<String> path, String name);

    //！！以上内容如果实例是File而不是Directory则均会抛出异常

    String getName();
    void setName(String name);
    FileSystem getParent();
    void setParent(FileSystem fileSystem);
    List<FileSystem> getChilds();
}