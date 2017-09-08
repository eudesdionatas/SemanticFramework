package garbage;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



public class Tw {



    static String AccessToken = "74136375-5hPoXiqMwpqwDFAVigmj2zmb0bKiOvutq3wLnXzM6";
    static String AccessSecret = "BdTvlt50mheJZyHPN7LRrDrK3gM2Y4adlhNa1QBBrutXF";
    static String ConsumerKey = "gtzmO7pzhlqZZvCwpDGDvbFSG ";
    static String ConsumerSecret = "QHIYRUsC0GtkNAf6vHc3T04njOqy758GQM0vOvR1x7pwLltiCM";

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(
                ConsumerKey,
                ConsumerSecret);

        consumer.setTokenWithSecret(AccessToken, AccessSecret);
        HttpGet request = new HttpGet("https://api.twitter.com/1.1/followers/ids.json?cursor=-1&screen_name=eudesdionatas");
//        &user_id=74136375
        consumer.sign(request);

        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode + ":" + response.getStatusLine().getReasonPhrase());
        System.out.println(IOUtils.toString(response.getEntity().getContent()));
    }


}
