package life.majiang.community.configuration;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.qcloud.cos.region.Region;

@Configuration
@Data
public class TC_COS_Config {
    @Value("${tencent.cloud.secret-id}")
    private String secretId;
    @Value("${tencent.clout.secret-key}")
    private String secretKey;

    @Value("${tencent.cloud.bucket-name}")
    private String bucketName;

    @Value("${tencent.cloud.region}")
    private String region;

    @Value("${tencent.cloud.url}")
    private String url;

    @Bean
    public COSClient cosClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(this.region);
        ClientConfig clientConfig=new ClientConfig(region);
        COSClient cosClient=new COSClient(cred,clientConfig);
        return cosClient;
    }
}
