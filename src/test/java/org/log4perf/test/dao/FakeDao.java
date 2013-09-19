package org.log4perf.test.dao;

/**
 * Created with IntelliJ IDEA.
 * User: nicolas
 * Date: 18/09/13
 * Time: 20:11
 * To change this template use File | Settings | File Templates.
 */
public class FakeDao implements Dao {
    @Override
    public int SelectById(int id){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return id;
    }
}
