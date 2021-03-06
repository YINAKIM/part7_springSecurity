--파일업로드 SQL
SELECT * FROM TBL_ATTACH where bno = 426016;
SELECT * FROM TBL_BOARD where bno = 426016;

/*
-- 426015 번 글,
426015,첨부 후 수정시 삭제만테스트,첨부 후 수정시 삭제만테스트,삭제만,2021-05-29 10:20:00,2021-05-29 10:20:00,0

--최초 첨부목록
c9799c6b-1d6f-4cbe-88a2-4fc581806ffb,2021/05/29,iiiii_test.png,1,426015
2ce8abeb-64bc-4e3e-8ebe-1b5c9daecba6,2021/05/29,토비의 스프링_초안6.docx,0,426015
*/



/*
bno = 426016

1. 최초등록시 첨부파일 목록 조회
89b8d875-bdb6-4355-880d-7fc6e2372a7d,2021/05/29,토비의 스프링_초안4.docx,0,426016
5743f722-1f27-488a-b71f-900e3e818b51,2021/05/29,토비의 스프링_초안5.docx,0,426016
92bd29b6-9d53-4ad3-82df-f6aa1f92cd37,2021/05/29,iiiii_test.png,1,426016

2. modify하면서 이미지파일 제외하고 일반파일만 삭제 후 조회
92bd29b6-9d53-4ad3-82df-f6aa1f92cd37,2021/05/29,iiiii_test.png,1,426016
--> DB에서 파일목록데이터 삭제 완료

3. 새로운 파일 추가 테스트
92bd29b6-9d53-4ad3-82df-f6aa1f92cd37,2021/05/29,iiiii_test.png,1,426016
201fa4c1-6f72-4878-b2c9-8042514c1cba,2021/05/29,summery.xlsx,0,426016
---> 물리파일은 삭제 안된상태, DB에서 데이터만 수정되는 상태까지 (p.592)

*/

SELECT *
FROM TBL_ATTACH
WHERE UPLOADPATH = TO_CHAR(SYSDATE-1, 'yyyy\mm\dd');
