package com.example.demo.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class CloudinaryService {

    private static final String TEMP_FILE_NAME = "temp-file";
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File imgFile = File.createTempFile(TEMP_FILE_NAME, multipartFile.getOriginalFilename());
        multipartFile.transferTo(imgFile);
        String url = this.cloudinary.
                uploader().
                upload(imgFile, new HashMap<>()).
                get("url").toString();
        //delete temporary file
        imgFile.delete();
        return url;
    }
}
