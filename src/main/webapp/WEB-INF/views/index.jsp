<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>
<div id="Main"  class="container">

<c:forEach var="board"  items = "${boards.content}">

	<div id = card class="card m-2">
		<div class="card-body">
			<h4 class="card-title">${board.title}</h4>
			<a href="/board/${board.id}" class="btn btn-primary">상세 보기</a>
		</div>
	</div>
	
	</c:forEach>
	
	<!-- 페이지가 첫번째라면 Previous는 disabled -->
<ul class="pagination justify-content-center">
  <c:choose>
  	<c:when test="${boards.first}">
  	 <li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
  	</c:when>
  	<c:otherwise>
  	<li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
  	</c:otherwise>
  </c:choose>
  
  	<!-- 페이지 순번과 해당 페이지 active  -->
    <c:forEach var="i" begin="1" end="${boards.totalPages}">
            <c:choose>
                <c:when test="${i eq boards.number+1}">
                    <li class="page-item active"><a class="page-link" href="?page=${i -1}">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href="?page=${i -1}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
   
  	<!-- 페이지가 마지막이라면 Next는 disabled -->
  <c:choose>
  	<c:when test= "${boards.last}">
  	  <li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
  	</c:when>
  	<c:otherwise>
  	<li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
  	</c:otherwise>
  </c:choose>
  

</ul>

</div>


<%@ include file="layout/footer.jsp"%>

