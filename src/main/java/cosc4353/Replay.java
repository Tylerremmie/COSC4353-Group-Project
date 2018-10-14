package cosc4353;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;


/*
   Skeleton class for replay storage/retrieval in S3
   S3 is fully setup. Needs implementation on this part of the code

   Dan M.

 */

public class Replay {

    private static AWSCredentials awsCredentials = new BasicAWSCredentials("aaa","bbb");  //placeholder keys. do not want to expose actual IAM keys. will change soon
    private static AmazonS3 s3 = new AmazonS3Client(awsCredentials); //this will fail with current credentials


}
