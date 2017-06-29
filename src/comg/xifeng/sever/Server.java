package comg.xifeng.sever;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Vector;

import com.xifeng.ui.Info;

public class Server {
	private static Vector<Info> shareinfo = new Vector<Info>();
	public static void main(String[] args) throws Exception {
		try {
			//定义一个socket用于接收客户端的请求
			ServerSocket serversock = new ServerSocket(12345);
			while(true){
				Socket sock = serversock.accept();
				//开启新的线程用于接收客户端分享的文件信息
				String order = getOrderFromClient(sock);
				if(order.equals("share")){
					//接收客户端分享的文件信息
					writeOutInfo(sock, "OK");
					Info info = getFileInfo(sock);
					if(shareinfo.contains(info) == false){
						shareinfo.addElement(info);
					}
				}else if(order.equals("get")){
					//发送文件信息到客户端
					System.out.println(shareinfo.size());
					String infos = "";
					for(Info info : shareinfo){
						infos += info.toString()+"]";
						System.out.println(info.toString());
					}
					writeOutInfo(sock, infos);
				}else{
					//发送错误信息给客户端
					writeOutInfo(sock, "ERROR");
					System.out.println("error");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getOrderFromClient(Socket sock){
		try {
			InputStream inputStream = sock.getInputStream();
			byte[] info = new byte[1024];
			int lenInfo =0;  
		    lenInfo = inputStream.read(info);  //获取 
		    String order = new String(info,0,lenInfo);
		    return order;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void writeOutInfo(Socket sock,String infoStr)throws Exception//将信息反馈给客户端  
	  {  
	    OutputStream sockOut = sock.getOutputStream();  
	    sockOut.write(infoStr.getBytes());  
	  } 
	
	public static Info getFileInfo(Socket sock){
		try {
			InputStream inputStream = sock.getInputStream();
			byte[] info = new byte[4096];
			int len = 0;
			len = inputStream.read(info);
			String filestr = new String(info,0,len);
			String[] str = filestr.split(",");
			Info fileinfo = new Info(str[0], Integer.parseInt(str[1]), str[2], str[3], str[4], Long.parseLong(str[5]));
			return fileinfo;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//将str转化为info
	public static Info strToInfo(String filestr){
			String[] str = filestr.split(",");
			Info fileinfo = new Info(str[0], Integer.parseInt(str[1]), str[2], str[3], str[4], Long.parseLong(str[5]));
			return fileinfo;
	}
}
