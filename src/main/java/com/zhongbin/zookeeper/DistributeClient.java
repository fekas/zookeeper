package com.zhongbin.zookeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class DistributeClient {

	public static void main(String[] args) throws Exception {
		
		DistributeClient client = new DistributeClient();
		
		//获取ZooKeeper连接
		client.getConnect();
		
		//注册监听
		client.getChildren();
		
		//业务逻辑处理
		client.business();
	}

	private void business() throws InterruptedException {

		Thread.sleep(Long.MAX_VALUE);
	}

	private void getChildren() throws KeeperException, InterruptedException {
		List<String> children = zkClient.getChildren("/servers", true);
		
		//存储服务器节点主机名称
		ArrayList<String> hosts = new ArrayList<>();
		
		for (String child : children) {
			byte[] data = zkClient.getData("/servers/" + child, false, null);
			
			hosts.add(new String(data));
		}
		
		//将所有在线主机名称打印到控制台
		System.out.println(hosts);
		
	}

	private String connectString = "hadoop102:2181,hadoop103:2180,hadoop104:2181";
	private int sessionTimeout = 10000;
	private ZooKeeper zkClient;

	private void getConnect() throws IOException {

		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				
				try {
					//为了多次执行
					getChildren();
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
}
