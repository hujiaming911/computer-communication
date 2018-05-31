package serverframe;

import java.io.*;
import java.net.*;

import java.awt.*;
import javax.swing.*;


import serverreal.Server;

public class ServerFrame extends JFrame {	
	private JButton kaiguan = new JButton("�򿪷�����");

	private JLabel jilubiaoqian = new JLabel("������״̬");

	private JTextArea jilukuang = new JTextArea();			//��¼
	
	private JScrollPane jilutiao =  new JScrollPane(jilukuang);			//��¼�Ĺ�����
	
	private Thread thread = null; 			//��������һ���߳�
	private Serverthread1 serverthread1 = null;		//������ʵ�ֵ�һ���̵߳ķ���
	
	private ServerFrame serverframe = this; 	


	public void appendLog(String msg){			//ͬ�ͻ���һ�������Ͻ�����Ϣ�������
		jilukuang.append(msg);	
		jilukuang.setCaretPosition(jilukuang.getText().length());
	}
//���°�ť�򿪷������������������һ���̲߳��ϼ�����û��socketҪ���������رշ�������ر�
	private JButton getJBOpenServer() {
			kaiguan.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if(kaiguan.getText()=="�򿪷�����"){
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
	//������
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
			this.setTitle("������");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			this.setVisible(true);	
	}
//��������һ���߳�	
	class Serverthread1 extends ServerSocket implements Runnable{
		public Serverthread1(int duankou, int shuliang) throws IOException {
			super(duankou, shuliang);
		}
		public void run() {
			kaiguan.setText("�رշ�����"); 
			appendLog("\r\n\r\n"+"������������");		
			Socket s;
			Server clientlist = new Server();
			try {
				while (true){
					s=accept();
					clientlist.insert(s);
				}
			} catch (Exception e) {//�رշ�����
				kaiguan.setText("�򿪷�����");
				appendLog("\r\n\r\n" + "�������ѹر�");	
			}
		}
	}
}
