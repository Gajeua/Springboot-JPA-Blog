let index = {
	// let _this = this; // function(){} 사용하려면 this를 바인딩 해줘야 한다.
	init: function() {
		$("#btn-save").on("click", ()=> // function(){}대신 ()=>{}를 사용하는 이유는 this를 바인딩 하기 위해서!
			this.save()
		);
		$("#btn-delete").on("click", ()=> // function(){}대신 ()=>{}를 사용하는 이유는 this를 바인딩 하기 위해서!
			this.deleteById()
		);
		$("#btn-update").on("click", ()=> // function(){}대신 ()=>{}를 사용하는 이유는 this를 바인딩 하기 위해서!
			this.update()
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
	
	deleteById: function() {
		let id = $("#id").text();

		$.ajax({
			type : "DELETE",
			url : "/api/board/"+id,
			dataType : "json" // 요청을 서버로 해서 응답이 왔을때  기본적으론 문자열이 응답 => 생긴게 JSON이라면 javascript 오브젝트로 변경.
		}).done(function(resp){ //  javascript 오브젝트로 변경된 응답 값이 파라미터에 저장
			alert("삭제가 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});  
		
	},
	
		update: function() {
		let id = $("#id").val();
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		
		$.ajax({
			type : "PUT",
			url : "/api/board/"+id,
			data : JSON.stringify(data), // http body 데이터
			contentType : "application/json; charset=utf-8",  //MIME 타입
			dataType : "json" // 요청을 서버로 해서 응답이 왔을때  기본적으론 문자열이 응답 => 생긴게 JSON이라면 javascript 오브젝트로 변경.
		}).done(function(resp){ //  javascript 오브젝트로 변경된 응답 값이 파라미터에 저장
			alert("글 수정 완료.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});  
		
	},
	

}

index.init();