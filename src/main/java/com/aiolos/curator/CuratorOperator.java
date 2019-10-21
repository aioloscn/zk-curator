package com.aiolos.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.util.List;

/**
 * @author Aiolos
 * @date 2019-10-21 14:45
 */
public class CuratorOperator {

    public CuratorFramework client = null;
    public static final String zkServerPath = "47.97.125.200:2181";

    /**
     * 实例化zk客户端
     */
    public CuratorOperator() {

        /**
         * 同步创建zk实例，原生api是异步的
         * curator连接zookeeper的策略：ExponentialBackoffRetry
         * baseSleepTimeMs：初始sleep的时间
         * maxRetries：最大重试次数
         * maxSleepMs：每次重试时间=baseSleepTimeMs * 次数，呈指数增长，如果重试时间大于最大重试时间就取最大重试时间
         */
//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);

        /**
         * curator连接zookeeper的策略：RetryNTimes
         * n：重试的次数
         * sleepMsBetweenRetries：每次重试间隔的时间
         */
        RetryPolicy retryPolicy = new RetryNTimes(3, 5000);

        /**
         * 不推荐
         * curator连接zookeeper的策略：RetryOneTime
         * sleepMsBetweenRetry：每次重试时间间隔
         */
//        RetryPolicy retryPolicy2 = new RetryOneTime(3000);

        /**
         * 永远重试，不推荐使用
         */
//        RetryPolicy retryPolicy3 = new RetryForever(3000);

        /**
         * curator连接zookeeper的策略：RetryUntilElapsed
         * maxElapsedTimeMs：最大重试时间
         * sleepMsBetweenRetries：每次重试间隔
         * 重试时间超过maxElapsedTimeMs后，就不再重试
         */
//        RetryPolicy retryPolicy4 = new RetryUntilElapsed(15000, 3000);

        client = CuratorFrameworkFactory.builder()
                .connectString(zkServerPath)
                .sessionTimeoutMs(10000).retryPolicy(retryPolicy)
                .namespace("workspace").build();
        client.start();
    }

    public void closeZKClient() {
        if (client != null)
            this.client.close();
    }

    public static void main(String[] args) throws Exception {

        CuratorOperator cto = new CuratorOperator();
        boolean isZkCuratorStarted = cto.client.isStarted();
        System.out.printf("当前客户的状态：%s\n", isZkCuratorStarted ? "连接中" : "已关闭");

        // 1. 创建节点
        String nodePath = "/super/aiolos";
        /*byte[] data = "superme".getBytes();
        cto.client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)   // Access Control List
                .forPath(nodePath, data);*/

        // 2. 更新节点数据
        /*byte[] newData = "batman".getBytes();
        cto.client.setData()
                .withVersion(0) // 对应client中的dataVersion，修改后变为1
                .forPath(nodePath, newData);*/

        // 3. 删除节点
        cto.client.delete()
                .guaranteed()   // 如果删除失败，那么在后端还是继续会删除，知道成功
                .deletingChildrenIfNeeded() // 如果有子节点，就删除
                .withVersion(1)
                .forPath(nodePath);

        Thread.sleep(3000);

        cto.closeZKClient();
        boolean isZkCuratorStarteds = cto.client.isStarted();
        System.out.printf("当前客户的状态：%s\n", isZkCuratorStarteds ? "连接中" : "已关闭");
    }
}
