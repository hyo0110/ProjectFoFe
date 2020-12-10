<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		#dark{
            width: 101%;
            height: 101%;
            background: rgba(0,0,0,0.4);
            position: absolute;
            top: 0;
            left: 0;
            display: none;
         }
         #popup{
             z-index: 999;
             position: absolute;
             box-sizing: border-box;
             max-width: 1000px;
             min-width: 600px;
             width: 750px;
             height: 600px;
             background-color: snow;
             top: 50%;
             left: 50%;
             margin-left: -375px;
             margin-top: -300px;
             padding: 0;
         }
         #album_photo{
             box-sizing: border-box;
             width: 300px;
             height: 600px;
             border-right: 1px solid #b3b3b3;
             float: left;
         }
         .community{
            border-left: black;
            width: 333px;
            height: 600px;
            float: left;
         }
         #album_text{
             box-sizing: border-box;
             width: 200px;
             height: 120px;
             border-bottom: 1px solid #b3b3b3;
             
         }
         #x_anyclose{
         	position: fixed;
         	width: 101%;
         	height:101%;
         	font-size: 40px;
         	background-color: transparent;
         	border: none;
         }
         #x_close{
         	position: fixed;
         	top: 18px;
         	right: 20px;
         	width: 45px;
         	height:45px;
         	color: white;
         	font-size: 40px;
         	background-color: transparent;
         	border: none;
         }
         #album_reply{
             box-sizing: border-box;
             width: 200px;
             height: 180px;
             float: left;
         }
         #image1{/*이미지를 대용하는 버튼. 추후 삭제*/
         	width: 180px;
         	height: 180px;
         	background-color: #d3dbff;
         	font-size: 40px;
         	margin-left: 20px;
         	margin-top: 20px;
         }
         #replyCont{
			width: 250px;
			height: 19px;
         }
	</style>
	</head>
	<form name="detailForm">
			<input type="button" value="이미지1" id="image1"/><!-- 이미지를 대신하는 버튼. 추후 삭제 -->
			<input type="hidden" value="1" id="replyLevel" name="replyLevel" />
			<input type="hidden" value="1" id="albumIdx" name="albumIdx" />
			<div id="dark">
                <button id="x_anyclose"></button>
				<button id="x_close">x</button>
	            <div id="popup">
                    <div id="album_photo">(해당 앨범 사진 영역)</div>
                    <div class="community">
                        <div id="album_text">(글 영역)</div>
                        <div id="album_reply">
                            (댓글 영역) </br>
                            <!-- 게시 버튼 -->
                            <input id="replyCont" name="replyCont" type="text" placeholder="댓글 달기.."/>
                            <input id="replyBtn" type="button" value="게시"/>
                            <div id="replyDiv">
                            </div>
                        </div>
                    </div>
	            </div>
	        </div>
    </form>
	</body>
	<script>
		$("#image1").click(function(){
			$("#dark").show();
		});
		$("#x_close").click(function(){
			$("#dark").css("display","none");
		});
		
		
		//댓글달기 버튼
		$("#replyBtn").click(function(){
			var queryString = $("form[name=detailForm]").serialize();
			console.log(queryString);
			
			$.ajax({
				type:'get',
				url:'albumReply',
				data: queryString,
				dataType:'HTML',
				success:function(result){
					console.log(result);
					// 댓글 리스트 갱신 이루어져야됨
					ReplyListSearch();
				},
				error:function(error){
					console.log(error);
				}		
			});
		});
		
		//댓글목록 불러오기
		var ReplyListSearch = function(){
			$.ajax({
				type:'get',
				url:'replyList',
				data: {
					"albumIdx": $("#albumIdx").val()
				},
				dataType:'HTML',
				success:function(result){
					//console.log(result);
					console.log("댓글 리스트 조회 성공");
					// 댓글 리스트 갱신
					$("#replyDiv").html(result);
				},
				error:function(error){
					console.log("댓글 리스트 조회 에러");
					console.log(error);
				}
			});
		}
		
		//댓글 삭제
		var replyDelete = function(replyIdx){
			$.ajax({
				type:'get',
				url:'replyDel',
				data: {
					"replyIdx": replyIdx
				},
				dataType:'HTML',
				success:function(result){
					// 댓글 리스트 갱신
					ReplyListSearch();
				},
				error:function(error){
					console.log(error);
				}
			});
		}

			
	</script>
</html>