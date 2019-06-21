package buaa.jj.designpattern;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import buaa.jj.designpattern.factory.FileSystemFactory;
import buaa.jj.designpattern.filesystem.FileSystem;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        File file = new File("/home/jj");
        System.out.println(file.getAbsolutePath());
    }
}