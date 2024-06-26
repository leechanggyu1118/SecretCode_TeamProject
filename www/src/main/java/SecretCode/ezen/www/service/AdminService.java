package SecretCode.ezen.www.service;

import SecretCode.ezen.www.domain.QnaVO;

public interface AdminService {

    QnaVO getAdminDetail(int bno);

    int deleteQna(int bno);
}
