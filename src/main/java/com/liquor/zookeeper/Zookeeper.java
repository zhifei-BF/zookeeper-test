package com.liquor.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author Liquor.Huang
 * @Date 2020/11/23 10:50
 * To change this template use File | Settings | File Templates.
 */
public class Zookeeper {

    private String path = "192.168.254.129:2181";

    public void test() throws IOException, KeeperException, InterruptedException {

        //初始化zookeeper客户端，负责建立与zkServer的会话。
        ZooKeeper zooKeeper = new ZooKeeper(path, 30000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("获取链接成功！");
            }
        });

        //创建一个节点，1-节点路径，2-节点内容，3-访问控制列表，4-节点类型
        String fullPath = zooKeeper.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //判断一个节点是否存在
        Stat stat = zooKeeper.exists(path, false);
        if (stat != null) {
            System.out.println("不为空");
        }

        //查询一个节点的内容
        Stat stat2 = new Stat();
        byte[] data = zooKeeper.getData(path, false, stat2);

        //更新一个节点
        zooKeeper.setData(path, new byte[]{}, stat2.getVersion() + 1);

        //删除一个节点
        zooKeeper.delete(path, stat.getVersion());

        //查询一个节点的子节点列表
        List<String> children = zooKeeper.getChildren(path, false);

        //关闭连接
        if (zooKeeper != null) {
            zooKeeper.close();
        }

    }
}
