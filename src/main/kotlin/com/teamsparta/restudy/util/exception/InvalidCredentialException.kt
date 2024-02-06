package com.teamsparta.restudy.util.exception

data class InvalidCredentialException(
    override val message: String? = "The credential is invalid"
): RuntimeException()


//class InvalidCredentailException(model: String):
//        RuntimeException("The credentail is invalid")

//이런 식으로 사용할 수도 있으나 data화 하는 이유는 좀 더 관리에 편하고
//        메소드를 자동생성 (gpt 참조) 해주기 때문?
//        위의 override 방식을 쓰면 이후 해당 메소드를 불러올 때
//        throw InvalidCredentialException() 까지만 한다면
//        The credential is invaild 문구가 반환되고, 괄호 안에 뭔가 써넣는다면
//        크레덴셜 이즈 인밸리드 대신 괄호 안의 문구가 반환됨.