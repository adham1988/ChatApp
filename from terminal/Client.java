package miniProject;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//package Multy;
/* 
* AUTAUMN SEMESTER 2020 
* @author 20gr552 COMTEK 5, AAU
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;


public class Client extends Frame implements ActionListener{
	PrintWriter os=null;  //Printwriter is a class which simply converts the bytes into normal characters
	Date date= new Date();
	String username = null;
	Frame f = new Frame("SNAP CAT");	
	Label l0;
	
	//Declare an TextArea instance area
		TextArea area,area2;
	//Declare an TextField instances t1,t2,t3
		TextField t1;
	//Declare an Button instances b1,b2
		Button b1;
		Client() {
			// Construct Label l0
			l0 = new Label("WELCOME");
			// setBounds(int x-coordinate, int y-coordinate, int width, int height)
			l0.setBounds(30, 30, 300, 30);
	        area = new TextArea();
	        area.setEditable(false);
			area.setBounds(30, 80, 300, 150);
			
			area2 = new TextArea();
			area2.setBounds(30, 300, 300, 60);
	        
			b1 = new Button("Send");
			b1.setBounds(500, 100, 50, 50);
			

			// event handling by anonymous class
			/**
			 * In Java, a class can contain another class known as nested class. It's
			 * possible to create a nested class without giving any name. A nested class
			 * that doesn't have any name is known as an anonymous class. An anonymous class
			 * must be defined inside another class. Hence, it is also known as an anonymous
			 * inner class..
			 */

			b1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// get input from text area and stored in String text
					String text = area2.getText();
					//area.setText(text);
					
					
				  long time = date.getTime();
				  Timestamp ts = new Timestamp(time);
				   os.println(ts+ " :"+username+" : "+text); 
		          //os.println( text); // send timestamp + user input to socket (to server)
		          os.flush();// forces any buffered output bytes to be written out
		          area2.setText("");
					

				}
			});
			
			
			//this handler is to activate the close button of the panel
			f.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			
			// Frame container adds components
			f.add(l0);
			f.add(b1);
			f.add(area);
			f.add(area2);
			
			
			// determine the size of frame
			f.setSize(600, 600);
			f.setLayout(null);
			f.setVisible(true);
		}
	
	
public static void main(String args[]) throws IOException{
	 if (args.length != 1) {
	       System.err.println(
	           "Usage: java Client <Username> ");
	       System.exit(1);
	   }
	 
Client client = new Client();
client.connect(args[0]);



}
	public void connect (String Name) throws IOException {
	
  String username = Name;  //store given parameter in string username 
  InetAddress address=InetAddress.getLocalHost(); //get the localhost IP address to connect to the server running on the same machine
  Socket s1=null; // initialize socket object s1
  String line=null;
  BufferedReader br=null;
  BufferedReader is=null;
  
 
  
 
		
		
		
  
  try {
      s1=new Socket(address, 1200); // You can use static final constant PORT_NUM
      br= new BufferedReader(new InputStreamReader(System.in)); //buffer to take input from terminal 
      is=new BufferedReader(new InputStreamReader(s1.getInputStream()));//getInputStream() returns an input stream for reading bytes from this socket
      os= new PrintWriter(s1.getOutputStream());  //create print to write to stream  
      //os.println("hej hej");
      
  }
  catch (IOException e){
      e.printStackTrace();
      System.err.print("IO Exception");
  }

  System.out.println("Client Address : "+address);
  System.out.println("Writte to chat ( Enter QUIT to end):");

  String response=null;  // string to read message from the input 
  try{
  	Thread reader = new Reader(is, this.area); //thread to get the inputstream (data coming from server)
		//System.out.println("hej");  // so whenever the input stream contains data, the data will be print it 
		reader.start();  //start the thread
      line=br.readLine(); //to store data input in buffer 
      
      while(line.compareTo("QUIT")!=0){
      	    
      	long time = date.getTime();
			Timestamp ts = new Timestamp(time);
          os.println(ts+ " :"+username+" : "+line); // send timestamp + user input to socket (to server)
          os.flush();// forces any buffered output bytes to be written out
          // response=is.readLine();  
          // System.out.println("Server Response : "+response);
           line=br.readLine();// buffer line input in stream

          }



  }
  catch(IOException e){
      e.printStackTrace();
  System.out.println("Socket read Error");
  }
  finally{

      is.close();os.close();br.close();s1.close();
              System.out.println("Connection Closed");

  }

}




class Reader extends Thread {
  
	private BufferedReader in;
	private TextArea t;
  public Reader(BufferedReader in,TextArea t) {
		this.in = in;
		this.t = t;
		
  
  }

  public void run() {
		String userInput;
		try{
          while ((userInput = in.readLine()) != null) {  //while the buffer contains data --> print the content
			//System.out.println(userInput);
        	  t.append(userInput+"\n"); }
			 } catch (IOException e) {
			 System.exit(1);}
	}
	}





public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}}





				
				
