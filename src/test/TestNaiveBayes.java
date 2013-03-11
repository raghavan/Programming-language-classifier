package test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import model.KnowledgeMap;

import org.junit.Ignore;
import org.junit.Test;

import util.Constants;
import util.Utility;
import dbhandler.DBReader;
import filehandler.FileInputReader;

public class TestNaiveBayes {

	@Ignore
	public void testGetAllFiles(){
		List<File> files = FileInputReader.getAllFilesInFolder(Constants.TRAINING_FOLDER);
		assertTrue(files.size()==3);
	}

	@Ignore
	public void testGetAllFileContent(){
		List<File> files = FileInputReader.getAllFilesInFolder(Constants.TRAINING_FOLDER);
		for(File file : files){
			System.out.println(FileInputReader.getContent(file));
		}
	}
	
	@Ignore
	public void testGetLabelFromFileName(){
		List<File> files = FileInputReader.getAllFilesInFolder(Constants.TRAINING_FOLDER);
		for(File file : files){
			System.out.println(Utility.getLabelFromFileName(file.getName()));
		}
	}
	
	@Ignore
	public void testGetDistinctLabels(){
		System.out.println(DBReader.getDistinctLabels());
	}
	
	@Test
	public void testGetKnowledgeMapForLabel(){
		System.out.println(DBReader.getTotalCountForWord("import"));
	}
	
	@Ignore
	public void testDoubleValueCompare(){
		double value1 = 8.364508159596757E24;
		double value2 = 6.067365322739479E37;
		int myInt = (int) Math.floor(value1);
		System.out.println(myInt);
		System.out.println((float)value2);
		System.out.println((float)value1 >  (float)value2);
	}
	
	@Test
	public void testWordCountMapForLabel(){
		KnowledgeMap knowledgeMap = DBReader.getWordCountMapForLabel("python");
		BigDecimal val = new BigDecimal(1/knowledgeMap.getSize());
		System.out.println(val);
	}
	
}
