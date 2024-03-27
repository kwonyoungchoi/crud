package com.exam.crud.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter @ToString
//이벤트 발생시 처리(날짜/시간)
@EntityListeners(AuditingEntityListener.class)
//abstract를 직접적으로 사용불가능 , extends를 이용해야만 사용가능
public abstract class BaseEntity {

    //자동으로 등록처리시 날짜처리 메소드를 생성해서 결과값을 주입
    @CreatedDate
    private LocalDateTime regDate; //생성시 등록할 날짜/시간

    @LastModifiedDate
    private LocalDateTime modDate; //수정시 등록할 날짜/시간

    //클래스내의 변수는 클래스내에서만 사용(private)
    //클래스내의 변수는 외부에서 사용하기위해서 메소드이용
    //외부->변수 넣을 때는 set변수명()
    //변수->외부 보낼 때는 get변수명()


}
