package msgr_client_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import slack_impl.SlackClientImpl;

import java.io.IOException;
import java.util.List;

@Component
public class SlackClientApi implements MsgrClientApi {

    @Autowired
    private SlackClientImpl slackClientImpl;

    @Override
    public void postToSlackChannels(List<String> messages) throws IOException {
        slackClientImpl.postToSlackChannels(messages);
    }
}
