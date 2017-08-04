package cn.ml_tech.mx.mlproj;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestDemo {
    @Test
    public void useAppContext() throws Exception {
        Class<?> ServiceManager = Class
                .forName("android.os.PowerManager");
        Method[] methods = ServiceManager.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals("shutdown")) {
                Class<?>[] params = methods[i].getParameterTypes();
                for (int c = 0; c < params.length; c++) {
                    Log.d("zw", params[c].getName());
                }
            }
        }
    }

    @Test
    public void Random() {
        Random random = new Random();
        for (int var = 0; var < 10; var++) {
            String string = String.valueOf(Math.abs(random.nextInt()));

            System.out.println(Math.abs(random.nextInt()));
        }
    }

    @Test
    public void TestUtils() {

        String string = "123";
        System.out.println(UtilsHelper.String2Int(string));
    }

    @Test
    public void TestHashMap() {
        Map<String, Double> map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            map.put(String.valueOf(i), (double) i);
        }
        if (map.containsKey(String.valueOf(0))) map.put(String.valueOf(0), (double) 1);
        assertTrue(map.get(String.valueOf(0)) == 1);
    }
}
