package com.exam.crud.repository;

import com.exam.crud.dto.BoardDTO;
import com.exam.crud.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//JpaRepository(사용할 저장소(테이블))<사용할 Entity, 기본키 데이터형>
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    
    //내용을 사용자가 원하는대로 구성(삽입,수정,삭제,조회..기본)
    //.save()-삽입(기본키X),수정(기본키O)
    //DeleteBy(대상)변수명(첫글자대문자)
    //DeleteById()해당아이디를 찾아서 삭제
    //FindAll()모두검색
    //FindBy변수명-해당변수의 값에 해당하는 내용을 검색
    //FindById(번호)-해당번호를 찾아서 검색
    //FindBySubject(내용)-제목에서 찾아서 검색
    //FindByIdAndSubject(번호,내용)-번호와 내용이 일치하는 내용을 찾아서 검색
    //FindByIdOrSubject(번호,내용)-번호나 내용이 일치하는 내용을 찾아서 검색
    //JPA에 없는 내용은 사용자가 메소드로 만들어서 사용

    
} //데이터 저장소
