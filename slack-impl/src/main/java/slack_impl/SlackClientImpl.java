package slack_impl;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import msgr_client_api.MsgrClientApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SlackClientImpl implements MsgrClientApi {
    @Autowired
    private Channels channels;

    @Autowired
    private SlackSession session;

    @Override
    public void postToSlackChannels(List<String> messages) throws IOException {

        session.connect();

        for (String channel : channels.getChannels()) {
            SlackChannel slackChannel = session.findChannelByName(channel);
            for (String message : messages) {
                session.sendMessage(slackChannel, message); //make sure bot is a member of the channel.
            }
        }

        session.disconnect();
    }
}
