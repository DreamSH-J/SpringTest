package me.hackerj.Upload.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {
    private String uploadFileName;  // 업로드한 파일명
    private String storeFileName;  // 저장된 파일명 (uuid등을 이용해서 안 겹치게)
}
