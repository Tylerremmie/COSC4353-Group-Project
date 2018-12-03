package cosc4353;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;


/*
   Class used to upload and/or download replays from S3 storage.

   Replay file naming convention Month-day-Hour-second-replay.ser
   Dan M.

 */

public class  Replay {


    private String s3bucket = "risk-replays";
    private String s3access = "XXXX";
    private String s3Secret = "XXXX";
    private String fileUpload = System.getProperty("user.dir") + "/src/main/resources/replays/examplereplay.ser";
    private String fileDownload = System.getProperty("user.dir") + "/src/main/resources/replays/testdownload.ser";
    private AmazonS3 S3;
    //file naming convention currently not cross platform.

    public Replay(){
        this.loadCreds();
        this.buildClient();

    }
    private void setS3access(String s3access) {
        this.s3access = s3access;
    }

    private void setS3Secret(String s3Secret) {
        this.s3Secret = s3Secret;
    }

    //Loads the locally stored S3 access keys to a Replay object to upload/download
    private boolean loadCreds(){
        final String credsFile = "secrets_DDMT.prop";

        Properties s3creds = new Properties();
        try{
            FileInputStream in = new FileInputStream(credsFile);
            s3creds.load(in);
            this.setS3access(s3creds.getProperty("s3access"));
            this.setS3Secret(s3creds.getProperty("s3secret"));
            // System.out.println("Loaded credentials. here is access and here is secret " + this.s3access + this.s3Secret);
            return true;
        }

        catch(IOException error){
            System.out.println(error.getMessage());
            return false;
        }

    }

    //creates an s3 client that the download/upload methods can use.
    private boolean buildClient(){
        try{
            final String s3Region = "us-east-1";
            BasicAWSCredentials creds = new BasicAWSCredentials(this.s3access, this.s3Secret);
            S3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(s3Region).build();
            return true;
        }
        catch(AmazonClientException e){
            e.printStackTrace();
            return false;
        }
    }

/*
meant to receive a serialized stack of type TurnManager
 */
    public boolean uploadReplay(){
        try {
            String fileKey = new SimpleDateFormat("MMddHHmmss").format(new Date()) + "replay.ser"; //unique filename everytime the method is called
            S3.putObject(new PutObjectRequest(s3bucket, fileKey, new File(fileUpload)));
            System.out.println(fileKey + " uploaded successfully!");
            return true;
        }
        catch(AmazonClientException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean downloadReplay(){
        try{
            ObjectListing availableReplays = S3.listObjects(new ListObjectsRequest().withBucketName(s3bucket));
            List<S3ObjectSummary> replayList = availableReplays.getObjectSummaries();
            //shows all available replays
            for(S3ObjectSummary replay : replayList) {
                System.out.println(replay.getKey());
            }
            //set the downloadkey to the index of replay we want to download and store it. In this case we just grab the first available.
            String downloadkey = replayList.get(0).getKey();
            S3.getObject(new GetObjectRequest(s3bucket, downloadkey), new File(fileDownload));
            return true;

        }
        catch (AmazonClientException e){
            e.printStackTrace();
            return false;
        }

    }

}