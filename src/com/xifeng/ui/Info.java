package com.xifeng.ui;


public class Info {
	
	private String ip;
	private int port;
	private String fileName;
	private String md5;
	private String nickName;
	private long length;
	
	public Info(String ip,int port , String fileName , String md5 , String nickName , long length) {
		this.ip = ip;
		this.port = port;
		this.fileName = fileName;
		this.md5 = md5;
		this.nickName = nickName;
		this.length = length;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return ip + "," + port + "," + fileName + "," + md5 + "," + nickName + ","+ length ;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Info){
			Info p = (Info) obj;
			if(ip.equals(p.getIp())&&port == p.getPort()&&fileName.equals(p.getFileName())&&md5.equals(p.getMd5())){
				return true;
			}
		}
		return false;
	}
}
