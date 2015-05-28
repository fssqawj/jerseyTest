package cn.dataprocess;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.*;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
public class JDBCTest {
	public JSONObject getSys(String tem) throws ClassNotFoundException, SQLException, IOException, JSONException{
		System.out.println(tem);

        String driver = "com.mysql.jdbc.Driver";

        String url = "jdbc:mysql://localhost:3306/maya";


        String user = "root"; 


        String password = "root";
        Class.forName(driver);


        Connection conn = DriverManager.getConnection(url,user,password);
        
        if(!conn.isClosed()) System.out.println("Succeeded connecting to the Database!");


        Statement statement = conn.createStatement();

        //BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        //String tem = "";
        //System.out.print("Input Word:");
        //while((tem = br.readLine())!=null){
     	   
        
        JSONObject result = new JSONObject();  
        JSONArray upword = new JSONArray();
        JSONArray sysword = new JSONArray();
            String sql = "select A.name as name, C.name as tname from check_entity as A left outer join check_hhpair as B on A.id = B.entity_id left outer join check_hyper as C on B.hyper_id = C.id where A.name = '" + tem + "'";

            ResultSet rs = statement.executeQuery(sql);



            //System.out.println(tem + "的上位词：");
            int cnt = 0;
            while(rs.next()) {
            	System.out.print(rs.getString("tname") + ",");
            	upword.put(cnt, rs.getString("tname"));
            	cnt ++;
            }
            //System.out.println();
            
            result.put("上位词", upword);
            sql = "select * from check_extractsynonym as A left outer join check_entity as B on A.entity_id = B.id where B.name = '" + tem + "'";
        	

            rs = statement.executeQuery(sql);
            
            System.out.println(tem + "的同义词：");
            cnt = 0;
            while(rs.next()) {
            	System.out.print("[" + rs.getString("synonym") + "," + rs.getDouble("confident") + "]");
            	JSONObject t = new JSONObject(); 
            	t.put( rs.getString("synonym"),rs.getDouble("confident"));
            	sysword.put(cnt,t);
            	cnt ++;
            }
            result.put("同义词", sysword);
            //System.out.println();
            rs.close();
	            
	            
	            //System.out.print("Input Word:");
        //}
         
        conn.close();
        return result;
	} 
	public static String change(String a) throws UnsupportedEncodingException{
		return new String(a.getBytes("ISO-8859-1"),"UTF-8");  
	}
}
