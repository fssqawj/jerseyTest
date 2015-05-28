package cn.dataprocess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
public class WordTree {
	public static Map<String,String> iMap = new HashMap<String,String>();
	
	public static List<String> findDown(String word) throws ClassNotFoundException, SQLException{
		String driver = "com.mysql.jdbc.Driver";

        String url = "jdbc:mysql://localhost:3306/maya";


        String user = "root"; 


        String password = "root";
        Class.forName(driver);


        Connection conn = DriverManager.getConnection(url,user,password);
        
        //if(!conn.isClosed()) System.out.println("Succeeded connecting to the Database!");


        Statement statement = conn.createStatement();
        
        String sql = "select * from hpy_hpy where parent = '" + word + "'";
        
        ResultSet rs = statement.executeQuery(sql);
        
        List<String> res = new ArrayList<String>();
        
        while(rs.next()){
        	res.add(rs.getString("child"));
        }
        return res;
	}
	public static List<String> findEntity(String word) throws ClassNotFoundException, SQLException{
		String driver = "com.mysql.jdbc.Driver";

        String url = "jdbc:mysql://localhost:3306/maya";


        String user = "root"; 


        String password = "root";
        Class.forName(driver);


        Connection conn = DriverManager.getConnection(url,user,password);
        
        //if(!conn.isClosed()) System.out.println("Succeeded connecting to the Database!");


        Statement statement = conn.createStatement();
        
        String sql = "select * from ent_hpy where hpy = '" + word + "'";
        
        ResultSet rs = statement.executeQuery(sql);
        
        List<String> res = new ArrayList<String>();
        
        while(rs.next()){
        	res.add(rs.getString("ent"));
        }
        return res;
	}
	public static JSONObject solve(String word) throws ClassNotFoundException, SQLException, JSONException{
		if(iMap.containsKey(word))return null;
		iMap.put(word, "has");
		//System.out.println("word:" + word);
		JSONObject res = new JSONObject();
		List<String> word_down = findDown(word);
		
		if(word_down.size() == 0){
			List<String> ent_down = findEntity(word);
			//res.add(ent_down);
			//return ;
			
			JSONArray tem = new JSONArray();
			for(int i = 0;i < ent_down.size();i ++){
				tem.put(i,ent_down.get(i));
			}
			res.put(word, tem);
			return res;
		}
		JSONArray tem = new JSONArray();
		for(String key : word_down){
			//System.out.println("key:" + key);
			tem.put(solve(key));
			//System.out.println("tem:" + tem.toString());
			
		}
		res.put(word,tem);
		return res;
	}
	
	public static JSONObject getTree(String tem) throws IOException, ClassNotFoundException, SQLException, JSONException {
		// TODO Auto-generated method stub
		//List<List<String>> res = new ArrayList<List<String>>();
        
        
        
        	iMap.clear();
        	JSONObject res = solve(tem);
        	
        	return res;
        
        
	}

}
