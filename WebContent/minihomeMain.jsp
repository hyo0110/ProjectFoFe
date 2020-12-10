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
         #homeView{
            position: relative;
            display: block;
            width: 1095px;
              height: 755px;
            
            }
         #newDiaryList{
            position: absolute;
            border-radius: 10px;
            width: 335px;
            height: 217px;
            top: 40px;
            left: 725px;
            border: 1px solid black;
			text-align: center;
         }
         #newDiaryList span{
         position:relative;
         top: 10px;
         font-size: 22px;
         }
         #newDiaryList a{
         color: black;
         text-decoration:none;
         }
         #newDiaryList a:hover{
         color: black;
         text-decoration:underline;
         }
         #newDiaryListTable{
         text-align: left;
         margin:20px 0px 0px 5px;
         font-size: 15px;
         }
         #newDiaryListTable tr,td{
         padding: 5px;
         }

         #mainPhoto{
            position: absolute;
            width: 709px;
             height: 419px;
             top: 296px;
             left: 40px;
             text-align: center;
         }
         #followerTalk{
            position: absolute;
            border-radius: 10px;
             width: 650px;
             height: 217px;
             top: 40px;
             left: 40px;
             border: 1px solid black;
			 overflow-y:auto;
			 -ms-overflow-style: none;
         }::-webkit-scrollbar { display: none; }
         #followerTalk span{
         position: relative;
        top: 10px;
        left: 5px;
         }
         #followerTalk a{
         	color: black;
         	text-decoration:none;
         }
         #followerTalk a:hover{
         	text-decoration:underline;
         }
         #followerTalkTxt{
         position: relative;
         width: 642px;
          height: 20px;
          background-color: transparent;
          border: 1px solid #80808033;
          margin-left: 1px; 
          top: 16px;
         }
         #followerTalkWriteBtn{
         position: absolute;
         left: 625px;
         top: 39px;
         margin: 1px 0px 0px 1px;
   		background: url( "images/updateIcon.png" ) no-repeat;
     	background-size: 20px 20px;
     	width: 20px;
     	height: 20px;
     	text-indent: -9000px;
     	border: none;
         }
         #followerTalkList{
         position: relative;
         top: 13px;
         }
         #followerTalkDeleteBtn{
         margin: 1px 0px 0px 1px;
   		background: url( "images/updateIcon.png" ) no-repeat;
     	background-size: 20px 20px;
     	width: 20px;
     	height: 20px;
     	text-indent: -9000px;
     	border: none;
         margin: 1px 0px 0px 1px;
   		 background: url( "images/deleteIcon.png" ) no-repeat;
     	 background-size: 20px 20px;
     	 width: 20px;
     	 height: 20px;
     	 text-indent: -9000px;
     	 border: none;
         }
         #mainImage{
			position: absolute;
		    top: 320px;
		    left: 676px;
		    width: 462px;
		    height: 468px;
         }
      </style>
   </head>
   <body>
      <div id="homeView">
             <div id="newDiaryList"><span>new 다이어리</span><table id="newDiaryListTable"></table></div>
             <div id="followerTalk">
                 <span>한줄평</span>
                <form action="followerTalkWrite" method="post">
                   <input type="text" name="followerTalk" id="followerTalkTxt"><input type="submit" id="followerTalkWriteBtn" value="작성">
                   <input type="hidden" name="homephost" value="${homephostId}">
                </form>
                <table id="followerTalkList"></table>
             </div>
             <div id="mainPhoto"><img alt="" src="/profilePhoto/${mainphoto}" height="100%" width="100%" style="border-radius: 10px;"></div>
             <div id="mainImage"><img src="images/mainFriends.png" width="100%" height="100%"/></div>
         </div>
         
   </body>
   <script>
      var msg="${msg}";
      if(msg!=""){
         alert(msg);   
      }
      var homephostId = $("input[name='homephost']").val();
      getNewDiaryList();//미니홈피 메인 다이어리 가져오기
      function getNewDiaryList() {
         $.ajax({
         type:"post",
         url:"getNewDiaryList",
         data:{
            "homephostId": homephostId
         },
         dataType:"JSON",
         success:function(data){
            console.log(data.list);
            $("#newDiaryListTable").empty();
            for (var i = 0; i < data.list.length; i++) {
               var diarysubject = data.list[i].diarysubject;
               var diaryidx = data.list[i].diaryidx;
               $("#newDiaryListTable").append("<tr>"
                        +"<td> ㆍ <a href='diaryDetail?idx="+diaryidx+"' target='_self'>"+diarysubject+"</a></td>"
                        +"</tr>");
            }
         },
         error:function(e){
            console.log(e);
         }
      });
   }
      getfollowerTalkList();
      function getfollowerTalkList() {
         $.ajax({
            type:"post",
            url:"getfollowerTalkList",
            data:{
               "homephostId": homephostId
            },
            dataType:"JSON",
            success:function(data){
               console.log(data.list);
               $("#followerTalkList").empty();
               for (var i = 0; i < data.list.length; i++) {
                  var followerTalkIdx = data.list[i].followerTalkIdx;
                  var followerTalkUser_name = data.list[i].followerTalkUser_name;
                  var followerTalkContent = data.list[i].followerTalkContent;
                  $("#followerTalkList").append("<tr>"
                           +"<th><a href='loadMinihome?id="+followerTalkUser_name+"' target='_blank'>"+followerTalkUser_name+"</th>"
                           +"<td>"+followerTalkContent+"</td>"
                           +"<td><form action='followerTalkDelete' method='post'><input type='hidden' name='followerTalkIdx' value='"+followerTalkIdx+"'><input type='hidden' name='homephostId' value='"+homephostId+"'>"
                           +"<input type='submit' id='followerTalkDeleteBtn' value='삭제'></form></td>" 
                           +"</tr>");
               }
            },
            error:function(e){
               console.log(e);
            }
         });
      }
      
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
                  $("#followerTalkWriteBtn").css({"display":"none"});
                  $("#followerTalkTxt").attr("disabled",true);
                  $("#followerTalkTxt").val("팔로우를 맺으면 한줄평을 남길 수 있어요*^^*");
               }
            },
            error:function(e){
               console.log(e);
            }
         });
      }
   </script>
</html>