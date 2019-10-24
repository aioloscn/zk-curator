package com.aiolos.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aiolos
 * @date 2019-10-24 9:55
 */
public class CuratorAcl {

    public CuratorFramework client = null;
    public static final String zkServerPath = "47.97.125.200:2181";

    public CuratorAcl() {

        RetryPolicy retryPolicy = new RetryNTimes(3, 5000);

        // build的时候同时认证，否则递归创建子节点时，匿名创建了第一个节点并赋予权限，此时这个节点是没有权限去创建它的子节点的
        client = CuratorFrameworkFactory.builder().authorization("digest", "aiolos1:123456".getBytes())
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

        CuratorAcl cta = new CuratorAcl();
        boolean isZkCuratorStarted = cta.client.isStarted();
        System.out.println("当前客户端的状态：" + (isZkCuratorStarted ? "连接中" : "已关闭"));

        String nodePath = "/super/aaa/bbb";
        List<ACL> acls = new ArrayList<>();
        Id aiolos1 = new Id("digest", AclUtils.getDigestUserPwd("aiolos1:123456"));
        Id aiolos2 = new Id("digest", AclUtils.getDigestUserPwd("aiolos2:123456"));
        acls.add(new ACL(ZooDefs.Perms.ALL, aiolos1));
        acls.add(new ACL(ZooDefs.Perms.READ, aiolos2));
        acls.add(new ACL(ZooDefs.Perms.DELETE | ZooDefs.Perms.CREATE, aiolos2));

        // 创建节点
        byte[] data = "aaaaaa".getBytes();
        cta.client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .withACL(acls, true)    // true: 通过递归的方式创建
                .forPath(nodePath, data);

        acls.add(new ACL(ZooDefs.Perms.WRITE, aiolos2));
        // 用于修改ACL，前提是节点已存在，不会修改父节点的ACL
        cta.client.setACL().withACL(acls).forPath(nodePath);

        cta.closeZKClient();
    }
}
