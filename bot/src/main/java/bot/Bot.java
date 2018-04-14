package bot;

import git_client_api.GitClientApi;
import msgr_client_api.MsgrClientApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
@EnableScheduling
@ComponentScan({"git_client_api", "gitlab_client_impl", "msgr_client_api", "slack_impl"})
public class Bot {

    @Autowired
    private GitClientApi gitClientApi;

    @Autowired
    private MsgrClientApi msgrClientApi;

    @Scheduled(fixedDelay = 10000)
    public void notifyOfMergeRequests() throws IOException {
        msgrClientApi.postToSlackChannels(gitClientApi.getMergeRequests());
    }

    public static void main(String[] args) {
        SpringApplication.run(Bot.class, args);
    }
}
