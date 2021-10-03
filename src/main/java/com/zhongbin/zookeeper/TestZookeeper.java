package com.zhongbin.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class TestZookeeper {
	
	private String connectString = "hadoop102:2181,hadoop103:2180,hadoop104:2181";
	private int sessionTimeout = 8000;
	private ZooKeeper zkClient;

	//��ȡ�ͻ���
	@Before
	public void init() throws IOException {
		
		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				
				System.out.println("-------start-------");
				List<String> children;
				try {
					children = zkClient.getChildren("/", true);
					
					for (String child : children) {
						System.out.println(child);
					}
					System.out.println("======end=======");
				} catch (KeeperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}
	
	//�����ڵ�
	@Test
	public void creatNode() throws KeeperException, InterruptedException {
		String path = zkClient.create("/dabin", "laopos".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		System.out.println(path);
	}
	
	//��ȡ�ӽڵ㲢����
	@Test
	public void getNodeAndWatch() throws KeeperException, InterruptedException {
		List<String> children = zkClient.getChildren("/", true);
		
		for (String child : children) {
			System.out.println(child);
		}
		
		Thread.sleep(Long.MAX_VALUE);
	}
	
	//�ж�Node�Ƿ����
	@Test
	public void exist() throws KeeperException, InterruptedException {
		Stat stat = zkClient.exists("/dabin", false);
		
		System.out.println(stat);
	}

}
