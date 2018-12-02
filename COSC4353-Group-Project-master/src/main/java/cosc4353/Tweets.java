package cosc4353;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**3
 Class used to tweet during a session of our game

  Dan Martinez
 **/

public class Tweets {

    private String creds = "secrets_DDMT.prop";
    private String consumerAccess;
    private String consumerSecret;
    private String twaccessToken;
    private String twsecretToken;
    private Twitter tweets;


    public void setConsumerAccess(String consumerAccess) {
        this.consumerAccess = consumerAccess;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public void setTwaccessToken(String twaccessToken) {
        this.twaccessToken = twaccessToken;
    }

    public void setTwsecretToken(String twsecretToken) {
        this.twsecretToken = twsecretToken;
    }

    //
    public Tweets(){
        this.loadAccess();
        //ConfigurationBuilder cb = new ConfigurationBuilder();
        //cb.setDebugEnabled(true).setOAuthConsumerKey(consumerAccess).setOAuthConsumerSecret(consumerSecret).setOAuthAccessToken(twaccessToken).setOAuthAccessTokenSecret(twsecretToken);
        tweets = new TwitterFactory().getInstance();
        tweets.setOAuthConsumer(consumerAccess,consumerSecret);
        AccessToken accessToken = new AccessToken(twaccessToken, twsecretToken);
        //System.out.println(accessToken.getScreenName());
        tweets.setOAuthAccessToken(accessToken);

    }

    private Boolean loadAccess(){
        try{
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

//TODO create String template to pass in function call after each player takes a turn.
    //function fails if the same message is sent twice in a row. This is a Twitter restriction
    public boolean sendTweet(String status){
        try{
            tweets.updateStatus(status);
            return true;
        }
        catch(TwitterException e){
            e.printStackTrace();
            return false;
        }
    }
/*
    public boolean getTimeline(){

    }

    public boolean deleteTweets(){

    }
*/
    //secondary method to build Twitter instance to access our account.
    public ConfigurationBuilder config(){
        this.loadAccess();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(consumerAccess);
        cb.setOAuthConsumerSecret(consumerSecret);
        cb.setOAuthAccessToken(twaccessToken);
        cb.setOAuthAccessTokenSecret(twsecretToken);
        System.out.println(" Credentials are here\n" + this.consumerAccess + "\n" + this.consumerSecret + "\n" + this.twaccessToken +"\n" + this.twsecretToken + "\n");
        return cb;
    }

}