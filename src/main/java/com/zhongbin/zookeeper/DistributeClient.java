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
		
		//��ȡZooKeeper����
		client.getConnect();
		
		//ע�����
		client.getChildren();
		
		//ҵ���߼�����
		client.business();
	}

	private void business() throws InterruptedException {

		Thread.sleep(Long.MAX_VALUE);
	}

	private void getChildren() throws KeeperException, InterruptedException {
		List<String> children = zkClient.getChildren("/servers", true);
		
		//�洢�������ڵ���������
		ArrayList<String> hosts = new ArrayList<>();
		
		for (String child : children) {
			byte[] data = zkClient.getData("/servers/" + child, false, null);
			
			hosts.add(new String(data));
		}
		
		//�����������������ƴ�ӡ������̨
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
					//Ϊ�˶��ִ��
					getChildren();
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
}
