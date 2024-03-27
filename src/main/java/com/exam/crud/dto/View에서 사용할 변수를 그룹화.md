<input name="sample" required> 필수입력항목-검증X
*아이디 :
View---> 전달받은 값(DTO)을 검증 ----> Service

DTO에서 검증, Controller 검증확인, View 검증결과 출력


검증(Validation)
;유효성검사
@NotNull : null이 아닌지 검사
@NotBlank : 문자열이 null 또는 길이가 0인지 검사
@NotEmpty : 문자열이 null 또는 길이가 0, 비어있는지 검사
@Size : 문자열의 길이를 검사
@Min, @Max : 숫자의 최소값과 최대값 검사
@Pattern : 정규 표현식을 사용하여 특정 패턴에 맞는지 검사
@Email : 이메일주소 형식인지 검사