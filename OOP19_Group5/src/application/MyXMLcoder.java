package application;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;


public class MyXMLcoder {

	//--Constructor--
	public MyXMLcoder() {
		
	}
	
	//--Methods--
	public void encode(List<Skier> arrList) {
		XMLEncoder encoder=null;
		try{
			encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("ContactBook.xml")));
		}catch(FileNotFoundException fileNotFound){
			System.out.println("ERROR: While Creating or Opening the File ContactBook.xml "+ fileNotFound);
		}
		encoder.writeObject(arrList);
		encoder.close();
	}//encode
	
	@SuppressWarnings({ "resource", "unchecked", "rawtypes" })
	public void decode(ObservableList<Skier> obList, TableView<Skier> table, File startFile){
		
		try {
			XMLDecoder decoder = null;
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(startFile)));
			ArrayList userSeries = (ArrayList) decoder.readObject();
			obList.addAll(userSeries);
			table.setItems(obList);
			
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File ContactBook.xml not found " + e);
		}	
	}//decode
}//class
