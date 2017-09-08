package twitter;

import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.util.List;

public class ReadTwitterElvis {

    //De Elvis
//    private static final String accessToken = "8345762-ZNhcgpF9mertMjPwHRW2CvC3HbNlsd2IjRSTwdV5Qf";
//    private static final String accessSecret = "R4ZYw2onDWiYTspZSOBlXNeGMRj3dI9gf713cMGxWQym0";
//    private static final String consumerKey = "KJphc7yL5UvbyR7p29gk3954T";
//    private static final String consumerSecret = "IO1cXehdg6dSG6QAXQZLxGXc0fLOpdFteuzVwpCIAoZWegJXWT";

    private static final String accessToken = "74136375-5hPoXiqMwpqwDFAVigmj2zmb0bKiOvutq3wLnXzM6";
    private static final String accessTokenSecret = "BdTvlt50mheJZyHPN7LRrDrK3gM2Y4adlhNa1QBBrutXF";
    private static final String consumerKey = "j4pTXd5p0jKJcqmXtDGdtxLvf";
    private static final String consumerSecret = "BDzgi4RENA9FwZzYeAAz2p3NBokTLMxM7v5WmYIkqr0NdAmaan";

    public static void main(String[] args) {

        try {
            Twitter twitter = TwitterFactory.getSingleton();
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
            twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));


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