package com.exam.crud.repository;

import com.exam.crud.dto.BoardDTO;
import com.exam.crud.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    //사용자 질의어
    //@Query를 이용해서 데이터베이스 질의어를 작성 방법2가지
    //1. entity에 변수명으로 질의어 작성
    //  -@Query(Select 별칭 FROM 엔티티 별칭(한글자) WHERE 별칭.변수 조건식 : 값)
    //2. 테이블의 필드명으로 질의어를 작성(네이티브)
    //%내용, - 뒤에 내용이 일치하면 / 내용% - 앞에서 일치하면 / %내용% - 포함되면

//    @Query("SELECT u FROM BoardEntity u WHERE u.title like %:search%")
//    //@Query(value = "SELECT * FROM BoardEntity * WHERE title like %:search%", nativeQuery = ture) - mybatis
//    Page<BoardEntity> SubjectSearch(String search, Pageable pageable);
//
//    @Query("SELECT u FROM BoardEntity u WHERE u.content like %:search%")
//    //@Param() = 서비스에서 search로 보낸 값을 search에 저장한다.
//    Page<BoardEntity> ContentSearch(@Param("search") String search, Pageable pageable);
//
//    @Query("SELECT u FROM BoardEntity u WHERE u.writer like %:search%")
//    Page<BoardEntity> WriterSearch(String search, Pageable pageable);
//
//    @Query("SELECT u FROM BoardEntity u WHERE u.title like %:search% or u.content like %:search%")
//    Page<BoardEntity> SubConSearch(String search, Pageable pageable);
//
//    // or ~중에서 title에 있거나, content에 있거나, writer에 있으면검색
//    // and ~모두 title에 있고, content에 있고, writer에 있으면검색
//    @Query("SELECT u FROM BoardEntity u WHERE u.title like %:search% or u.content like %:search% or u.writer like %:search%")
//    Page<BoardEntity> AllSearch(String search, Pageable pageable);


    Page<BoardEntity> findByTitleContaining(String search, Pageable pageable);
    Page<BoardEntity> findByContentContaining(String search, Pageable pageable);
    Page<BoardEntity> findByWriterContaining(String search, Pageable pageable);
    Page<BoardEntity> findByTitleContainingOrContentContaining(String search, Pageable pageable);
    Page<BoardEntity> findByTitleContainingOrContentContainingOrWriterContaining(String search, Pageable pageable);

    
} //데이터 저장소
