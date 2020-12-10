<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	   	<meta http-equiv="Pragma" content="no-cache" />
	   	<meta http-equiv="Expires" content="0" />
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
		<style>
			body{
			font-size: 20px;
			}
            #body{
                position: static;
				width: 1095px;
     			height: 755px;
            }
            #viewbox{
                position: relative;
                width: 1095px;
                height: 610px;
            }
            #viewDetail{
                position: absolute;
                width: 1075px;
                height: 610px;
                top: 10px;
                left: 20px;
                float:left;
                overflow:auto;
            }
	/* 		 html{ -ms-overflow-style: none;}::-webkit-scrollbar { display: none; } */
            .viewDetail2 {
                position: relative;
                margin: 15px 0px;
                text-align: center;
            }
            .viewDetail2 table, th, td{
                border: 1px solid black;
                border-collapse: collapse;
                width: 700px;
            }
            .viewDetail2 table{
                margin: 10px auto;
            }
            .viewDetail2 a{
            	color: black;
            	font-weight: 700;
         		text-decoration:none;
            }
            .viewDetail2 a:hover{
            	font-weight: 700;
         		text-decoration:underline;
            }
            table div{
                float: left;
            }
            table img{
                width: 250px;
                height: 250px;
                border-radius: 50%;
            }
            .date{
                position: absolute;
                left: 440px;
            }
            .updateDelete{
                position: absolute;
                left: 448px;
                float: left;
            }
            .guestbookDelete{
           		position: absolute;
            	border: none;
            	left: 400px;
           		background: url( "images/deleteIcon.png" ) no-repeat;
             	background-size: 27px 27px;
             	width: 35px;
             	height: 35px;
             	text-indent: -9000px;
             	border: none;
             	 
            }
            .guestBookUpdateBtn{
            	position: absolute;
              	background: url( "images/updateIcon.png" ) no-repeat;
             	background-size: 27px 27px;
             	width: 35px;
             	height: 35px;
             	text-indent: -9000px;
             	border: none;
             	left: 365px
            }
            .GuestbookDetail{
                margin: 20px 20px;
            }
            #wrightBox{
                position: absolute;
                top: 10px;
                width: 620px;
                height: 80px;
                top: 650px;
                left: 241px;
                text-align: center;
            }
            #wrightBox div{
                position: absolute;
                left: 25px;
                top: 18px;
            }
            #wrightBox input[type="text"]{
                width: 505px;
                height: 40px;
            }
            #wrightBox input[type="submit"]{
                width: 60px;
                height: 40px;
            }
		</style>
	</head>
	<body>
		<div id="body"> <!-- 미니홈피 틀 크기 맞추기용 div영역 -->
            <div id="viewbox"> <!-- 방명록 글들 들어올 영영(다른 영역이랑 겹치지 않기용)-->
                <div id="viewDetail"> <!--방명록 글들 들어올 영역(스크롤용)-->
                  <c:forEach items="${list}" var="guestBook">
	                    <div class="viewDetail2"><!--여기 영역 하나가 방명록 DB에서 불러올 부분-->
	                    <input type="hidden" name="guestBookIdx" value="${guestBook.guestBookIdx}"/>
	                        <table>
	                            <tr>
	                                <td>
	                                    <div class="userName"><a href='loadMinihome?id=${guestBook.guestBookUser_name}' target='_blank'>${guestBook.guestBookUser_name}</a></div>
	                                    <div class="date">${guestBook.guestBookReg_date}</div>
	                                    <div class="updateDelete">
		                                    	<input type="button" value="${guestBook.guestBookUser_name}" name="${guestBook.guestBookIdx}" class="guestBookUpdateBtn"/>
		                                	<form action="guestbookDelete" method="post">
		                                		<input type="hidden" name="guestBookIdx" value="${guestBook.guestBookIdx}"/>
		                                		<input type="hidden" name="homephost" value="${guestBook.id}"/>
		                                		<input type="submit" value="삭제" class="guestbookDelete" width="20px"/>
		                                	</form>
										</div>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td>
	                                    <div>
	                                    <img class="NO-CACHE" src="/profilePhoto/${guestBook.guestBookUser_name}profilephoto.jpg" alt="userProfile" title="userName">
	                                    </div>
	                                    <div class="GuestbookDetail" id="GuestbookDetail${guestBook.guestBookUser_name}">
	                                        ${guestBook.guestBookContent}
	                                    </div>
	                                </td>
	                            </tr>
	                        </table>
	                    </div>
                    </c:forEach>
            	</div>
            <div id="wrightBox"><!--방명록 작성 영역-->
                <div id="wrightBoxForm">
                    <form action="guestbookWrite" method="POST">
                    	<input type="hidden" name="homephost" value="${homephost}"/>
                        <input type="text" name="GuestbookWright" placeholder="메시지를 입력하세요"/>
                        <input type="submit" value="입력"/>
                    </form>
                </div>
            </div>
        </div>
	</body>
	<script>
	$(document).ready(function () { $('.NO-CACHE').attr('src',function () { return $(this).attr('src') + "?a=" + Math.random() }); });//이미지 캐싱 방지
	var msg="${msg}";
	if(msg!=""){
		alert(msg);	
	}
	
	var homephostId = $("input[name='homephost']").val();
	followCheck();
	function followCheck() {//팔로우중인지 확인
			$.ajax({
			type:"get",
			url:"followCheck",
			data:{"homephostId": homephostId},
			dataType:"JSON",
			success:function(data){ 		
				if(data.result){
					
				}else{
					$("#wrightBoxForm").css({"display":"none"});
					$("#wrightBox").html("<p id='nofollowMsg'>팔로우하면 방명록을 작성할 수 있어요~<p>")
				}
			},
			error:function(e){
				console.log(e);
			}
		});
	}

	var guestbookUpdateText ="";
	var guestBookIdx ="";
	$(".guestBookUpdateBtn").click(function () {
		var id = $(this).val();
		var idx = $(this).attr("name");
		console.log(id,idx);
		$.ajax({
			type:"post",
			url:"guestBookUpdateCheck",
			data:{
				"guestBookUpId": id,
				"guestBookIdx" : idx
				},
			dataType:"JSON",
			success:function(data){ 		 
				if(data.result){
					var guestbookcontent = data.guestbookcontent;
					$("#GuestbookDetail"+id).empty();
					$("#GuestbookDetail"+id).append("<textarea id='guestbookUpdateText' style='resize:none; width:280px; height:100px'>"+guestbookcontent+"</textarea>");
					$("#GuestbookDetail"+id).append(" <input type='button' value='수정' id='guestbookUpdate'/>");
					$("#guestbookUpdate").click(function () {
						guestbookUpdateText = $("#guestbookUpdateText").val();
						guestBookIdx = idx;
						guestBookUpdate();
					})
				}else{
					alert("작성자만 수정할 수 있습니다.");
				}
			},
			error:function(e){
				console.log(e);
			}
   		});
	});

	function guestBookUpdate() {
		$.ajax({
			type:"post",
			url:"guestBookUpdate",
			data:{
				"guestbookUpdateText": guestbookUpdateText,
				"guestBookIdx" : guestBookIdx
				},
			dataType:"JSON",
			success:function(data){ 		
				if(data.result){
					parent.document.location.reload();
					alert("방명록 수정 완료.");
				}else{
					alert("다시 시도해주세요.");
				}
			},
			error:function(e){
				console.log(e);
			}
		});
	}
	</script>
</html>
