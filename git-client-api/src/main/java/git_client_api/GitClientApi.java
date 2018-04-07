package git_client_api;

import java.io.IOException;
import java.util.List;

public interface GitClientApi {
    List<String> getMergeRequests() throws IOException;
}
