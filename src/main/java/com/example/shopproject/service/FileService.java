package com.example.shopproject.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {
    public String uploadFile(String uploadPath,String originalFileName,byte[] fileData) throws Exception{
        UUID uuid=UUID.randomUUID();//서로 다른 개체들을 구별하기 위해서 사용함
        String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
        String saveFileName=uuid.toString()+extension;
        String fileUploadFullName=uploadPath+"/"+saveFileName;
        FileOutputStream fos=new FileOutputStream(fileUploadFullName);//바이트 단위를 추출
        fos.write(fileData);
        fos.close();
        return saveFileName;
    }

    public void deleteFile(String filePath)throws Exception{
        File deleteFile=new File(filePath);

        if (deleteFile.exists()){
            deleteFile.delete();
            log.info("파일을 삭제하셨습니다");
        }else {
            log.info("해당 파일이 존재 하지 않습니다.");
        }
    }
}
