package com.aiolos.curator;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author Aiolos
 * @date 2019-10-23 13:18
 */
public class MyWatcher implements Watcher {

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("触发watcher，节点路径为：" + watchedEvent.getPath());
    }
}
