package com.exam.crud.dto;



import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;



@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder
//Entity(1) <-> DTO(여러개)
//하나의 Entity를 사용용도별로 DTO를 나누어서 작업
//검증처리(View에서 입력내용이 정상적인 값인지 판별)
public class BoardDTO {

    private long id;
    //게시판 제목, 내용, 작성자, (생성일, 수정일)-이미생성
    private String title;
    private String content;
    private String writer;
    private LocalDateTime modDate;
    //Entity에 없는 변수 선언 가능(주문관리->금액처리)

    //변수를 처리할 필요한 메소드를 작성
    //Entity<->DTO간의 변환하는 메소드



}
