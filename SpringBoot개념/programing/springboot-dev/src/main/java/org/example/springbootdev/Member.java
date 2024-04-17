package org.example.springbootdev;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Member {
// 오라클 기준    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "시퀀스이름")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;  //DB 테이블의 'id' 컬럼과 매칭

    @Column(name= "name", nullable = false)
    private String name; //DB 테이블의 'name' 컬럼과 매칭

}
