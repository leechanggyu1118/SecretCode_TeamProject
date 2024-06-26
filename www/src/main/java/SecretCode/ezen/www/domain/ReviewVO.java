package SecretCode.ezen.www.domain;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewVO {

    private int bno;
    private int rno;
    private String title;
    private String writer;
    private  String content;
    private String regDate;
    private int readCount; //좋아요 수
    private String theme; //선택한 테마명
    private String uuid; //선택한 테마명
    private String saveDir;
    private String fileName;
    private String themeName;
    private int  rating;



}
