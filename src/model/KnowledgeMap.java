package model;

import java.util.HashMap;
import java.util.Map;

public class KnowledgeMap {
	private static Map<String, Integer> wordCount = new HashMap<String, Integer>();

	public static Integer getWordCount(String word) {
		return wordCount.get(word);
	}

	public static void putWordCount(String word) {
		int count = 0;
		if (wordCount.containsKey(word)) {
			count = wordCount.get(word);
		}
		count += 1;
		wordCount.put(word, count);
	}

	public static void clear() {
		wordCount.clear();
		wordCount = new HashMap<String, Integer>();
	}

	public static Map<String, Integer> getMap() {
		return wordCount;
	}

	public static void load(Map<String, Integer> wordCountForLabel) {
		wordCount.clear();
		wordCount = new HashMap<String, Integer>();
		wordCount = wordCountForLabel;
	}

	public static Integer getSize() {
		if(wordCount != null)
			return wordCount.size();
		
		return 0;
	}	

}
