package cowell.vn.api.backlog4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nulabinc.backlog4j.BacklogClient;
import com.nulabinc.backlog4j.ChangeLog;
import com.nulabinc.backlog4j.Issue;
import com.nulabinc.backlog4j.Issue.StatusType;
import com.nulabinc.backlog4j.IssueComment;
import com.nulabinc.backlog4j.ResponseList;
import com.nulabinc.backlog4j.api.option.GetIssuesCountParams;
import com.nulabinc.backlog4j.api.option.GetIssuesParams;

import cowell.vn.api.backlog4j.auth.BackLogAuth;
import cowell.vn.api.google.UserUtils;
import cowell.vn.util.DateUtils;

public class IssueUtils {
	static BacklogClient backlog = BackLogAuth.buildBacklogClient();

	
	/* Task 2*/
	public static List<List<Object>> getData4Task2() throws IOException{
		List<List<Object>> data = new ArrayList<List<Object>>();
		
		for(Object userId : UserUtils.getListUserFromSheet()){
			Map<Date, Object> mapData = getDataForUser(userId.toString());
			
			//List<Object> data1 = new ArrayList<Object>();
			//data1.add(val);
			//data.add(data1);
		}
		
		return data;
	}
	public static Map<Date, Object> getDataForUser(String userId){
		Map<Date, Object> mapData = new HashMap<Date, Object>();
		ResponseList<Issue> lstIssue = getIssuesWithUser(userId.toString());
		
		for (Issue issue : lstIssue) {
			getDataForIssue(mapData, issue);
		}
		
		return mapData;
	}
	public static void getDataForIssue(Map<Date, Object> mapData, Issue issue){
		ResponseList<IssueComment> lstComment = backlog.getIssueComments(issue.getId());
		for (IssueComment issueComment : lstComment) {
			//1.updated
			Date updatedDate = issueComment.getUpdated();
			//2.change log
			List<ChangeLog> lstChangeLog = issueComment.getChangeLog();
			for (ChangeLog changeLog : lstChangeLog) {
				if("actualHours".equals(changeLog.getField())){
					String actualHour = changeLog.getNewValue();
					mapData.put(updatedDate, actualHour);
					break;
				}
			}
		}
	}
	public static ResponseList<Issue> getIssuesWithUser(String userId){
		GetIssuesParams params = new GetIssuesParams(Arrays.asList());
		params.assigneeIds(Arrays.asList(userId));
		
		return backlog.getIssues(params);
	}
	/* .Task 2*/
	
	
	/* Task 1*/
	public static int getHasseiList(String[] projectIdArr, String[] issueTypeIdArr){
		return getHasseiList(projectIdArr, issueTypeIdArr, DateUtils.getCurDate());
	}
	public static int getHasseiList(String[] projectIdArr, String[] issueTypeIdArr, String date){
		GetIssuesCountParams params = new GetIssuesCountParams(Arrays.asList(projectIdArr));
		params.issueTypeIds(Arrays.asList(issueTypeIdArr));
		params.createdSince(date);
		params.createdUntil(DateUtils.getNextDate(date));
		
		int countIssue = backlog.getIssuesCount(params);
		return countIssue;
	}
	
	public static int getShoriZumiList(String[] projectIdArr, String[] issueTypeIdArr){
		return getShoriZumiList(projectIdArr, issueTypeIdArr, DateUtils.getCurDate());
	}
	public static int getShoriZumiList(String[] projectIdArr, String[] issueTypeIdArr, String date){
		GetIssuesCountParams params = new GetIssuesCountParams(Arrays.asList(projectIdArr));
		params.issueTypeIds(Arrays.asList(issueTypeIdArr));
		List<Issue.StatusType> statuses = new ArrayList<>();
		statuses.add(StatusType.Closed);
		params.statuses(statuses);
		params.createdSince(date);
		params.createdUntil(DateUtils.getNextDate(date));
		
		int countIssue = backlog.getIssuesCount(params);
		return countIssue;
	}
	
	public static int getZanList(String[] projectIdArr, String[] issueTypeIdArr){
		return getZanList(projectIdArr, issueTypeIdArr, DateUtils.getCurDate());
	}
	public static int getZanList(String[] projectIdArr, String[] issueTypeIdArr, String date){
		GetIssuesCountParams params = new GetIssuesCountParams(Arrays.asList(projectIdArr));
		params.issueTypeIds(Arrays.asList(issueTypeIdArr));
		List<Issue.StatusType> statuses = new ArrayList<>();
		statuses.add(StatusType.Open);
		statuses.add(StatusType.InProgress);
		statuses.add(StatusType.Resolved);
		params.statuses(statuses);
		
		int countIssue = backlog.getIssuesCount(params);
		return countIssue;
	}
	/* .Task 1*/
	
}