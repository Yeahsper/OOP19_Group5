package server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
/**
 * Simple server for the program, just listens on socket for connection, and when a connection happens create a new ClientHandler.
 * @author Jesper
 *
 */
public class Server implements Runnable {

	//--Variables etc--
	private static ServerSocket serverSocket;
	static Vector<ClientHandler> vectorHandler = new Vector<>();
	private int nrOfClients = 0;

		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket(8081);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("---Server started---");
			
			while (true) {
				Socket socket = null;
				try {
					socket = serverSocket.accept();
					DataInputStream input = new DataInputStream(socket.getInputStream()); 
					DataOutputStream output = new DataOutputStream(socket.getOutputStream()); 

					ClientHandler clientHandler = new ClientHandler(socket,"Client"+nrOfClients, input, output);
					Thread thread = new Thread(clientHandler);


					vectorHandler.add(clientHandler);
					System.out.println("Added client \""+clientHandler.getName()+"\" to the vectorlist");

					thread.start();
					nrOfClients++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}
