package SecretCode.ezen.www.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import SecretCode.ezen.www.domain.FileVO;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component
public class FileHandler {

     private final String UP_DIR="C:\\_secretFileUpload";
//    private final String UP_DIR = "/Users/kimnoa/Desktop/noa/_myProject/_java/_fileupload/";

    public List<FileVO> uploadFiles(MultipartFile[] files) {
        List<FileVO> flist = new ArrayList<FileVO>();

        LocalDate date = LocalDate.now();
        String today = date.toString();
        log.info(" >>> today >> {}", today);

        today = today.replace("-", File.separator);
        File folders = new File(UP_DIR, today);

        if (!folders.exists()) {
            folders.mkdirs();
        }

        // 리스트 설정
        for (MultipartFile file : files) {
            FileVO fvo = new FileVO();
            fvo.setSaveDir(today);
            fvo.setFileSize(file.getSize()); // return long

            // getOriginalFilename() : 경로+파일명. 파일 경로를 포함하는 케이스도 있음.
            String originalFileName = file.getOriginalFilename();
            String onlyFileName = originalFileName.substring(
                    originalFileName.lastIndexOf(File.separator) + 1); // 실 파일명만 추출
            fvo.setFileName(onlyFileName);

            // UUID 생성
            UUID uuid = UUID.randomUUID();
            String uuidStr = uuid.toString();
            fvo.setUuid(uuidStr);

            // 디스크에 저장할 파일 객체 생성 -> 저장
            String fullFileName = uuidStr + "_" + onlyFileName;
            File storeFile = new File(folders, fullFileName);

            // 저장 => 저장 경로 또는 파일 없다면 IOException 발생
            try {
                file.transferTo(storeFile); // 저장

                // 파일 타입을 결정 => 이미지만 썸네일 저장
                if (isImageFile(storeFile)) {
                    fvo.setFileType(1);

                    // 썸네일 생성
                    File thumbNail = new File(folders, uuidStr + "_th_" + onlyFileName);
                    Thumbnails.of(storeFile).size(75, 75).toFile(thumbNail);
                }

            } catch (Exception e) {
                log.info(">>> file 저장 에러");
                e.printStackTrace();
            }

            // list에 fvo 추가
            flist.add(fvo);
        }
        return flist;
    }

    // tika를 활용한 파일 형식 체크 -> 이미지 파일이 맞는지 확인
    public boolean isImageFile(File storeFile) throws IOException {
        String mimeType = new Tika().detect(storeFile); // image/png  image/jpg
        return mimeType.startsWith("image");
    }

}
