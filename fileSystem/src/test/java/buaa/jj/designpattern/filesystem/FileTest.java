package buaa.jj.designpattern.filesystem;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileTest {

    @Test
    public void open() {
        File file1 = File.getFile("test.txt");
        file1.open();
    }

    @Test
    public void getFile() {
        File file1 = File.getFile("test.txt");
        File file2 = File.getFile("test.tbnb");
        File file3 = File.getFile("test");
    }
}