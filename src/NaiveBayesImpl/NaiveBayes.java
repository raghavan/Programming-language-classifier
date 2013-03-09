package NaiveBayesImpl;

import java.io.File;
import java.util.List;
import java.util.Set;

import model.KnowledgeMap;
import util.Constants;
import util.Utility;
import dbhandler.DBReader;
import filehandler.FileInputReader;

public class NaiveBayes {

	public static void main(String[] args) {

		boolean train = true;
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
						/*if(knowledgeMap.getSize() > 3000){
							Utility.persistMapToDb(knowledgeMap.getMap(), label);
							knowledgeMap.clear();
							knowledgeMap = new KnowledgeMap();
						}*/
					}
					Utility.persistMapToDb(knowledgeMap.getMap(), label);					
				}
			}
		} else {
			String testContent = FileInputReader.getContent(new File(Constants.TEST_FILE));
			List<String> labels = DBReader.getDistinctLabels();
			if (testContent != null) {
				String[] bagOfTestWords = testContent.split(" ");
				double highestCount = 0;
				String resultLabel = "";
				for (String label : labels) {
					double probability = Math.log(0.00001);
					KnowledgeMap knowledgeMap = DBReader.getWordCountForLabel(label);
					int totalWordCount = DBReader.getTotalWordsForLabel(label);
					for (String testWord : bagOfTestWords) {
						if (!stopWords.contains(testWord)) {
							int wordCountRead = 1;
							if (knowledgeMap.getWordCount(testWord) != null) {
								wordCountRead += knowledgeMap.getWordCount(testWord);
							}
							double val = wordCountRead / totalWordCount;	
							probability *= Math.log(val);//Utility.logBase2(val);
							//probability *= (wordCountRead/totalWordCount); 
						}
					}
					if (probability > highestCount) {
						highestCount = probability;
						resultLabel = new String(label);
					}
				}
				System.out.println("The test file is identified as " + resultLabel.toUpperCase()
						+ " with log likelihood of " + highestCount);
			}
		}
	}
}
