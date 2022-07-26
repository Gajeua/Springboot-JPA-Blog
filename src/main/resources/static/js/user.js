let index = {
	// let _this = this; // function(){} 사용하려면 this를 바인딩 해줘야 한다.
	init: function() {
		$("#btn-save").on("click", ()=> // function(){}대신 ()=>{}를 사용하는 이유는 this를 바인딩 하기 위해서!
			this.save()
		);
	},
		

	save: function() {
		//alert('user의 save함수 호출됨');
		// 데이터를 ID값으로 가져와서 객체화
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		// console.log(data);
		
		// ajax 호출시 default.가 비동기 호출
		// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환
		$.ajax({
			type : "POST",
			url : "/auth/joinProc",
			data : JSON.stringify(data), // http body 데이터
			contentType : "application/json; charset=utf-8",  //MIME 타입
			dataType : "json" // 요청을 서버로 해서 응답이 왔을때  기본적으론 문자열이 응답 => 생긴게 JSON이라면 javascript 오브젝트로 변경.
		}).done(function(resp){ //  javascript 오브젝트로 변경된 응답 값이 파라미터에 저장
			alert("회원가입 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});  // ajax 통신을 이용해서 3개의 파라미터를 JSON으로 변경하여 insert 요청
		
	},
	

}

index.init();