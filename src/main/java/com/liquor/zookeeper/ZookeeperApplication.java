package com.liquor.zookeeper;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author Liquor.Huang
 * @Date 2020/11/23 11:34
 * To change this template use File | Settings | File Templates.
 */
public class ZookeeperApplication {
    public static void main(String[] args) throws Exception {
        BaseZookeeper zookeeper = new BaseZookeeper();
        zookeeper.connectZookeeper("192.168.254.129:2181");
        System.out.println("hello zookeeper");
        zookeeper.closeConnection();
    }
}
