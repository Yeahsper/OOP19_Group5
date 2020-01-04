package application;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for serialization with XML.
 * @author Jesper
 *
 */
public class Serialization {
	
    public void serialize(ArrayList<Skier> arrlist, String fileName) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("./skiers.xml"));
            XMLEncoder xmlEncoder = new XMLEncoder(fileOutputStream);
            xmlEncoder.writeObject(arrlist);
            xmlEncoder.close();
            fileOutputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//serialize

    public ArrayList<Skier> deserialize(ArrayList<Skier> arrlist, String fileName) {
    	try {
            FileInputStream fileInputStream = new FileInputStream(new File("./skiers.xml"));
            XMLDecoder xmlDecoder = new XMLDecoder(fileInputStream);
            arrlist = (ArrayList<Skier>) xmlDecoder.readObject();
            xmlDecoder.close();
            fileInputStream.close();
        } catch(
                IOException ex)

        {
            ex.printStackTrace();
        }
        return arrlist;
    }//deserialize

}//class
