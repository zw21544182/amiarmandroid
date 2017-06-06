package cn.ml_tech.mx.mlproj;

import android.content.Context;
import android.content.SyncStatusObserver;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.io.FileBackedOutputStream;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestDemo {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("cn.ml_tech.mx.mlproj", appContext.getPackageName());
    }
    @Test
    public void Random()
    {
        Random random=new Random();
        for (int var=0;var<10;var++)
        {
            String string= String.valueOf(Math.abs(random.nextInt()));

            System.out.println(Math.abs( random.nextInt()));
        }
    }

    @Test
    public void TestUtils()
    {
        
        String string="123";
       System.out.println( UtilsHelper.String2Int(string));
    }
}
