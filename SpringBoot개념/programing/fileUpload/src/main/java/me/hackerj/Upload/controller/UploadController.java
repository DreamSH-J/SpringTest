package me.hackerj.Upload.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/upload")
public class UploadController {
    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/file1")
    public String newFile() {
        return "upload_form";
    }

    //@PostMapping("/file1")
    //DispatcherServlet에서 MultipartResolber를 실행하여 request를 아래와 같이 받게 해준다.
    public String saveFileV1(MultipartHttpServletRequest mRequest,
                             HttpServletRequest request) throws IOException {
        log.info("1. request = {}", request);
        log.info("2. mRequest = {}", mRequest);
        log.info("3. itemName = {}", mRequest.getParameter("itemName"));
        Iterator<String> params = mRequest.getFileNames(); //파라미터 file을 담고 있는 Iterator
        String param = params.next();
        log.info("4. 파라미터 이름 = {}", param);
        MultipartFile mfile = mRequest.getFile(param);
        String filename = mfile.getOriginalFilename(); //첨부를 안하면 빈 스트링
        log.info(filename.equals("") ? ". 첨부 안함" : "5. 첨부파일:" + filename);
        if (!filename.equals("")) {
            //첨부함
            String fullPath = fileDir + filename;
            if (new File(fullPath).exists()) {
                //첨부파일과 같은 이름의 파일이 존재 : 현재 밀리세컨 + 파일 이름
                fullPath = fileDir + System.currentTimeMillis() + filename;
            }//중복된 파일명 변경
            mfile.transferTo(new File(fullPath));
            log.info("6. 파일 저장 fullpath={}", fullPath);
        }
        return "upload_form";
    }

    @PostMapping("/file1")
    public String saveFileV1(@RequestParam String itemName,
                             @RequestParam MultipartFile file,
                             //MultipartHttpServletRequest mRequest,
                             HttpServletRequest mRequest) throws IOException {
        log.info("1. itemName = {}", itemName);
        log.info("2. mFile ={}", file);
        String filename = file.getOriginalFilename(); //첨부를 안하면 빈 스트링
        log.info(filename, equals("") ? "3. 첨부안함" : "3.첨부파일:" + filename);
        if (!file.isEmpty()) {
            //첨부함
            String fullPath = fileDir + filename;
            if (new File(fullPath).exists()) {
                //첨부파일과 같은 이름의 파일이 존재: uuid +파일 이름
                fullPath = fileDir + UUID.randomUUID().toString() + filename;
            }//중복된 파일명 변경
            file.transferTo(new File(fullPath));
            log.info("5. 파일 저장 fullPath={}", fullPath);
        }
        return "upload_form";
    }

    @GetMapping("/file2")
    public String newFile2() {return "upload_form2";}

    //PostMapping("/file2")
    public String saveFile(@RequestParam String itemName,
                           MultipartRequest mRequest) throws IOException {
        Iterator<String> params= mRequest.getFileNames(); //file1, file2
        int i = 1;
        while (params.hasNext()){
            String param = params.next();
            log.info("= = = {}번째 파라미터 이름 = = = ", i, param);
            MultipartFile mfile = mRequest.getFile(param);//파라미터의 파일 객체
            String filename = mfile.getOriginalFilename();//업로드한 파일명
            log.info(filename.equals("")? "첨부 안해서 빈스트링": "첨부한 파일 이름은 " + filename);
            //첨부 여부
            String fullPath = fileDir + filename;
            if(filename!=null && !filename.equals("")){
                //첨부함
                if(new File(fullPath).exists()){
                    //첨부파일과 같은 이름의 파일이 서버에 존재: 현재 밀리세컨 + 파일 이름
                    fullPath = fileDir + System.currentTimeMillis() + filename;
                }//중복된 파일명 변경
                mfile.transferTo(new File(fullPath));//서버에 파일 저장
                log.info("서버에 저장된 파일 : " + fullPath);
            } //if
            i++;
        }//while
        return "upload_form2";
    }

    @PostMapping("/file2")
    public String saveFile(@RequestParam String itemName,
                           @RequestParam MultipartFile file1,
                           @RequestParam MultipartFile file2) throws IOException {
        if(!file1.isEmpty()){
            String fullPath1 = fileDir + file1.getOriginalFilename();
            log.info("1. 첨부한 파일에 따라 저장할 예정 ={}", fullPath1);
            if(new File(fullPath1).exists()){
                fullPath1 = fileDir + UUID.randomUUID().toString()+file1.getOriginalFilename();
            }
            file1.transferTo(new File(fullPath1));
            log.info("1. 실제 저장한 파일 fullPath={}", fullPath1);
        }else{
            log.info("1. 파일 첨부 안함");
        }
        if(!file2.isEmpty()){
            String fullPath2 = fileDir + file2.getOriginalFilename();
            log.info("2. 첨부한 파일에 따라 저장할 예정 = {}", fullPath2);
            if(new File(fullPath2).exists()){
                fullPath2 = fileDir + UUID.randomUUID().toString() +file2.getOriginalFilename();
            }
            file2.transferTo(new File(fullPath2));
            log.info("2.실제 저장한 파일 fullPath={}", fullPath2);
        }else{
            log.info("2.파일 첨부 안 함");
        }
        return "upload_form2";
    }



}
