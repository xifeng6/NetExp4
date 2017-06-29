package com.xifeng.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Hashtable;

import com.xifeng.ui.Info;

public class ShareThread implements Runnable{

	private DatagramSocket sock;
	private DatagramPacket dp;
	private Hashtable<String, String> localinfo;
	private final byte[] exitData = "exit data mark".getBytes();
	private String filename;
	private InetAddress ip;
	private int clientPort;
	
	public ShareThread(DatagramSocket sock,DatagramPacket dp,Hashtable<String, String> localinfo) {
		this.sock = sock;
		this.dp = dp;
		this.localinfo = localinfo;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * 用于向客户端发送信息
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException{
		DatagramPacket dpk = new DatagramPacket(message.getBytes(), message.length(),new InetSocketAddress(ip, clientPort));
		sock.send(dpk);
	}
	
    public  boolean isEqualsByteArray(byte[] compareBuf,byte[] buf,int len){  
        if (buf == null || buf.length == 0 || buf.length < len || compareBuf.length < len)  
            return false;  
          
        boolean flag = true;  
          
        int innerMinLen = Math.min(compareBuf.length, len);  
        //if(buf.length == compareBuf.length){  
            for (int i = 0; i < innerMinLen; i++) {    
                if(buf[i] != compareBuf[i]){  
                    flag = false;  
                    break;  
                }  
            }  
        //}else   
        //  return false;  
        return flag;  
    }
    
  //将str转化为info
  	public  Info strToInfo(String filestr){
  		String[] str = filestr.split(",");
  		Info fileinfo = new Info(str[0], Integer.parseInt(str[1]), str[2], str[3], str[4], Long.parseLong(str[5]));
  		return fileinfo;
  	}
}
