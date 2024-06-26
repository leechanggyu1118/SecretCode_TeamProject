package SecretCode.ezen.www.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class adRegisterVO {

    private int themeNum;       // 테마 번호
    private String themeName;   // 테마 이름
    private String difficulty;  // 난이도
    private String genre;       // 장르
    private String recNumPeople; // 추천인원
    private String playTime;    // 플레이 시간
    private String description; // 설명
    private String uuid;        // 사진




}
