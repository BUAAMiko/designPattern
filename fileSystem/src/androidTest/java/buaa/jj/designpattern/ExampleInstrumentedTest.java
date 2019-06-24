package buaa.jj.designpattern;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;

import buaa.jj.designpattern.factory.FileSystemFactory;
import buaa.jj.designpattern.filesystem.File;
import buaa.jj.designpattern.filesystem.FileSystem;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        FileSystemFactory.savePath = appContext.getExternalCacheDir().getAbsolutePath();
        FileSystemFactory factory = new FileSystemFactory();
        FileSystemFactory.userId = "2";
        FileSystem fileSystem = factory.getFileSystem(true);
        LinkedList linkedList = new LinkedList<String>();
        linkedList.add("1");
//        fileSystem.addFile(linkedList,File.getFile("text.png"));
        fileSystem = factory.getFileSystem(true).getFile(new LinkedList<String>(),"1");
        //        assertEquals("buaa.jj.filesystem.test", appContext.getPackageName());
    }
}
