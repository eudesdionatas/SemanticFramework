package garbage;

import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.util.List;

public class App {

    private static final String accessToken = "8345762-ZNhcgpF9mertMjPwHRW2CvC3HbNlsd2IjRSTwdV5Qf";
    private static final String accessSecret = "R4ZYw2onDWiYTspZSOBlXNeGMRj3dI9gf713cMGxWQym0";
    private static final String consumerKey = "KJphc7yL5UvbyR7p29gk3954T";
    private static final String consumerSecret = "IO1cXehdg6dSG6QAXQZLxGXc0fLOpdFteuzVwpCIAoZWegJXWT";

    public static void main(String[] args) {
        new App().run(args);
    }

    private void run(String... args) {
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
            twitter.setOAuthAccessToken(new AccessToken(accessToken, accessSecret));

            User user = twitter.verifyCredentials();

            List<Status> statuses = twitter.getHomeTimeline();
            System.out.println("Showing @" + user.getScreenName() + "'s home timeline.");

            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }
}
