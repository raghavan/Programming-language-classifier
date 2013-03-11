package util;

public interface Constants {
	String TRAINING_FOLDER = "data/training/";
	String TEST_FILE = "data/test.java";
	//String TEST_FILE = "data/test/MSApp2.java";
	String STOP_WORD_FILE = "data/stopwords.txt";

	
	//Query
	String GET_RESULT_FOR_LABEL = "select * from knowledgemodel where word = ";
	String GET_DISTINCT_LABEL = "select distinct label from knowledgemodel";
	String GET_TOTAL_COUNT = "select count(*) as totalcount from knowledgemodel where word =";
	
	//DB props
	String dbDriver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/programclassify";
	String username = "root";
	String password = "root";
}
