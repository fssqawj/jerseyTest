package cn.service;
import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;  
import javax.ws.rs.Path;  
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;  
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import cn.dataprocess.JDBCTest;
import cn.dataprocess.WordTree;
@Path("/func")
public class HelloService {
	@Path("/down/{contact}")
	@GET
	@Produces("application/json;charset=UTF-8")
	public JSONObject helloWorld(@PathParam("contact") String contact) throws ClassNotFoundException, IOException, SQLException, JSONException{
		WordTree wt = new WordTree();
		
		return wt.getTree(contact);
	}
	@Path("/sys/{word}")
	@GET
	@Produces("application/json;charset=UTF-8")
	public  JSONObject sysnonym(@PathParam("word") String word) throws ClassNotFoundException, SQLException, IOException, JSONException{
		/*JSONObject address = new JSONObject();  
        JSONArray addresses = new JSONArray();  
        try {  
            addresses.put(0, "address1");  
            addresses.put(1, "address2");  
            address.put("addresses", addresses);  
        } catch (JSONException e) {  
            e.printStackTrace();  
        }  */
		JDBCTest jbc = new JDBCTest();
		JSONObject res = jbc.getSys(word);
        return res;  
	}
}