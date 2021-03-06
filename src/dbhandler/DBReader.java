package dbhandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;

import model.Knowledge;
import model.KnowledgeMap;
import util.Constants;

public class DBReader {

	public static KnowledgeMap getWordCountMapForLabel(String label) {
		String query = Constants.GET_RESULT_FOR_LABEL+"'"+label.toLowerCase()+"'";
		ResultSet results = getResults(query, DBConnect.getConnection());
		KnowledgeMap knowledge = new KnowledgeMap();
		if (results != null) {
			try {
				while (results.next()) {
					knowledge.addWordAndCount(results.getString("word"),results.getInt("count"));
				}				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					results.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return knowledge;
	}	

	public static List<String> getDistinctLabels() {
		String query = Constants.GET_DISTINCT_LABEL;
		ResultSet results = getResults(query, DBConnect.getConnection());
		List<String> labels = new ArrayList<String>();
		if (results != null) {
			try {
				while (results.next()) {
					labels.add(results.getString("label"));
				}				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					results.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return labels;
	}

	public static void insert(String insertStatement) {
		updateDb(insertStatement);		
	}

	

	public static void update(String updateStmt) {
		updateDb(updateStmt);		
	}

	public static Knowledge getExistingKnowledge(String key, String label) {
		String query = "select * from knowledgemodel where word = '"+key.toLowerCase()+"' and label='"+label.toLowerCase()+"'";
		ResultSet results = getResults(query, DBConnect.getConnection());
		Knowledge knowledge = null;
		if (results != null) {
			try {
				while (results.next()) {
					knowledge = new Knowledge(results.getInt("id"),results.getString("word"),results.getInt("count"),results.getString("label"));
				}				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					results.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return knowledge;
	}

	public static int getTotalCountForWord(String word) {
		String query = Constants.GET_TOTAL_COUNT+"'"+word.toLowerCase()+"'";
		ResultSet results = getResults(query, DBConnect.getConnection());
		int count = 0;
		if (results != null) {
			try {
				while (results.next()) {
					count = results.getInt("totalcount");
				}				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					results.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return count;		
	}
	

	private static ResultSet getResults(String query,Connection conn) {		
	
		query = Util.escape(query);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

		}catch(MySQLSyntaxErrorException me){
			System.out.println("Error in select query ="+query);
		}catch (SQLException e) {
			System.out.println("Error in select query ="+query);
			e.printStackTrace();
		}
		return rs;
	}
	
	private static void updateDb(String insertStatement) {
		try {
			Statement st = DBConnect.getConnection().createStatement();
			st.executeUpdate(insertStatement);
		} catch(MysqlDataTruncation mde){
			System.out.println("Data to long = "+insertStatement);
		}
		catch (SQLException e) {	
			System.out.println("Error in insert/update ="+insertStatement);
			e.printStackTrace();
		}
	}

}
