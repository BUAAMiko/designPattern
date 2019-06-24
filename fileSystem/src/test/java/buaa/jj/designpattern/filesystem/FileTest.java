package buaa.jj.designpattern.filesystem;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.junit.Test;

import buaa.jj.designpattern.factory.FileSystemFactory;

import static org.junit.Assert.*;

public class FileTest {

    @Test
    public void open() {
        File file1 = File.getFile("test.txt");
        file1.open();
    }

    @Test
    public void getFile() {
        FileSystemFactory factory = new FileSystemFactory();
        FileSystemFactory.userId = "1";
        factory.getFileSystem(true);
    }
}