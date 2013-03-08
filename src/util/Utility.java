package util;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Knowledge;

import dbhandler.DBReader;
import filehandler.FileInputReader;

public class Utility {

	public static Set<String> getStopWords() {
		String fileContent = FileInputReader.getContent(new File(Constants.STOP_WORD_FILE));
		Set<String> stopWords = new HashSet<String>();
		for(String word : fileContent.split(" ")){
			stopWords.add(word);
		}
		return stopWords;
	}

	public static void persistMapToDb(Map<String, Integer> map, String label) {
		for (String key : map.keySet()) {
			int count =  map.get(key);
			Knowledge existing = DBReader.getExistingKnowledge(key, label);
			if (existing == null) {
				String insertStatement = getInsertStatement(key,count, label);
				DBReader.insert(insertStatement);
			}else{
				count += existing.getCount();
				String updateStmt = getUpdateStmt(existing.getId(),count);
				DBReader.update(updateStmt);
			}
		}
	}

	private static String getInsertStatement(String key, Integer count, String label) {
		String insertStmt = "insert into knowledgemodel (word,count,label) values ('" + key.toLowerCase() + "'," + count + ",'" + label.toLowerCase()
				+ "')";
		return insertStmt;
	}
	
	private static String getUpdateStmt(Integer id, Integer count) {
		String updateStmt = "update knowledgemodel set count = "+count+" where id = "+id;
		return updateStmt;
	}

	public static String getLabelFromFileName(String name) {
		String label = name.split("\\.")[0];
		return label.toLowerCase();
	}
	
	public static int logBase2(int x)
	{
	    return (int) (Math.log(x) / Math.log(2));
	}
}
