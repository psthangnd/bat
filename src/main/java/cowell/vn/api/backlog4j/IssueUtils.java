package cowell.vn.api.backlog4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nulabinc.backlog4j.BacklogClient;
import com.nulabinc.backlog4j.Issue;
import com.nulabinc.backlog4j.Issue.StatusType;
import com.nulabinc.backlog4j.api.option.GetIssuesCountParams;

import cowell.vn.api.backlog4j.auth.BackLogAuth;
import cowell.vn.util.DateUtils;

public class IssueUtils {
	static BacklogClient backlog = BackLogAuth.buildBacklogClient();

	
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
	
}