package miniProject;


/* 
 * AUTAUMN SEMESTER 2020 
 * @author 20gr552 COMTEK 5, AAU
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	ArrayList<ServerThread> clients = new ArrayList<>(); // array list to store all the sockets

	public static void main(String args[]) {
		System.out.println("hej med dig ");
		Server server = new Server(); // create new object Server
		server.connect();// run the function connect
	}

	public void handlemessage(String msg, ServerThread st) {// this function loops over all sockets and send every
															// received message to all sockets
		for (ServerThread sst : this.clients) { // loop over all clients
			System.out.println("Sending message");
			sst.sendmessage(msg); // send to all other
		}
	}

	public void connect() {

		Socket s = null; // initialize socket object s
		ServerSocket ss2 = null; // initialize serverSocket object ss2

		System.out.println("Server helllo   Listening......");
		try {
			ss2 = new ServerSocket(1200); // // starts server and waits for a connection

		} catch (IOException e) { // Exception to ensure that the flow of the program doesn't break when an
									// exception occurs
			e.printStackTrace();
			System.out.println("Server error");

		}

		while (true) {
			try {

				s = ss2.accept(); // creating socket and waiting for client connection
				System.out.println("connection Established");
				ServerThread st = new ServerThread(s, this); // handel every socket connection
				// connection[i] = new SocketDatabase (s, null);
				// System.out.println(connection[i].sct);

				// clients.add(s);
				// System.out.println(clients);

				// System.out.println(s.getInetAddress());
				st.start(); // start the thread
				clients.add(st); // add the thread to socket list

			}

			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Connection Error");

			}
		}

	}

}

// class ServerThread to make socket connection whenever a client connect to server socket
class ServerThread extends Thread {
	Server srv;
	String line = null; //// using string line to reade message from input stream coming from client
	BufferedReader is = null; // returns an input stream for reading bytes from this socket
	PrintWriter os = null; // returns an output stream for writing bytes to this socket (send data to
							// client)
	Socket s = null;

	public ServerThread(Socket s, Server srv) { // constructor takes socket and server as parameters and store them
		this.s = s;
		this.srv = srv;

	}

	// function to forward messages. It takes parameter msg and then write it to
	// socket using os.prinln
	public void sendmessage(String msg) {
		// this.os.print(msg);

		os.println(msg);
		os.flush(); // forces any buffered output bytes to be written out
		System.out.println("Response to Client :  " + msg);

	}

	public void run() {

		try {

			// getInputStream() returns an input stream for reading bytes from this socket
			// read from socket to ObjectInputStream object
			is = new BufferedReader(new InputStreamReader(s.getInputStream()));
			// create ObjectOutputStream object to write to socket
			// Printwriter is a class which simply converts the bytes into normal characters
			os = new PrintWriter(s.getOutputStream());

		} catch (IOException e) {
			System.out.println("IO error in server thread");
		}

		try {
			line = is.readLine(); // using string line to read message from input stream coming from client
			while (line.compareTo("QUIT") != 0) {// while line input is different from "QUIT"

				// os.println(line);
				this.srv.handlemessage(line, this);// pass the line input and the socket itself and pass them in
													// function handelmessage(copy af itself)
				os.flush();
				// System.out.println("Response to Client : "+line);

				line = is.readLine();
			}
		} catch (IOException e) {

			line = this.getName(); // reused String line for getting thread name
			System.out.println("IO Error/ Client " + line + " terminated abruptly");
		} catch (NullPointerException e) {
			line = this.getName(); // reused String line for getting thread name
			System.out.println("Client " + line + " Closed");
		}

		finally {
			try {
				System.out.println("Connection Closing..");
				if (is != null) {
					is.close();
					System.out.println(" Socket Input Stream Closed");
				}

				if (os != null) {
					os.close();
					System.out.println("Socket Out Closed");
				}
				if (s != null) {
					s.close();
					System.out.println("Socket Closed");
				}

			} catch (IOException ie) {
				System.out.println("Socket Close Error");
			}
		} // end finally
	}
}
