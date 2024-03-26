package com.exam.crud.dto;



import com.exam.crud.entity.BaseEntity;
import lombok.*;


@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder
public class BoardInsertDTO extends BaseEntity {


    private long id;
    //게시판 제목, 내용, 작성자, (생성일, 수정일)-이미생성
    private String title;
    private String content;
    private String writer;
    //변수를 처리할 필요한 메소드를 작성
    //Entity<->DTO간의 변환하는 메소드



}
