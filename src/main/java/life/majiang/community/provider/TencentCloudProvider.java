package life.majiang.community.provider;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import life.majiang.community.configuration.TC_COS_Config;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class TencentCloudProvider {

    @Autowired
    private COSClient cosClient;

    @Autowired
    private TC_COS_Config tc_cos_config;

    private static final String[] IMAGE_TYPE = new String[]{".jpg", ".jpeg", ".gif", ".png", ".bmp", ".webp"};
    public String upload(MultipartFile uploadFile) {

        boolean isLegal = false;
        for(String type : IMAGE_TYPE){
            if(StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(),type)){
                isLegal = true;
                break;
            }
        }
        if(!isLegal){
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }

        // 文件新路径
        String fileName = uploadFile.getOriginalFilename();
        String filePath = getFilePath(fileName);

        try {
            String bucketName = tc_cos_config.getBucketName();
            String key = filePath;
            cosClient.putObject(bucketName,key,new ByteArrayInputStream(uploadFile.getBytes()),null);


            return tc_cos_config.getUrl()+"/"+key;
        } catch (Exception e){
            log.error("upload fail",e);
            return null;
        }
    }
    public boolean deletePic(String key){
        try {
            // 指定对象所在的存储桶
            String bucketName = this.tc_cos_config.getBucketName();
            cosClient.deleteObject(bucketName, key);
            return true;
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
            return false;
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
            return false;
        }
    }

    private String getFilePath(String sourceFileName) {
        DateTime dateTime = new DateTime();
        return "images/"
                + dateTime.toString("yyyy") + "/" + dateTime.toString("MM")
                + "/" + dateTime.toString("dd")
                + "/" + System.currentTimeMillis()
                + RandomUtils.nextInt(100, 9999) + "."
                + StringUtils.substringAfterLast(sourceFileName, ".");
    }


}
