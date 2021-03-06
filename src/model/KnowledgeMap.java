package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import util.Utility;

public class KnowledgeMap {
	private Map<String, Integer> wordCount = new HashMap<String, Integer>();

	public Integer getWordCount(String word) {
		return wordCount.get(word);
	}

	public void putWordCount(String word) {
		int count = 0;
		if (wordCount.containsKey(word)) {
			count = wordCount.get(word);
		}
		count += 1;
		wordCount.put(word, count);
	}

	public void addWordAndCount(String word, Integer count) {
		if (wordCount.containsKey(word)) {
			count += wordCount.get(word);
		}
		wordCount.put(word, count);
	}

	public void clear() {
		wordCount.clear();
		wordCount = new HashMap<String, Integer>();
	}

	public Map<String, Integer> getMap() {
		return wordCount;
	}

	public Integer getSize() {
		if (wordCount != null)
			return wordCount.size();
		return 0;
	}

	public void print() {
		for (String str : wordCount.keySet()) {
			System.out.println("Word = " + str + " count = " + wordCount.get(str));
		}
	}

	public void makeKnowledgeMap(String content) {
		Set<String> stopWords = Utility.getStopWords();
		String[] bagOfWords = content.split(" ");
		for (String str : bagOfWords) {
			if (!stopWords.contains(str)) {
				this.putWordCount(str);
			}
		}
	}

	public Set<String> getKeys() {
		return wordCount.keySet();
	}

}
