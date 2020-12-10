<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>

    <head>
    	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	   	<meta http-equiv="Pragma" content="no-cache" />
	   	<meta http-equiv="Expires" content="0" />
        <meta charset="UTF-8">
        <title>프로필</title>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <style TYPE="text/css">
            #profile{
            	box-sizing: border-box;
                width: 800px;
                height: 500px;
                margin-left: auto;
                margin-right: auto;
                margin-top: 150px;
            }
            #chart{
                font-size: 20px;
                width: 800px;
                height: 400px;
                padding: 8px;
                margin-left: auto;
                margin-right: auto;
                margin-top: 60px;
            }
            .tdMenu{
            	width: 200px;
            }
            #chart tr td{
            	background-color: #ebecf1;
            	border-radius: 5px;
            	padding: 10px;
            }
            #update{
                margin-left: 750px;
            }
        </style>
    </head>
    <body>
        <div id="profile">
	        <input type="hidden" name="homephost" value="${Profile.id}">
	        <div id="posi">
		        <table id="chart">
			        <tr><td class="tdMenu">이름</td><td>${Profile.nickname}</td></tr>
			        <tr><td class="tdMenu">생일</td><td>${Profile.myBirth}</td></tr>
			        <tr><td class="tdMenu">혈액형</td><td>${Profile.blood}</td></tr>
			        <tr><td class="tdMenu">주소</td><td>${Profile.addr}</td></tr>
			        <tr><td class="tdMenu">전공</td><td>${Profile.major}</td></tr>
			        <tr><td class="tdMenu">나만의 이성유혹법</td><td>${Profile.seduWay}</td></tr>
			        <tr><td class="tdMenu">좌우명</td><td>${Profile.motto}</td></tr>
			        <tr><td class="tdMenu">인생영화</td><td>${Profile.fMovie}</td></tr>
		        </table>
	       	</div>
	      	<input id="update" type="button" value="EDIT" onclick="location.href='profileUpdateForm?id=${Profile.id}'"/>
        </div>
    </body>
    <script>
	    var msg="${msg}";
	    if(msg!=""){
	    	alert(msg);
	    }
		
        var homephostId = $("input[name='homephost']").val();
        minihomeCheck();//미니홈피 주인 확인
   		function minihomeCheck() {
   			$.ajax({
    			type:"get",
    			url:"minihomeCheck",
    			data:{"homephostId": homephostId},
    			dataType:"JSON",
    			success:function(data){ 		
					if(data.result){ //미니홈피 주인이 맞다면
					}else{
						$("#update").css({"display":"none"});
					}
    			},
    			error:function(e){
    				console.log(e);
    			}
    		});
		}



    </script>
</html>