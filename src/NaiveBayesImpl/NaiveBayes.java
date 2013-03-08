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

		Set<String> stopWords = Utility.getStopWords();
		List<File> trainingFiles = FileInputReader.getAllFilesInFolder(Constants.TRAINING_FOLDER);
		for (File file : trainingFiles) {
			String label = Utility.getLabelFromFileName(file.getName());
			String fileContents = FileInputReader.getContent(file);
			KnowledgeMap.clear();
			if (fileContents != null) {
				String[] bagOfWords = fileContents.split(" ");
				for (String str : bagOfWords) {
					if (!stopWords.contains(str)) {
						KnowledgeMap.putWordCount(str);
					}
				}
				Utility.persistMapToDb(KnowledgeMap.getMap(), label);
			}
		}

		String testContent = FileInputReader.getContent(new File(Constants.TEST_FILE));
		List<String> labels = DBReader.getDistinctLabels();
		if (testContent != null) {
			String[] bagOfTestWords = testContent.split(" ");
			for (String label : labels) {
				double probability = 0d;
				KnowledgeMap.load(DBReader.getWordCountForLabel(label));
				for (String testWord : bagOfTestWords) {
					if(!stopWords.contains(testWord)){
						probability *= KnowledgeMap.getWordCount(testWord) / KnowledgeMap.getSize();
					}
				}
				System.out.println("Probability of test file being " + label + " = " + probability);
			}
		}
	}
}
