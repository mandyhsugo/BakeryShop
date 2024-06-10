package cn.tedu.baking.controller;

import cn.tedu.baking.response.JsonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/v1/")
public class UploadController {
    @Value("${filePath}")
    private String dirPath;

    @PostMapping("upload")
    public JsonResult upload(MultipartFile file) throws IOException {
        //Get the name of the uploaded image   a.jpg
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        // get suffix.jpg
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //get unique file name,  UUID.randomUUID()get unique identifier
        fileName = UUID.randomUUID() + suffix;
        System.out.println(fileName);

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("/yyyy/MM/dd/");
        //Get date-related paths     /2023/7/12/
        String datePath = simpleDateFormat.format(new Date());
        //Create file object
        File dirFile = new File(dirPath + datePath);
        //Determine if the file does not exist, create it
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        //Save the image to the specified path and throw an exception
        file.transferTo(new File(dirPath + datePath + fileName));
        //After the upload is successful, respond to the client with the image path.  /2023/07/12/xxxx.jpg
        return JsonResult.ok(datePath + fileName);
    }

    @PostMapping("remove")
    public JsonResult remove(String url) {
        //Delete the file in the disk corresponding to the path
        new File(dirPath + url).delete();
        return JsonResult.ok();
    }


}
