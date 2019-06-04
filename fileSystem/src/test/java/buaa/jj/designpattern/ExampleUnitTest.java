package buaa.jj.designpattern;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String path = "/s/asa/txt.sas";
        LinkedList linkedList = new LinkedList();
        linkedList.addAll(Arrays.asList(path.split("/")));
        linkedList.poll();
        System.out.println(linkedList);
    }
}