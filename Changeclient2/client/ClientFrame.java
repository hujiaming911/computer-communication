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



	private JLabel ipbiaoqian = new JLabel("������IP");//����IP
	private JTextField ipkuang = new JTextField(10);	
	private JButton lianjie = new JButton("���ӷ�����");

	private JLabel nichengbiaoqian = new JLabel("�ǳ�");//�ǳ�
	private JTextField nichengkuang = new JTextField(10);	
	private JButton gaiming = new JButton("����");

	private JTextArea fasongneirong = new JTextArea(10,15);//��������
	private JButton fasong = new JButton("����");
	private JScrollPane fasongtiao = new JScrollPane(fasongneirong);
	
	private JLabel jilubiaoqian =new JLabel("�����¼");//�����¼	
	private JTextArea liaotianjilukuang = new JTextArea(10,15);			
	private JScrollPane liaotianjilutiao = new JScrollPane(liaotianjilukuang);

	private Socket s = null;		//��
	private ThreadReceive trReceive = null;	//�����߳�

	DataInputStream dain;
	DataOutputStream daou;	//���ͻ���	
	
	
	private void appendLog(String log){//��log�ӵ�jtalog����ײ���append)������setcareposition��ʹ������һֱ��������ײ�
		liaotianjilukuang.append(log);
		liaotianjilukuang.setCaretPosition(liaotianjilukuang.getText().length());
		
	}
//�����߳���	
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
				// TODO �Զ����� catch ��	
				e.printStackTrace();
			}
			
		}
	}
//���ӷ����������������߳�
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
							appendLog("����������ӳɹ�");
						}		
						trReceive = new ThreadReceive();
						trReceive.start();
						ipkuang.setText("");
					} catch (Exception e1) {
						appendLog("\r\n");
						appendLog("�����������ʧ��");
					}
				}
			});
		return lianjie;
	}
//����
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
						// TODO �Զ����� catch ��
						appendLog("\r\n");
						appendLog("����ʧ��" );
					}
				}
			});
		return fasong;
	}
//�޸��ǳ�
	private JButton getgaiming() {
			gaiming.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
				//	System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					
					if ( gaiming.getText().equals("����") ){
						gaiming.setText("ȷ��");
						nichengkuang.setEditable(true);
					}else{
						gaiming.setText("����");
						nichengkuang.setEditable(false);
					}
				}
			});
		return gaiming;
	}
//������
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
			this.setTitle("�ͻ���");
			this.setVisible(true);
	}
}

