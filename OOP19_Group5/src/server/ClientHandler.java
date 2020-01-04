package server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;

import application.MainGUI;

/**
 * ClientHandler on the server-side of the program, pretty much the most important part of the program since it handles everything except 
 * the initial connection which Server.java handles.
 * @author Jesper
 *
 */
public class ClientHandler implements Runnable {

	//--Variables--
	private String name;
	private final Socket socket;
	private final DataInputStream input; 
	private final DataOutputStream output; 


	public ClientHandler(Socket socket, String name, DataInputStream input, DataOutputStream output) {
		this.socket = socket;
		this.name = name;
		this.input = input;
		this.output = output;

	}

	/**
	 * Does different stuff depending on what message it recieves. This is easy to expand by just creating more if-statements.
	 */
	@Override
	public void run() {

		String recieved;
		while (true) {
			try {
				//Sets the recieved message to a string
				recieved = input.readUTF();

		
				if(recieved.equalsIgnoreCase("SelectedSkier")) {
					for(ClientHandler ch : Server.vectorHandler) {
						String chosenSkier;
						chosenSkier = MainGUI.getChosenSkier();
						ch.output.writeUTF(chosenSkier);
						System.out.println("SelectedSkier");
					}
				}
				
				if(recieved.equalsIgnoreCase("TimerStart")) {
					for(ClientHandler ch : Server.vectorHandler) {
						ch.output.writeUTF("TimerStart");
						System.out.println("TimerStart");
					}
				}

				if(recieved.equalsIgnoreCase("TimerStop")) {
					for(ClientHandler ch : Server.vectorHandler) {
						ch.output.writeUTF("TimerStop");
						System.out.println("TimerStop");
					}
				}
				if(recieved.equalsIgnoreCase("Finish")) {
					for(ClientHandler ch : Server.vectorHandler) {
						ch.output.writeUTF("Finish");
						System.out.println("Finishh");
					}
				}

				if(recieved.equalsIgnoreCase("Split")) {
					for(ClientHandler ch : Server.vectorHandler) {
						ch.output.writeUTF("Split");
						System.out.println("Split");
					}
				}
			} catch (IOException ex) {
				System.out.println("Unable to get streams from client");
				break;
			}
		} 

	}



	public String getName() {
		return name;
	}

}
