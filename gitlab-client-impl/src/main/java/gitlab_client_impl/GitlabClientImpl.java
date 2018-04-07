package gitlab_client_impl;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabGroup;
import org.gitlab.api.models.GitlabMergeRequest;
import org.gitlab.api.models.GitlabProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GitlabClientImpl {

    @Autowired
    private Groups groups;

    @Autowired
    private GitlabAPI connection;

    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    public GitlabAPI getConnection() {
        return connection;
    }

    public void setConnection(GitlabAPI connection) {
        this.connection = connection;
    }

    private List<GitlabGroup> getGroups(GitlabAPI api) throws IOException {
        return api.getGroups();
    }

    private List<GitlabProject> getGroupProjects(GitlabAPI api, GitlabGroup group) throws IOException {
        return api.getGroupProjects(group);
    }

    private List<GitlabMergeRequest> getMergeRequests(GitlabAPI api, GitlabProject project) throws IOException {
        return api.getMergeRequests(project);
    }

    private List<GitlabMergeRequest> excludeWipMergeRequests(List<GitlabMergeRequest> mergeRequests) {
        List<GitlabMergeRequest> mergeRequestsWithoutWip = new ArrayList<GitlabMergeRequest>();

        for (GitlabMergeRequest mergeRequest : mergeRequests) {
            if (!mergeRequest.getTitle().contains("WIP")) {
                mergeRequestsWithoutWip.add(mergeRequest);
            }
        }

        return mergeRequestsWithoutWip;
    }

    private String printMergeRequest(GitlabMergeRequest mergeRequest) {
        String mrToString = "Merge Request №" + mergeRequest.getId()
                + "\ntitle: " + mergeRequest.getTitle()
                + "\nfrom: " + mergeRequest.getSourceBranch()
                + "\nto: " + mergeRequest.getTargetBranch();
        return mrToString;
    }

    private List<String> convertMergeRequestsToStringList(List<GitlabMergeRequest> mergeRequests) {
        return mergeRequests.stream().map(s -> printMergeRequest(s)).collect(Collectors.toList());
    }

    public List<String> getRequiringMergeRequests() throws IOException {

        List<GitlabMergeRequest> requiringMergeRequests = new ArrayList<GitlabMergeRequest>();


        Set<String> setOfGroupsRequiringMergeRequests
                = new HashSet<String>(groups.getGroups());

        List<GitlabGroup> groups = this.getGroups(connection);
        for (GitlabGroup group : groups) {

            if (!setOfGroupsRequiringMergeRequests.contains(group.getName())) {
                continue;
            }

            List<GitlabProject> projects = this.getGroupProjects(connection, group);
            for (GitlabProject project : projects) {

                List<GitlabMergeRequest> mergeRequests = this.getMergeRequests(connection, project);

                requiringMergeRequests.addAll(mergeRequests);
            }
        }

        requiringMergeRequests = this.excludeWipMergeRequests(requiringMergeRequests);
        return this.convertMergeRequestsToStringList(requiringMergeRequests);
    }

}
