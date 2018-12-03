package cosc4353;

import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**3
 Class used to tweet during a session of our game

  Dan Martinez
 **/

public class Tweets {

    private String consumerAccess ="XXXX";
    private String consumerSecret ="XXXX";
    private String twaccessToken = "XXXX";
    private String twsecretToken = "XXXX";
    private Twitter tweets;
    private List<Status> statuses;
    private String user;


    private void setConsumerAccess(String consumerAccess) {
        this.consumerAccess = consumerAccess;
    }

    private void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    private void setTwaccessToken(String twaccessToken) {
        this.twaccessToken = twaccessToken;
    }

    private void setTwsecretToken(String twsecretToken) {
        this.twsecretToken = twsecretToken;
    }

    //
    Tweets(){
        this.loadAccess();
        tweets = new TwitterFactory().getInstance();
        tweets.setOAuthConsumer(consumerAccess,consumerSecret);
        AccessToken accessToken = new AccessToken(twaccessToken, twsecretToken);
        tweets.setOAuthAccessToken(accessToken);

    }

    private Boolean loadAccess(){
        try{
            final String creds = "secrets_DDMT.prop";
            Properties tw = new Properties();
            tw.load(new FileInputStream(creds));
            this.setConsumerAccess(tw.getProperty("twapi"));
            this.setConsumerSecret(tw.getProperty("twapisecret"));
            this.setTwaccessToken(tw.getProperty("twacctoken"));
            this.setTwsecretToken(tw.getProperty("twaccsecret"));
            //System.out.println(" Credentials are here\n" + this.consumerAccess + "\n" + this.consumerSecret + "\n" + this.twaccessToken +"\n" + this.twsecretToken + "\n");
            return true;
        }
        catch (IOException e){
            e.getMessage();
            return false;
        }
    }

    //function fails if the same message is sent twice in a row. This is a Twitter restriction
    public boolean sendTweet(String status){
        try{
            tweets.updateStatus(status);
            return true;
        }
        catch(TwitterException e){
            e.getMessage();
            return false;
        }
    }


    public boolean getTimeline(){
        try{
            user = tweets.verifyCredentials().getScreenName();
            statuses = tweets.getUserTimeline(user);
            return true;
        }
        catch(TwitterException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTweets(){
        try{
            for(Status old: statuses){
                tweets.destroyStatus(old.getId());
            }
            return true;
        }
        catch(TwitterException | NullPointerException e){
            e.printStackTrace();
            System.out.println("Failed to delete status: " + e.getMessage());
            return false;
        }

    }

}