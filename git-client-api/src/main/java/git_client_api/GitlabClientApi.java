package git_client_api;

import gitlab_client_impl.GitlabClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

@Component
@ComponentScan
public class GitlabClientApi implements GitClientApi {

    @Autowired
    private GitlabClientImpl gitlabClientImpl;

    @Override
    public List<String> getMergeRequests() throws IOException {
        return gitlabClientImpl.getRequiringMergeRequests();
    }

}
