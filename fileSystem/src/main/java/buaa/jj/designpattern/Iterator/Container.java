package buaa.jj.designpattern.Iterator;

public interface Container {
    /**
     * 获取迭代器，根据参数不同迭代不同类型的文件，迭代过程中会返
     * 回中途的文件
     * @param type type应该与File.Type类型下的枚举相对应，如
     *             Document等，如果为null则全种类
     * @return 返回一个迭代特定种类的迭代器
     */
    Iterator getIterator(String type);
}
