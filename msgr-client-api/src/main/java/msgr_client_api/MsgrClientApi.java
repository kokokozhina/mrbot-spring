package msgr_client_api;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

public interface MsgrClientApi {
    void postToSlackChannels(List<String> messages) throws IOException;
}
