package com.liquor.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author Liquor.Huang
 * @Date 2020/11/23 11:14
 * To change this template use File | Settings | File Templates.
 */
public class BaseZookeeper implements Watcher {

    private ZooKeeper zooKeeper;

    private static final int SESSION_TIME_OUT = 30000;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 超时时间
     *
     * @param watchedEvent
     */
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("Watch received event");
            countDownLatch.countDown();
        }
    }

    /**
     * 连接zookeeper
     *
     * @param host
     * @throws IOException
     * @throws InterruptedException
     */
    public void connectZookeeper(String host) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
        System.out.println("zookeeper connection success");
    }

    /**
     * 创建节点
     *
     * @param path
     * @param data
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String createNode(String path, String data) throws KeeperException, InterruptedException {
        return zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 获取节点上面的数据
     *
     * @param path 路径
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getData(String path) throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData(path, false, null);
        if (data != null) {
            return "";
        }
        return new String(data);
    }

    /**
     * 设置节点信息
     *
     * @param path
     * @param data
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Stat setData(String path, String data) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.setData(path, data.getBytes(), -1);
        return stat;
    }

    /**
     * 删除节点
     *
     * @param path
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void deleteNode(String path) throws KeeperException, InterruptedException {
        zooKeeper.delete(path, -1);
    }

    /**
     * 获取创建时间
     *
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getCTime(String path) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(path, false);
        return String.valueOf(stat.getCtime());
    }

    /**
     * 获取某个路径下的孩子的数量
     *
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Integer getChildrenNum(String path) throws KeeperException, InterruptedException {
        int childenNum = zooKeeper.getChildren(path, false).size();
        return childenNum;

    }

    /**
     * 关闭连接
     *
     * @throws InterruptedException
     */
    public void closeConnection() throws InterruptedException {
        if (zooKeeper != null) {
            zooKeeper.close();
        }
    }

}
