package util;

public interface Constants {
	String TRAINING_FOLDER = "data/training/";
	String TEST_FILE = "data/test.txt";
	String STOP_WORD_FILE = "data/stopwords.txt";
	
	String DB_NAME  = "NaiveBayes";
	String DB_USER_NAME = "root";
	String DB_PASSWORD = "root";
	
	//Query
	String GET_RESULT_FOR_LABEL = "select * from knowledgemodel where label = ";
	String GET_DISTINCT_LABEL = "select distinct label from knowledgemodel";
	String GET_TOTAL_COUNT = "select count(*) as totalcount from knowledgemodel where label =";
}
