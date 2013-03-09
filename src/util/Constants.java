package util;

public interface Constants {
	String TRAINING_FOLDER = "data/training/";
	String TEST_FILE = "data/test/linkedlist.c";
	String STOP_WORD_FILE = "data/stopwords.txt";

	
	//Query
	String GET_RESULT_FOR_LABEL = "select * from knowledgemodel where label = ";
	String GET_DISTINCT_LABEL = "select distinct label from knowledgemodel";
	String GET_TOTAL_COUNT = "select count(*) as totalcount from knowledgemodel where label =";
	
	//DB props
	String dbDriver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:8889/NaiveBayes";
	String username = "root";
	String password = "root";
}
