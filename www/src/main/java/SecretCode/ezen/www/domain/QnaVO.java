package SecretCode.ezen.www.domain;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QnaVO {


    private int bno;
    private String title;
    private String writer;
    private  String content;
    private String regDate;
    private int readCount;
    private int cmtQty;
    private String isSecret; //비밀글 여부
    private String scpwd; // 비밀글 비밀번호
    private String isNotice; // 비밀글 비밀번호
    private String nickName;



}
