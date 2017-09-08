package twitter;

import org.apache.jena.base.Sys;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

/**
 * Created by Eudes on 08/09/2017.
 */
//https://www.youtube.com/watch?v=NQPeLRcVxYM
public class TwitterInfo {

    public static void main (String[] args){
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey("j4pTXd5p0jKJcqmXtDGdtxLvf")
                .setOAuthConsumerSecret("BDzgi4RENA9FwZzYeAAz2p3NBokTLMxM7v5WmYIkqr0NdAmaan")
                .setOAuthAccessToken("74136375-5hPoXiqMwpqwDFAVigmj2zmb0bKiOvutq3wLnXzM6")
                .setOAuthAccessTokenSecret("BdTvlt50mheJZyHPN7LRrDrK3gM2Y4adlhNa1QBBrutXF");

        TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
        twitter4j.Twitter twitter = tf.getInstance();

        try {
            List<Status> status = twitter.getHomeTimeline();
            for(Status s: status){
                System.out.println(s.getUser().getName() + "      " + s.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
