package cosc4353;

public class Twitter {

    private String creds = "secrets_DDMT.prop";
    private String apiAccess;
    private String apiSecret;
    private String accessToken;
    private String secretToken;

    public void setApiAccess(String apiAccess) {
        this.apiAccess = apiAccess;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }
}
