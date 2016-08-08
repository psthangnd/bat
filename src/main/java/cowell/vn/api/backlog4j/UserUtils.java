package cowell.vn.api.backlog4j;

import java.util.ArrayList;
import java.util.List;

import com.nulabinc.backlog4j.BacklogClient;
import com.nulabinc.backlog4j.ResponseList;
import com.nulabinc.backlog4j.User;

import cowell.vn.api.backlog4j.auth.BackLogAuth;

public class UserUtils {
	static BacklogClient backlog = BackLogAuth.buildBacklogClient();
	
	public static void main(String[] args) {
		getListUserIdFromAPI();
	}
	
	public static User getUser(String userId){
		return backlog.getUser(userId);
	}
	
	public static List<Object> getListUserIdFromAPI(){
		List<Object> lstUser = new ArrayList<Object>();
		
		ResponseList<User> rlu = backlog.getUsers();
		for (User user : rlu) {
			lstUser.add(user.getId());
			System.out.println(user.getId() + "\t" + user.getName());
		}
		
		return lstUser;
	}
	
}