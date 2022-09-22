package com.vecondev.buildoptima.mailsender;

import static com.vecondev.buildoptima.mailsender.S3ConfigProperties.ACCESS;
import static com.vecondev.buildoptima.mailsender.S3ConfigProperties.BUCKET;
import static com.vecondev.buildoptima.mailsender.S3ConfigProperties.SECRET;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;

public class AmazonS3Service {

  private AmazonS3Service() {}

  private static final AmazonS3 amazonS3 =
      AmazonS3ClientBuilder.standard()
          .withCredentials(
              new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS, SECRET)))
          .withRegion(Regions.US_EAST_1)
          .build();

  public static S3Object getObject(String objectKey) {
    return amazonS3.getObject(BUCKET, objectKey);
  }
}
