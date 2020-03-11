package life.majiang.community.controller;


import life.majiang.community.dto.FileDTO;
import life.majiang.community.provider.TencentCloudProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class FileController {

    @Autowired
    private TencentCloudProvider tencentCloudProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
        MultipartFile file=multipartHttpServletRequest.getFile("editormd-image-file");
        System.out.println("ready to upload");
        try{
            String fileName = tencentCloudProvider.upload(file);
            FileDTO fileDTO=new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(fileName);
            System.out.println(fileName);
            return fileDTO;
        }catch (Exception e){
            log.error("upload error",e);
            FileDTO fileDTO=new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败");
            return fileDTO;
        }

    }
}
