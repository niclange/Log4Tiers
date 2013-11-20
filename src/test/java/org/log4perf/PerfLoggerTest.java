package org.log4perf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.log4perf.test.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: nicolas
 * Date: 17/09/13
 * Time: 18:35
 * To change this template use File | Settings | File Templates.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springContext.xml" })
public class PerfLoggerTest {

    @Autowired
    Service service;


    @Test
    public void testLogAround() throws Exception {
        int res=2;
        for(int i=0;i<10;i++) {
             res = service.calculateService(res);
        }

        File file = new File("target/test/log4perf.log");
        assertTrue(file.exists());
    }
}
