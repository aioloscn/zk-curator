package com.aiolos.curator;

import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

/**
 * @author Aiolos
 * @date 2019-10-23 13:19
 */
public class MyCuratorWatcher implements CuratorWatcher {

    @Override
    public void process(WatchedEvent watchedEvent) throws Exception {
        System.out.println("触发watcher，节点路径为：" + watchedEvent.getPath());
    }
}
