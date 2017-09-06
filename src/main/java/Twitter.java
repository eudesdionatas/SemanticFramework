/*
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;

*/
/**
 * Created by Eudes on 05/09/2017.
 *//*

public class Twitter {


    public void testUserTimelineWithAuthSample() throws Exception {
        //This will read the timeline of your account.
        String method = "GET";
        String url = "https://api.twitter.com/1.1/statuses/user_timeline.json";

        String oAuthConsumerKey = "Your value here.";
        String oAuthConsumerSecret = "Your value here."; //<--- DO NOT SHARE THIS VALUE

        String oAuthAccessToken = "Your value here.";
        String oAuthAccessTokenSecret = "Your value here."; //<--- DO NOT SHARE THIS VALUE

        String oAuthNonce = String.valueOf(System.currentTimeMillis());
        String oAuthSignatureMethod = "HMAC-SHA1";
        String oAuthTimestamp = time();
        String oAuthVersion = "1.0";

        String signatureBaseString1 = method;
        String signatureBaseString2 = url;
        String signatureBaseString3Templ = "oauth_consumer_key=%s&oauth_nonce=%s&oauth_signature_method=%s&oauth_timestamp=%s&oauth_token=%s&oauth_version=%s";
        String signatureBaseString3 = String.format(signatureBaseString3Templ,
                oAuthConsumerKey,
                oAuthNonce,
                oAuthSignatureMethod,
                oAuthTimestamp,
                oAuthAccessToken,
                oAuthVersion);

        String signatureBaseStringTemplate = "%s&%s&%s";
        String signatureBaseString = String.format(signatureBaseStringTemplate,
                URLEncoder.encode(signatureBaseString1, "UTF-8"),
                URLEncoder.encode(signatureBaseString2, "UTF-8"),
                URLEncoder.encode(signatureBaseString3, "UTF-8"));

        System.out.println("signatureBaseString: " + signatureBaseString);

        String compositeKey = URLEncoder.encode(oAuthConsumerSecret, "UTF-8") + "&" + URLEncoder.encode(oAuthAccessTokenSecret, "UTF-8");

        String oAuthSignature = computeSignature(signatureBaseString, compositeKey);
        System.out.println("oAuthSignature       : " + oAuthSignature);

        String oAuthSignatureEncoded = URLEncoder.encode(oAuthSignature, "UTF-8");
        System.out.println("oAuthSignatureEncoded: " + oAuthSignatureEncoded);

        String authorizationHeaderValueTempl = "OAuth oauth_consumer_key=\"%s\", oauth_nonce=\"%s\", oauth_signature=\"%s\", oauth_signature_method=\"%s\", oauth_timestamp=\"%s\", oauth_token=\"%s\", oauth_version=\"%s\"";

        String authorizationHeaderValue = String.format(authorizationHeaderValueTempl,
                oAuthConsumerKey,
                oAuthNonce,
                oAuthSignatureEncoded,
                oAuthSignatureMethod,
                oAuthTimestamp,
                oAuthAccessToken,
                oAuthVersion);
        System.out.println("authorizationHeaderValue: " + authorizationHeaderValue);


        System.out.println("url: " + url);
        System.out.println("authorizationHeaderValue:" + authorizationHeaderValue);

        */
/*GetMethod *//*
HttpGet getMethod = new HttpGet();*/
/*GetMethod(url);*//*

        getMethod.addHeader("Authorization", authorizationHeaderValue);
//        getMethod.addRequestHeader("Authorization", authorizationHeaderValue);
        HttpClient cli = new HttpClient();
        int status = cli.executeMethod(getMethod);
        System.out.println("Status:" + status);

        long responseContentLength = getMethod.getResponseContentLength();
        System.out.println("responseContentLength:" + responseContentLength);

        String response = getMethod.getResponseBodyAsString();
        System.out.println("response: " + response);
    }


    private static String computeSignature(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException, Exception {
        SecretKey secretKey = null;

        byte[] keyBytes = keyString.getBytes();
        secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

        Mac mac = Mac.getInstance("HmacSHA1");

        mac.init(secretKey);

        byte[] text = baseString.getBytes();

        return new String(Base64.encodeBase64(mac.doFinal(text))).trim();
    }

    private String time() {
        long millis = System.currentTimeMillis();
        long secs = millis / 1000;
        return String.valueOf(secs);
    }

}*/
