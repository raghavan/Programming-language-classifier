package filehandler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class FileInputReader {

	public static String getContent(File file) {
		
		FileInputStream fstream = null;
		StringBuffer fileContent = new StringBuffer();
		try {
			fstream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		try {
			while ((strLine = br.readLine()) != null) {
				fileContent.append(strLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
		return fileContent.toString();
	}

	public static List<File> getAllFilesInFolder(String trainingFolder) {
		File folder = new File(trainingFolder);
		File[] listOfFiles = folder.listFiles();
		return Arrays.asList(listOfFiles);
	}
}
