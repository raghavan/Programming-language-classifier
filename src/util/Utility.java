package util;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		String insertStmt = "insert into knowledgemodel (word,count,label) values ('" + escape(key.toLowerCase()) + "'," + count + ",'" + label.toLowerCase()
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
	
	public static double logBase2(double x)
	{
	    return (double) (Math.log(x) / Math.log(2));
	}
	
	private static final HashMap<String,String> sqlTokens;
	private static Pattern sqlTokenPattern;

	static
	{           
	    //MySQL escape sequences: http://dev.mysql.com/doc/refman/5.1/en/string-syntax.html
	    String[][] search_regex_replacement = new String[][]
	    {
	                //search string     search regex        sql replacement regex
	            {   "\u0000"    ,       "\\x00"     ,       "\\\\0"     },
	            {   "'"         ,       "'"         ,       "\\\\'"     },
	            {   "\""        ,       "\""        ,       "\\\\\""    },
	            {   "\b"        ,       "\\x08"     ,       "\\\\b"     },
	            {   "\n"        ,       "\\n"       ,       "\\\\n"     },
	            {   "\r"        ,       "\\r"       ,       "\\\\r"     },
	            {   "\t"        ,       "\\t"       ,       "\\\\t"     },
	            {   "\u001A"    ,       "\\x1A"     ,       "\\\\Z"     },
	            {   "\\"        ,       "\\\\"      ,       "\\\\\\\\"  }
	    };

	    sqlTokens = new HashMap<String,String>();
	    String patternStr = "";
	    for (String[] srr : search_regex_replacement)
	    {
	        sqlTokens.put(srr[0], srr[2]);
	        patternStr += (patternStr.isEmpty() ? "" : "|") + srr[1];            
	    }
	    sqlTokenPattern = Pattern.compile('(' + patternStr + ')');
	}


	public static String escape(String s)
	{
	    Matcher matcher = sqlTokenPattern.matcher(s);
	    StringBuffer sb = new StringBuffer();
	    while(matcher.find())
	    {
	        matcher.appendReplacement(sb, sqlTokens.get(matcher.group(1)));
	    }
	    matcher.appendTail(sb);
	    return sb.toString();
	}
}
