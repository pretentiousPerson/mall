package com.baijia.mall.service.impl;

import com.google.common.collect.Lists;
import com.baijia.mall.service.IFileService;
import com.baijia.mall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by geely
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);


    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();
        //��չ��
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("��ʼ�ϴ��ļ�,�ϴ��ļ����ļ���:{},�ϴ���·��:{},���ļ���:{}",fileName,path,uploadFileName);
        System.out.println("path = "+path);
        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);


        try {
            file.transferTo(targetFile);
            //�ļ��Ѿ��ϴ��ɹ���


            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //�Ѿ��ϴ���ftp��������

            targetFile.delete();
        } catch (IOException e) {
            logger.error("�ϴ��ļ��쳣",e);
            return null;
        }
        //A:abc.jpg
        //B:abc.jpg
        return targetFile.getName();
    }

}
