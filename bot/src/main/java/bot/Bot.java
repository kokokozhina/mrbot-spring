package bot;

import git_client_api.GitClientApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@SpringBootApplication
@ComponentScan({"git_client_api", "gitlab_client_impl"})
public class Bot {

    @Autowired
    private GitClientApi g;

    @PostConstruct
    public void main() throws IOException {
        PrintWriter pw = new PrintWriter("TestOutputFile.txt");
        List<String> l = g.getMergeRequests();
        for (String s : l) {
            pw.write(s + "\n");
        }

        pw.flush();
        pw.close();
    }

    public static void main(String[] args) {
        SpringApplication.run(Bot.class, args);
    }
}
