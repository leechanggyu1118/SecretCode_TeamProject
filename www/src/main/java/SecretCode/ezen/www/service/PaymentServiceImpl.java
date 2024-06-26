package SecretCode.ezen.www.service;

import SecretCode.ezen.www.domain.PagingVO;
import SecretCode.ezen.www.domain.ReservationVO;
import SecretCode.ezen.www.repository.PaymentMapper;
import com.siot.IamportRestClient.IamportClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService{
    private final PaymentMapper paymentMapper;

    private IamportClient api;

    @Override
    public int reservationInsert(ReservationVO rvo) {
        return paymentMapper.reservationInsert(rvo);
    }

    @Override
    public ReservationVO getImRes(String email, String merchantUid) {
        return paymentMapper.getImRes(email, merchantUid);
    }

    @Override
    public List<ReservationVO> getmyReservation(String email) {

        return paymentMapper.getmyReservation(email);
    }

    @Override
    public List<ReservationVO> getPayList(PagingVO pgvo) {
        return paymentMapper.getPayList(pgvo);
    }

    @Override
    public int deleteReservation(int reservationNum) {
        return paymentMapper.deleteReservation(reservationNum);
    }

    @Override
    public ReservationVO getReservation(String merchantUid) {
       return paymentMapper.getReservation(merchantUid);
    }

    @Override
    public List<ReservationVO> getAllReservations() {
        // 예약 정보를 가져오는 비즈니스 로직을 구현
        List<ReservationVO> reservations = paymentMapper.findAll();
        // Entity를 VO로 변환하는 등의 추가 로직이 있을 수 있음
        List<ReservationVO> reservationVOs = convertToReservationVOList(reservations);
        return reservationVOs;
    }

    private List<ReservationVO> convertToReservationVOList(List<ReservationVO> reservations) {
        // 예약 Entity를 VO로 변환하는 메서드 구현
        List<ReservationVO> reservationVOs = new ArrayList<>();
        for (ReservationVO reservation : reservations) {
            ReservationVO reservationVO = new ReservationVO();
            // 예약 정보 Entity에서 필요한 정보를 VO에 복사하는 로직 추가
            reservationVO.setReservationDate(reservation.getReservationDate());
            reservationVO.setThemeName(reservation.getThemeName());
            reservationVO.setReservationTime(reservation.getReservationTime());
            // 필요한 경우 더 많은 정보 설정
            reservationVOs.add(reservationVO);
        }
        return reservationVOs;
    }

    @Override
    public List<ReservationVO> getMemberReservation() {
        return paymentMapper.getMemberReservation();
    }




}

