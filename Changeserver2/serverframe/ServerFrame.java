package serverframe;

import java.io.*;
import java.net.*;

import java.awt.*;
import javax.swing.*;


import serverreal.Server;

public class ServerFrame extends JFrame {	
	private JButton kaiguan = new JButton("打开服务器");

	private JLabel jilubiaoqian = new JLabel("服务器状态");

	private JTextArea jilukuang = new JTextArea();			//记录
	
	private JScrollPane jilutiao =  new JScrollPane(jilukuang);			//记录的滚动条
	
	private Thread thread = null; 			//服务器第一个线程
	private Serverthread1 serverthread1 = null;		//服务器实现第一个线程的方法
	
	private ServerFrame serverframe = this; 	


	public void appendLog(String msg){			//同客户端一样，不断将新信息导入框中
		jilukuang.append(msg);	
		jilukuang.setCaretPosition(jilukuang.getText().length());
	}
//按下按钮打开服务器，服务器进入第一个线程不断监听有没有socket要连过来，关闭服务器则关闭
	private JButton getJBOpenServer() {
			kaiguan.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if(kaiguan.getText()=="打开服务器"){
						try {
							serverthread1 = new Serverthread1(8848,50);
							thread = new Thread( serverthread1 );
							thread.start();
						} catch (Exception e1) {
							return;
						}
					}else{						
							try {
								serverthread1.close();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
					}
				}
			});
		return kaiguan;
	}
	//主函数
	public static void main(String[] args) {
				ServerFrame thisClass = new ServerFrame();			
	}
	public ServerFrame() {

			jilukuang.setEditable(false);
			jilukuang.setLineWrap(true);
			jilukuang.setWrapStyleWord(true); 
			this.add(jilubiaoqian,BorderLayout.NORTH);
			this.add(jilutiao);
			this.add(getJBOpenServer(),BorderLayout.SOUTH);		

			
			this.setSize(600, 400);
			this.setTitle("服务器");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			this.setVisible(true);	
	}
//服务器第一个线程	
	class Serverthread1 extends ServerSocket implements Runnable{
		public Serverthread1(int duankou, int shuliang) throws IOException {
			super(duankou, shuliang);
		}
		public void run() {
			kaiguan.setText("关闭服务器"); 
			appendLog("\r\n\r\n"+"服务器已启动");		
			Socket s;
			Server clientlist = new Server();
			try {
				while (true){
					s=accept();
					clientlist.insert(s);
				}
			} catch (Exception e) {//关闭服务器
				kaiguan.setText("打开服务器");
				appendLog("\r\n\r\n" + "服务器已关闭");	
			}
		}
	}
}
