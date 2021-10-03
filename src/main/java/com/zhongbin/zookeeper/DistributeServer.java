package com.zhongbin.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class DistributeServer {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		DistributeServer server = new DistributeServer();
		
		//连接ZooKeeper集群
		server.getConnect();
		//注册节点
		server.regist(args[0]);
		//业务逻辑处理
		server.business();
	}

	private void business() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}

	private void regist(String hostname) throws KeeperException, InterruptedException {

		zkClient.create("/servers/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		
		System.out.println(hostname + "is on line.");
	}

	private String connectString = "hadoop102:2181,hadoop103:2180,hadoop104:2181";
	private int sessionTimeout = 10000;
	private ZooKeeper zkClient;

	private void getConnect() throws IOException {

		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
