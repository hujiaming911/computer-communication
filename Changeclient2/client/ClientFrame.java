package client;

import java.awt.*;
import javax.swing.*;

import java.io.*;
import java.net.*;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ClientFrame extends JFrame {

	private JPanel mypanel1 = new JPanel();	
	private JPanel mypanel2 = new JPanel();	
	private JPanel mypanel3 = new JPanel();	



	private JLabel ipbiaoqian = new JLabel("服务器IP");//连接IP
	private JTextField ipkuang = new JTextField(10);	
	private JButton lianjie = new JButton("连接服务器");

	private JLabel nichengbiaoqian = new JLabel("昵称");//昵称
	private JTextField nichengkuang = new JTextField(10);	
	private JButton gaiming = new JButton("改名");

	private JTextArea fasongneirong = new JTextArea(10,15);//发送内容
	private JButton fasong = new JButton("发送");
	private JScrollPane fasongtiao = new JScrollPane(fasongneirong);
	
	private JLabel jilubiaoqian =new JLabel("聊天记录");//聊天记录	
	private JTextArea liaotianjilukuang = new JTextArea(10,15);			
	private JScrollPane liaotianjilutiao = new JScrollPane(liaotianjilukuang);

	private Socket s = null;		//线
	private ThreadReceive trReceive = null;	//接收线程

	DataInputStream dain;
	DataOutputStream daou;	//发送缓冲	
	
	
	private void appendLog(String log){//把log加到jtalog的最底部（append)方法，setcareposition是使滚动条一直保持在最底部
		liaotianjilukuang.append(log);
		liaotianjilukuang.setCaretPosition(liaotianjilukuang.getText().length());
		
	}
//接收线程类	
	class ThreadReceive extends Thread{
		public void run(){
			String info;
			try {
				dain = new DataInputStream(s.getInputStream());
				while (true){
					info =dain.readUTF();
					appendLog("\r\n" +info);
				}
			} catch (Exception e) {
				// TODO 自动生成 catch 块	
				e.printStackTrace();
			}
			
		}
	}
//连接服务器并开启接收线程
	private JButton getlianjie() {
			lianjie.addMouseListener(new java.awt.event.MouseAdapter() {				
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//	System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					try {
						if( s!=null ){
								s.close();
						}
						s = new Socket(ipkuang.getText(),8848);
						if(s.isConnected())
						{
							appendLog("\r\n");
							appendLog("与服务器连接成功");
						}		
						trReceive = new ThreadReceive();
						trReceive.start();
						ipkuang.setText("");
					} catch (Exception e1) {
						appendLog("\r\n");
						appendLog("与服务器连接失败");
					}
				}
			});
		return lianjie;
	}
//发送
	private JButton getfasong() {
			fasong.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					try {
						if(fasongneirong.getText().equals("")){
							return;}
						SimpleDateFormat sdf  = new SimpleDateFormat("HH:mm:ss");
						daou = new DataOutputStream(s.getOutputStream());
						daou.writeUTF(nichengkuang.getText() + "   " + sdf.format(new Date()) + "\r\n" + fasongneirong.getText() );							
						daou.flush();
						fasongneirong.setText("");
					} catch (Exception e1) {
						// TODO 自动生成 catch 块
						appendLog("\r\n");
						appendLog("发送失败" );
					}
				}
			});
		return fasong;
	}
//修改昵称
	private JButton getgaiming() {
			gaiming.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
				//	System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					
					if ( gaiming.getText().equals("改名") ){
						gaiming.setText("确定");
						nichengkuang.setEditable(true);
					}else{
						gaiming.setText("改名");
						nichengkuang.setEditable(false);
					}
				}
			});
		return gaiming;
	}
//主函数
	public static void main(String[] args) {
				ClientFrame clientframe = new ClientFrame();
	}
	public ClientFrame() {
            mypanel2.setLayout(new GridLayout(2,1));
            mypanel1.add(jilubiaoqian);
            mypanel1.add(ipbiaoqian);
            mypanel1.add(ipkuang);
            mypanel1.add(getlianjie());
            mypanel2.add(liaotianjilutiao);
            mypanel2.add(fasongtiao);
            mypanel3.add(nichengbiaoqian);
            mypanel3.add(nichengkuang);
            mypanel3.add(getgaiming());
            mypanel3.add(getfasong());

           
            this.add(mypanel1,BorderLayout.NORTH);
            this.add(mypanel2,BorderLayout.CENTER);
            this.add(mypanel3,BorderLayout.SOUTH);
         

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize(500, 600);
			this.setLocation(500,250);
			this.setTitle("客户端");
			this.setVisible(true);
	}
}

