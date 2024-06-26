package SecretCode.ezen.www.service;

import SecretCode.ezen.www.domain.QnaVO;
import SecretCode.ezen.www.repository.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    @Override
    public QnaVO getAdminDetail(int bno) {
        return adminMapper.getAdminDetail(bno);
    }

    @Override
    public int deleteQna(int bno) {
        return adminMapper.deleteQna(bno);
    }
}
