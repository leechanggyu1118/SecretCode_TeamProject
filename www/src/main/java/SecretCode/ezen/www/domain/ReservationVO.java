package SecretCode.ezen.www.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationVO {

    public int reservationNum;
    public String merchantUid; // 결제 한 후 완료코드
    public String reservationDate;
    public String reservationTime;
    public String themeName;
    public String email;
    public String name;
    public String phone;
    public int reservationPeople;
    public int reservationPrice;
    public String reservationPaydate;







}
