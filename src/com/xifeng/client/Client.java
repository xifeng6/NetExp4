package com.xifeng.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;

import com.xifeng.ui.ClientUi;
import com.xifeng.ui.Info;
import com.xifeng.ui.MD5Util;

public class Client {
	private static Vector<Info> sharefile = new Vector<>();
	private static Hashtable<String, String> localinfo = new Hashtable<>();
	public static void main(String[] args) throws Exception {
		ClientUi ui = new ClientUi();
		ui.setVisible(true);
		
		JTable table = ui.getTable();

		//定义客户端中的udp传输端口
		DatagramSocket dsock = new DatagramSocket();
		
		//获取的按钮
		JButton receive = ui.getReceive();
		receive.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int select = -1;
				select = table.getSelectedRow();
				if(select != -1){
					System.out.println(sharefile.get(select).toString());
					//连接
					//发送文件名
				}
			}
		});
		
		//分享的按钮
		JButton share = ui.getShare();
		share.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					Socket sock= new Socket("127.0.0.1",12345);
					OutputStream sockOut = sock.getOutputStream();
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					chooser.showDialog(new JLabel(), "选择");
					File file = chooser.getSelectedFile();
					String filename = file.getName();
					String md5= MD5Util.getFileMD5String(file);
					String nickName = ui.getNickname().getText();
					if(nickName.equals(null) == false){
						ui.getNickname().setEnabled(false);
					}
					long length = file.length();
					localinfo.put(filename, file.getAbsolutePath());
					Info info = new Info(InetAddress.getLocalHost().getHostAddress(),dsock.getLocalPort(),filename,md5,nickName,length);
					
					sockOut.write("share".getBytes());
					if(servInfoBack(sock).equals("OK")){
						sockOut.write(info.toString().getBytes());
					}
					sockOut.close();
					sock.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		//刷新的按钮
		JButton refresh = ui.getRefresh();
		refresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Socket sock= new Socket("127.0.0.1",12345);
					OutputStream sockOut = sock.getOutputStream();	//定义socket输出流
					sockOut.write("get".getBytes());
					String info1 = servInfoBack(sock);
					String[] str = info1.split("]");
					System.out.println(info1);
					for(String s : str){
						Info info = strToInfo(s);
						if (sharefile.contains(info) == false) {
							sharefile.addElement(info);
							ui.addData(info);
						}
					}
					sockOut.close();
					sock.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}   
			}
		});
		 
	}
	
	public static String servInfoBack(Socket sock) throws Exception  //读取服务端的反馈信息  
	  {  
	    InputStream sockIn = sock.getInputStream(); //定义socket输入流  
	    byte[] bufIn =new byte[1024];  
	    int lenIn=sockIn.read(bufIn);            //将服务端返回的信息写入bufIn字节缓冲区  
	    String info=new String(bufIn,0,lenIn);  
	    return info;  
	  }
	//将str转化为info
	public static Info strToInfo(String filestr){
		String[] str = filestr.split(",");
		Info fileinfo = new Info(str[0], Integer.parseInt(str[1]), str[2], str[3], str[4], Long.parseLong(str[5]));
		return fileinfo;
	}
	
	class FileThread implements Runnable{
		private DatagramSocket sock;
		private byte[] buf = new byte[1024];
		public FileThread(DatagramSocket sock) {
			this.sock = sock;
		}

		@Override
		public void run() {
			while(true){
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					sock.receive(dp);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
