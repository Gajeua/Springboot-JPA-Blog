let index = {
	// let _this = this; // function(){} 사용하려면 this를 바인딩 해줘야 한다.
	init: function() {
		$("#btn-save").on("click", ()=> // function(){}대신 ()=>{}를 사용하는 이유는 this를 바인딩 하기 위해서!
			this.save()
		);
	},
		

	save: function() {
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		
		$.ajax({
			type : "POST",
			url : "/api/board",
			data : JSON.stringify(data), // http body 데이터
			contentType : "application/json; charset=utf-8",  //MIME 타입
			dataType : "json" // 요청을 서버로 해서 응답이 왔을때  기본적으론 문자열이 응답 => 생긴게 JSON이라면 javascript 오브젝트로 변경.
		}).done(function(resp){ //  javascript 오브젝트로 변경된 응답 값이 파라미터에 저장
			alert("글쓰기 완료.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});  
		
	},
	

}

index.init();