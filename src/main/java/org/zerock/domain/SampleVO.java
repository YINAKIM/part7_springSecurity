package org.zerock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 모든 속성을 다 사용하는 생성자
@NoArgsConstructor  // 기본생성자
public class SampleVO {
    private Integer mno;
    private String firstName;
    private String lastName;
}
