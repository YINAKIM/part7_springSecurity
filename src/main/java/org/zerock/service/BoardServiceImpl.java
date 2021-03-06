package org.zerock.service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

import java.util.List;


//어노테이션은 패키지를 읽어들이는 시점에 처리됨

@Service // 계층 구조상 주로 비즈니스 영역을 담당하는 객체임을 표시
@Log4j
@AllArgsConstructor //  모든 파라미터를 이용하는 생성자를 만드는 어노테이션 : 실제 코드는 BoardMapper를 주입받는 생성자가 만들어짐
public class BoardServiceImpl implements BoardService{

    // @Autowired -> 이것도 OK, 근데 Lombok사용할 때는 @Setter를 권장
    @Setter(onMethod_ = @Autowired)
    private BoardMapper mapper;

    @Setter(onMethod_=@Autowired)
    private BoardAttachMapper attachMapper; //첨부파일등록용

    @Transactional
    @Override
    public void register(BoardVO board) {
        log.info("register.........."+board);
        mapper.insertSelectKey(board);
        //BoardService는 void register , insertSelectKey는 int 리턴
        // => OK, 필요하다면 예외처리나 int리턴으로 사용가능 : 실행 후 생성된 게시물 벊를 확인할 수 있음

    //--첨부파일 등록 1. null체크
        if( board.getAttachList() == null || board.getAttachList().size() <= 0 ){
            return; //첨부파일 없으며면 바로 종료
        }

    //--첨부파일 등록 2. 등록처리
        board.getAttachList().forEach(attach -> {
            attach.setBno(board.getBno());
            attachMapper.insert(attach);
        });

/*  @Transactional 처리해둠 : 게시물등록 > 첨부파일 각각등록

    [1] TBL_BOARD(BoardMapper.xml)의 insertSelectKey로 "먼저 게시물 등록"
    [2] 첨부파일List 없으면 핸들러 종료
        첨부파일List 있으면 게시물 bno 받아다가 각각 setBno
    [3] TBL_ATTACH(BoardAttachMapper.xml)의 insert로 첨부파일 "각각" 등록(forEach)
*/


    }

    @Override
    public BoardVO get(Long bno) {
        log.info("get.........."+bno);
        return mapper.read(bno);
    }

    @Transactional
    @Override
    public boolean modify(BoardVO board) {
        log.info("modify......"+board);
    /*** 첨부파일 수정처리 : 기존첨부파일 관련 데이터 전부 삭제 > 다시 첨부파일 데이터 추가 ---> @Transactional 처리 ***/
        attachMapper.deleteAll(board.getBno());

        log.info( "수정할 파일목록은?? "+board.getAttachList() );
        boolean modifyResult = mapper.update(board) == 1;
        if( modifyResult && board.getAttachList() != null
                             && board.getAttachList().size() > 0){

            log.info( "if들어옴------------첨부파일이 있다는 뜻"  );
            log.info( "첨부파일 있고, 수정할 파일목록은?? "+board.getAttachList() );
            board.getAttachList().forEach(attach -> {
                attach.setBno(board.getBno());
                attachMapper.insert(attach);
            });
        }
    /*** 첨부파일 수정처리 : 기존첨부파일 관련 데이터 전부 삭제 > 다시 첨부파일 데이터 추가 ---> @Transactional 처리 ***/

        log.info(modifyResult);
        return modifyResult; //첨부파일 삭제 후 > 게시글 전체 정보(새로운 파일목록포함) > update처리까지 된 modifyResult
    }

    @Transactional
    @Override
    public boolean remove(Long bno) {
        log.info("여기서 deleteAll이랑 delete랑 같이함 remove......"+bno);

        //게시글삭제 시 첨부파일 같이 삭제 : @Transactional처리
        // 삭제요청 > 첨부파일 먼저 삭제 > 게시글 삭제
        attachMapper.deleteAll(bno);

        return mapper.delete(bno) == 1;       //  삭제/수정은 void로 설계할 수 도 있지만 성공여부를 정확히 처리하기 위해 boolean으로

    }

    @Override
    public List<BoardVO> getList(Criteria cri) {
        log.info("#########getList with criteria : "+ cri);
        return mapper.getListWithPaging(cri);
    }

    @Override
    public int getTotal(Criteria cri) {
        log.info(".......getTotal_ROW");
        return mapper.getTotal(cri);
    }

    @Override
    public List<BoardAttachVO> getAttachList(Long bno) {
        log.info("첨부파일 getAttachList by bno");
        return attachMapper.findByBno(bno);
    }

/*
페이징처리안된 CRUD테스트용 getList

    @Override
    public List<BoardVO> getList() {
        log.info("getList.............");
        return mapper.getList();
    }
*/



}
