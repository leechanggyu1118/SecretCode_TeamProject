package SecretCode.ezen.www.repository;


import SecretCode.ezen.www.domain.ThemeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.ui.context.Theme;

import java.util.List;

@Mapper
public interface ThemeMapper {

    List<ThemeVO> getTheme();


    ThemeVO getThemeDetails(Long themeNum);

    List<ThemeVO> getReservThemes();

    List<ThemeVO> getThemes();


    ThemeVO findByUuid(String themeUuid);
}
