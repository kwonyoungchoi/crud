package com.exam.crud.entity;



import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

//데이터베이스 테이블
//가능하면 소문자로 구성, _문자로 단어 분리X
//기본키 id(유사하게)
//나머지 필드는 항목이름으로 영문
//테이블명 작업이름
//다른 Entity에 추가를 위한 Entity


//ToString은 모든 변수를 전달
@Getter @Setter @ToString
//클래스생성자 BoardEntity(), BoardEntity(변수,........)
@AllArgsConstructor @NoArgsConstructor
//변수의 값을 getter와 setter를 이용하지 않고 변경
@Builder
//데이터베이스 테이블을 위한 클래스
@Entity
//자바17 jakarta, / 자바15 이하 javax
@Table(name="board")
//테이블 카운트에 정보를 저장할 테이블
//initialvalue = 시작값, allocationSize=증가값
@SequenceGenerator(
        name = "board_seq",
        sequenceName = "board_seq",
        initialValue = 1,
        allocationSize = 1
)
public class BoardEntity extends BaseEntity {

    //기본키, 유일한 키 - 중복X
    @Id
    //생성전략
    //문자변수일 때 GenerationType.IDNETITY - 중복되지 않도록 처리
    //숫자변수일 때 GenerationType.SEQUENCE - 숫자를 증가하면서 처리
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "board_seq")
    private long id;
    //게시판 제목, 내용, 작성자, (생성일, 수정일)-이미생성

    private String title;
    private String content;
    private String writer;
    
    //변수를 처리할 필요한 메소드를 작성
    //Entity<->DTO간의 변환하는 메소드
} //비즈니스 모델
