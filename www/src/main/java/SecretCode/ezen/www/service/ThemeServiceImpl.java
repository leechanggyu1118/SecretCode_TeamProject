package SecretCode.ezen.www.service;


import SecretCode.ezen.www.domain.PagingVO;
import SecretCode.ezen.www.domain.ReservationVO;
import SecretCode.ezen.www.domain.ThemeVO;
import SecretCode.ezen.www.repository.PaymentMapper;
import SecretCode.ezen.www.repository.ThemeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    private final ThemeMapper themeMapper;
    private final PaymentMapper paymentMapper;
    private PagingVO pgvo;


    @Override
    public List<ThemeVO> getAllThemes() {
        return themeMapper.getTheme();
    }

    @Override
    public ThemeVO getThemeDetails(Long themeNum) {
        return themeMapper.getThemeDetails(themeNum);
    }

    @Override
    public List<ThemeVO> getThemeList() {
        return themeMapper.getReservThemes();
    }

    @Override
    public List<ThemeVO> getThemes() {
        return themeMapper.getThemes();
    }

    @Override
    public ThemeVO getThemeDetailsByUuid(String themeUuid) {
        return themeMapper.findByUuid(themeUuid);
    }

    @Override
    public List<ReservationVO> getAllReservations() {
        return paymentMapper.getMemberReservation();
    }


}
