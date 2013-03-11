package NaiveBayesImpl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.KnowledgeMap;
import util.Constants;
import util.Utility;
import dbhandler.DBReader;
import filehandler.FileInputReader;

public class NaiveBayes {
//Refer - http://www.nils-haldenwang.de/computer-science/machine-learning/how-to-apply-naive-bayes-classifiers-to-document-classification-problems
	public static void main(String[] args) {

		boolean train = false;
		Set<String> stopWords = Utility.getStopWords();
		if (train) {
			List<File> trainingFiles = FileInputReader.getAllFilesInFolder(Constants.TRAINING_FOLDER);
			for (File file : trainingFiles) {
				String label = Utility.getLabelFromFileName(file.getName());
				String fileContents = FileInputReader.getContent(file);
				KnowledgeMap knowledgeMap = new KnowledgeMap();
				if (fileContents != null) {
					String[] bagOfWords = fileContents.split(" ");
					for (String str : bagOfWords) {
						if (!stopWords.contains(str)) {
							knowledgeMap.putWordCount(str);
						}
						/*
						 * if(knowledgeMap.getSize() > 3000){
						 * Utility.persistMapToDb(knowledgeMap.getMap(), label);
						 * knowledgeMap.clear(); knowledgeMap = new
						 * KnowledgeMap(); }
						 */
					}
					Utility.persistMapToDb(knowledgeMap.getMap(), label);
				}
			}
		} else {
			String testContent = FileInputReader.getContent(new File(Constants.TEST_FILE));
			KnowledgeMap knowledgeMapForTesFile = new KnowledgeMap();
			knowledgeMapForTesFile.makeKnowledgeMap(testContent);
			List<String> labels = DBReader.getDistinctLabels();
			if (testContent != null) {
				for (String label : labels) {					
					KnowledgeMap knowledgeMap = DBReader.getWordCountMapForLabel(label);
					Map<String,Double> labelProb = new HashMap<String,Double>();					
					double probability = 0;
					for(String str : knowledgeMapForTesFile.getKeys()){
						if(!stopWords.contains(str)){
							int totalWordCount = DBReader.getTotalCountForWord(str)+(labels.size()*1);
							probability += knowledgeMapForTesFile.getWordCount(str)*getProbability(knowledgeMap, str, totalWordCount);
						}
					}
					labelProb.put(label, probability);
					System.out.println(" Label " + label + "  prob -- " + probability);								
				}

			}
		}
	}
	
	private static double getProbability(KnowledgeMap knowledgeMap,String testWord,int totalWordCount){
		double currentProbability = 0;
		int wordCountRead = 1;
		if(knowledgeMap.getWordCount(testWord) != null){
			wordCountRead += knowledgeMap.getWordCount(testWord);									
		}
		double val = wordCountRead / totalWordCount;
		double logvalue = Math.log(val);
		if ((logvalue != Double.POSITIVE_INFINITY && 
				logvalue != Double.NEGATIVE_INFINITY) &&
				  logvalue >= 0) {
			currentProbability += logvalue;
		}
		return currentProbability;
	}
}
