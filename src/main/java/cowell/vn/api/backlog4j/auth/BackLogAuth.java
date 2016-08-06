package cowell.vn.api.backlog4j.auth;

import java.net.MalformedURLException;

import com.nulabinc.backlog4j.BacklogClient;
import com.nulabinc.backlog4j.BacklogClientFactory;
import com.nulabinc.backlog4j.conf.BacklogConfigure;
import com.nulabinc.backlog4j.conf.BacklogJpConfigure;

import cowell.vn.constant.BackLogConstant;

public class BackLogAuth {
	public static BacklogClient buildBacklogClient(){
		try {
			// if your space is in backlog.jp
			BacklogConfigure configure = new BacklogJpConfigure(BackLogConstant.SPACE_KEY).apiKey(BackLogConstant.API_KEY);
		
			// get the BacklogClient
			BacklogClient backlog = new BacklogClientFactory(configure).newClient();
			return backlog;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
