package com.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTests {

    @Before
    public void contextLoads() {

        System.out.println("cccc");
    }

    @Test
    public void contextLoads2() {

        System.out.println("bbbb");
    }

    @Test
    public void contextLoads3() throws IOException {
        File file = new File("d:/aaa.txt");
        FileOutputStream os = new FileOutputStream(file);

        String a = "钟点工看电视剧覅适得府君书打飞机快递费计算的发生代理费看得见覅时代峰峻disk浮点" +
                "计算钟点工看电视剧覅适得府君书打飞机快递费计算的发生代理费看得见覅时代峰峻disk浮点计算钟点工看电视" +
                "剧覅适得府君书打飞机快递费计算的发生代理费看得见覅时代峰峻disk浮点计算钟点工看电视剧覅适得府君书打飞机" +
                "快递费计算的发生代理费看得见覅时代峰峻disk浮点计算钟点工看电视剧覅适得府君书打飞机快递费计算的发生代理费" +
                "看得见覅时代峰峻disk浮点计算钟点工看电视剧覅适得府君书打飞机快递费计算的发生代理费看得见覅时代峰峻disk浮点计算\n";
        for (int i=0;i < 20000;i++){
            os.write(a.getBytes());
        }
        os.flush();
        os.close();

        System.out.println("bbbb");
    }


}
