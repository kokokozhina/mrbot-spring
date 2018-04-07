package slack_impl;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SlackClientImpl {
    @Autowired
    private Channels channels;

    @Autowired
    private SlackSession session;

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
