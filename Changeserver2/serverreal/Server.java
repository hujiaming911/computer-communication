package serverreal;

import java.io.*;
import java.net.*;
import java.util.*;



public class Server {
	class Serverthread2 extends Thread{//�������ڶ����߳�	
		Socket clientsocket = null;
		InputStream sin = null;
		DataInputStream dsin = null;
		OutputStream sout = null;
		DataOutputStream dsout = null;
		public Serverthread2( Socket socket ){	
			clientsocket = socket;
			
			try {
				sin = clientsocket.getInputStream();			
				sout = clientsocket.getOutputStream();
				dsin = new DataInputStream( sin );
				dsout = new DataOutputStream( sout );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public void run(){
			try {
				String msg;
				String client;
				while( true ){
					msg = dsin.readUTF();
					client ="ip��Զ�˶˿ںţ�"+clientsocket.getRemoteSocketAddress().toString()+" ";
					send(client+msg,dsout);
				}
			}catch (Exception e) {
              e.printStackTrace();
			}
		}
	}		
//��ͨ����������һ���̼߳�������clientsocket�Ž������в��������������������ڶ����߳�
	Socket clientsocket = null;
	InputStream sin = null;
	DataInputStream dsin = null;
	OutputStream sout = null;
	DataOutputStream dsout = null;
	LinkedList<Serverthread2> ll=new LinkedList<Serverthread2>();
    public void insert( Socket clientsocket ){
		Serverthread2 newClient = new Serverthread2(clientsocket);
		newClient.clientsocket = clientsocket;
		ll.addFirst(newClient);
		System.out.println("����ɹ�");
		ll.get(0).start();
	}
	public void send( String msg,DataOutputStream dsout ){
		for(int i=0;i<ll.size();i++){
		Serverthread2 tc = ll.get(i) ; 
		try{tc.dsout.writeUTF(msg);
		    tc.dsout.flush();
		  }catch(Exception e)
		{e.printStackTrace();}
		}
	}
}