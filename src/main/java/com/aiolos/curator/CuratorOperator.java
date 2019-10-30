package com.aiolos.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

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
        /*cto.client.delete()
                .guaranteed()   // 如果删除失败，那么在后端还是继续会删除，知道成功
                .deletingChildrenIfNeeded() // 如果有子节点，就删除
                .withVersion(1)
                .forPath(nodePath);*/

        // 4. 读取节点数据
        Stat stat = new Stat();
        byte[] data = cto.client.getData()
                .storingStatIn(stat)    // 获取节点数据的同时把它的状态信息拉取出来
                .forPath(nodePath);
        System.out.printf("节点%s的数据为: %s\n", nodePath, new String(data));
        System.out.printf("节点的版本号为: %s\n", stat.getVersion());

        // 5. 查询子节点
        List<String> childNodes = cto.client.getChildren().forPath(nodePath);
        childNodes.forEach(s -> System.out.println(nodePath + "的子节点: " + s));

        // 6. 判断节点是否存在，如果不存在则为空
        Stat statExist = cto.client.checkExists().forPath(nodePath);
        System.out.println(statExist);

        // 监听修改只监听nodePath这个节点的变更，子节点不触发
        // 7. 监听watcher事件，当使用usingWatcher的时候，监听只会触发一次，监听完毕后就销毁
//        cto.client.getData().usingWatcher(new MyCuratorWatcher()).forPath(nodePath);
//        cto.client.getData().usingWatcher(new MyWatcher()).forPath(nodePath);
        // NodeCache：监听数据节点的变更，会触发事件
        final NodeCache nodeCache = new NodeCache(cto.client, nodePath);
        // 可选参数buildInitial: 初始化的时候获取node的值并且缓存到本地
        nodeCache.start(true);
        if (nodeCache.getCurrentData() != null)
            System.out.println("节点初始化数据为：" + new String(nodeCache.getCurrentData().getData()));
        else
            System.out.println("节点初始化数据为空");

        // 监听节点数据发生变化
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                // 如果是delete操作会抛NullPointerException
//                String data = new String(nodeCache.getCurrentData().getData());
                // 修改后
                ChildData childData = nodeCache.getCurrentData();
                if (childData != null) {
                    System.out.printf("发生变化，节点路径：%s 数据：%s\n", childData.getPath(), new String(childData.getData()));
                } else
                    System.out.println(nodeCache.getPath() + "节点不存在");
            }
        });

        // 8. 监听子节点的变化
        // cacheData: 是否设置缓存节点的数据状态
        final PathChildrenCache childrenCache = new PathChildrenCache(cto.client, nodePath, true);

        /**
         * POST_INITIALIZED_EVENT: 异步初始化，初始化之后会触发事件，启动时childrenCache.getCurrentData()没有数据，会监听自己子节点变化
         * NORMAL: 异步初始化，启动时childrenCache.getCurrentData()没有数据，也不监听子节点变化
         * BUILD_INITIAL_CACHE:同步初始化，启动时childrenCache.getCurrentData()有数据，但不能监听数据变化
         */
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

        List<ChildData> childDataList = childrenCache.getCurrentData();
        childDataList.forEach(d -> System.out.printf("%s的子节点%s的数据：%s\n", nodePath, d.getPath(), new String(d.getData())));

        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                if (event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED))
                    System.out.println("子节点初始化ok");
                else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED))
                    System.out.printf("%s添加子节点%s，数据为：%s\n", nodePath, event.getData().getPath(), new String(event.getData().getData()));
                else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED))
                    System.out.println("删除子节点：" + event.getData().getPath());
                else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                    System.out.printf("%s的子节点%s的数据修改为：%s\n", nodePath, event.getData().getPath(), new String(event.getData().getData()));
                }
            }
        });

        Thread.sleep(300000);

        cto.closeZKClient();
        boolean isZkCuratorStarteds = cto.client.isStarted();
        System.out.printf("当前客户的状态：%s\n", isZkCuratorStarteds ? "连接中" : "已关闭");
    }
}
