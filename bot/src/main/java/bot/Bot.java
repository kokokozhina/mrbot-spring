package bot;

import git_client_api.GitClientApi;
import msgr_client_api.MsgrClientApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@SpringBootApplication
@ComponentScan({"git_client_api", "gitlab_client_impl", "msgr_client_api", "slack_impl"})
public class Bot {

    @Autowired
    private GitClientApi gitClientApi;

    @Autowired
    private MsgrClientApi msgrClientApi;

    @PostConstruct
    public void main() throws IOException {
        msgrClientApi.postToSlackChannels(gitClientApi.getMergeRequests());
    }

    public static void main(String[] args) {
        SpringApplication.run(Bot.class, args);
    }
}
