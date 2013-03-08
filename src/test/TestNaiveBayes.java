package test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import dbhandler.DBConnect;
import dbhandler.DBReader;

import util.Constants;
import util.Utility;
import filehandler.FileInputReader;

public class TestNaiveBayes {

	@Test
	public void testGetAllFiles(){
		List<File> files = FileInputReader.getAllFilesInFolder(Constants.TRAINING_FOLDER);
		assertTrue(files.size()==3);
	}

	@Test
	public void testGetAllFileContent(){
		List<File> files = FileInputReader.getAllFilesInFolder(Constants.TRAINING_FOLDER);
		for(File file : files){
			System.out.println(FileInputReader.getContent(file));
		}
	}
	
	@Test
	public void testGetLabelFromFileName(){
		List<File> files = FileInputReader.getAllFilesInFolder(Constants.TRAINING_FOLDER);
		for(File file : files){
			System.out.println(Utility.getLabelFromFileName(file.getName()));
		}
	}
	
	@Test
	public void testGetDistinctLabels(){
		System.out.println(DBReader.getDistinctLabels());
	}
}
