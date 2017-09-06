package twitter;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class Tw {

    static String AccessToken = "74136375-ZxLtgsDLiEJ1NaWUHliYs6MoGE5I0vRFTf3k0N7FQ";
    static String AccessSecret = "2kYRIK1vAUZ1agQ4UB8lbk0iTbevWPF5Reg1xiUtysHlo";
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
        HttpGet request = new HttpGet("http://api.twitter.com/1.1/followers/ids.json?cursor=-1&screen_name=josdirksen");
        consumer.sign(request);

        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode + ":" + response.getStatusLine().getReasonPhrase());
        System.out.println(IOUtils.toString(response.getEntity().getContent()));
    }
}
