package org.log4perf.test.service;

import org.log4perf.test.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: nicolas
 * Date: 18/09/13
 * Time: 20:05
 * To change this template use File | Settings | File Templates.
 */
public class FakeService implements Service {
    @Autowired
    Dao dao;

    @Override
    public int calculateService(int value){
        int result = 0;
        try {
            for(int i =0;i<value;i++){
                result = dao.SelectById(value) + 2;
            }
            Thread.sleep(100);

        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;

    }
}
