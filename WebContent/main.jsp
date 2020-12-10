<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
    	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	   	<meta http-equiv="Pragma" content="no-cache" />
	   	<meta http-equiv="Expires" content="0" />
        <meta charset="utf-8">
        <link rel="icon" href="icon_ff.png">
        <title>포플</title>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <style>
            /*로그인, 회원가입*/
            .login {position: fixed;bottom: 0;z-index: 101;left: 0px;min-width: 100%;min-height: 75px;font-size: 12pt;border-top: 1px solid #5aad01; background-color: #3b7200;}
            .login_box {margin-right: 15px;position: absolute;top: 32%;right: 15%;z-index: 101;}
            .login :link{color: #5aad01; text-decoration:none;}
            .login :visited{color: #5aad01;}
            .login :hover{color: #3b7200;}
            .login :active{color: #5aad01;}
            #MyProfile{width: 50px; height: 50px; border-radius: 50%; border: 0px; padding: 0; margin: 0; vertical-align: middle;}

            /*로고*/
            .logo {text-align: left;position: absolute; left: 15%;}
            .logo2 {width: 99%;height: 310px;text-align: center;}
			
			/*내 미니홈피 가기*/
			#myhome{width: 100px;position: absolute;cursor: pointer; left:15%; top:32%; z-index:101;}
			
            /*검색창*/
            .search {position: fixed; z-index: 100; min-width: 1230px;min-height: 50px;text-align: center;position: relative;top: 15px;}
            #srch {width: 390px;height: 25px;border-radius: 20px;color: #5aad01;font-size: 12pt;font-weight: bold;border: 1px solid #8aca46;background-image: url();background-repeat: no-repeat;padding: 5px 37px 5px 33px;background-color: white;}
            #srch:focus {outline:none;}
            #srch:hover {border: 1px solid #6ebd19;}
            #srch::placeholder {color: #6ebd19; text-align:center;}
            #srchBtn{height: 36px;border-radius: 10px;color: #8aca46;font-size: 12pt;font-weight: bold;border: 1px solid #8aca46;background-image: url();background-repeat: no-repeat;padding: 5px; background-color: white}
            #srchResult {display: none;position: fixed;z-index: 99;bottom: 45px;left: 50%;margin-left: -256px;width: 420px;padding: 10px 10px 23px 30px;border-radius: 15px 15px 0px 0px;font-size: 12pt;border: 1px solid #8aca46;background-color: white;}
            
            /*친구목록*/
            .friends_po{position: relative; top: 10px; width: 500px; height: 535px; margin: 0 auto; margin-bottom: 74px;}
            .followingFriends{position: absolute; border: 1px solid; width: 500px; top: 40px; display: block;}
            .followerFriends{position: absolute; border: 1px solid; width: 500px; top: 40px; display: none;}
            .ImgRadius{max-width: 300px; max-height: 300px; border-radius:30px;}
            td{max-width: 300px; max-height: 300px;}
            .photo{cursor: pointer;}
			#following{
			position: absolute;
			height: 30px;
			width: 200px;
			background-color: green;
			font-weight: 700;
			border-radius: 15px;
			text-align: center;
			cursor: pointer;
			}
			#follower{
			position: absolute;
			height: 30px;
			width: 200px;
			background-color: green;
			left: 300px;
			font-weight: 400;
			border-radius: 15px;
			text-align: center;
			cursor: pointer;
			}
            /*웹폰트 : 적용은 안됨. 추후 시간나면 하려고 형식만 일단 가져옴.*/
            @import url('https://fonts.googleapis.com/css?family=Nanum+Gothic');
            body { font: 17px 'Nanum Gothic', sans-serif; }

            /*더보기 버튼*/
            #button{position: absolute; width:70px; height:30px; left: 48%; top: 96%; cursor: pointer;
            border: 1px solid #86d356; border-radius:30px; background-color: #86d356; font-size: 12pt; color: white;}
            
            .profileImg{
		      border-radius: 50%;
		      width: 35px;
		      height: 35px;
			}
			
			 .profileImg2{
		      border-radius: 50%;
		      width: 70px;
		      height: 70px;
			}
			a:link {text-decoration: underline; color: black;}
			a:visited {text-decoration: underline; color: black;}
			a:active {text-decoration: underline; color: black;}
			/* 무한스크롤 */
			#infinityScroll{width:100%;height:1080px;}
			html{ -ms-overflow-style: none; } ::-webkit-scrollbar { display: none; }
			
        </style>
    </head>
    <body>
    	<input type="hidden" name="loginId" value="${loginId}"/> <!-- 로그인된 아이디 확인용 히든 -->
        <!--전체 감싸기-->
        <!--1페이지-->
        <div style="width: 100%; height: 100%; padding: 0; margin: 0; border: 0;">
            <div>
                <!--로그인, 회원가입-->
                <div class="login">
                    <div class="login_box">
	                    <span><a href="logout">로그아웃</a></span>
                    </div>
                    <div id="myhome" onclick="goMyHome()"><a href='loadMinihome?id=${loginId}' target='_blank'>내 미니홈피</a></div>
                    
                    <!--검색창-->
	                <div class="search">
	                		<!--로고-->
	                		<input id="srch" type="text" name="srchName" placeholder="포플 친구를 검색하세요!"/>
	                		<input type="button" id="srchBtn" value="검색"/>
	                </div>
	                <div id="srchResult">
                       <table id="searchResult"></table>
                   </div>    
                </div>
				<!-- <a href="profileDetail?id=${Profile.id}">프로필 테스트 중. 추후 지울 링크</a> -->
                <!--로고-->
                <div class="logo2">
                	<!-- 현재페이지 리프레쉬 기능 ↓↓↓ -->
                    <a href="javascript:window.location.reload();"><img src="images/logo_fofriends.png" width="300px"></a>
                </div>

                <!--친구목록-->
                <div class="friends_po">
                	<div id="following" onclick="followingView()">팔로잉</div><div id="follower" onclick="followerView()">팔로워</div>
                    <!-- 추후 사진 추가 : img 태그 class를 ffPhoto로 지정.-->
                    <table class="followingFriends">

					<!-- 추후 사진 추가 : 스크립트에 사진 추가할 예정.-->
                    </table>
                    <table class="followerFriends">

                    </table>
                </div>

				<!-- 무한스크롤 -->
				<div id="infinityScroll">
				</div>
				
            </div>
        </div>


    </body>
    <script>
    $(document).ready(
    		function () { 
    			$('.profileImg').attr('src',function () { 
    				return $(this).attr('src') + "?a=" + Math.random() 
    			}); 
    		}
    );//이미지 캐싱 방지
    $(document).ready(
    		function () { 
    			$('.profileImg2').attr('src',function () { 
    				return $(this).attr('src') + "?a=" + Math.random() 
    			}); 
    		}
    );//이미지 캐싱 방지
    
    	loadFollowList ();
    	loadFollowerList();
    	function loadFollowList () { //팔로우 리스트 불러오기
    		var loginId = $("input[name='loginId']").val();
    		$.ajax({
    			type:"get",
    			url:"loadFollowList",
    			data:{"loginId":loginId},
    			dataType:"JSON",
    			success:function(data){
    				console.log(data);
    				$(".followingFriends").empty();
    					for (var i = 0; i < data.arrList.length; i++) {
        					var id = data.arrList[i].id;
        					var name = data.arrList[i].name;
        					
        					$(".followingFriends").append("<tr>"
        							+"<td><img src='/profilePhoto/"+id+"profilephoto.jpg' class='profileImg2'/></td>"
        				            +"<td><a href='loadMinihome?id="+id+"' target='_blank'>"+id+"</a></td>"
        				            +"<td>"+name+"</td>"
        				            +"</tr>");
        				}
					    				
    			},
    			error:function(e){
    				console.log(e);
    			}
    		});
		}
    	function loadFollowerList () { //팔로워 리스트 불러오기
    		var loginId = $("input[name='loginId']").val();
    		$.ajax({
    			type:"get",
    			url:"loadFollowerList",
    			data:{"loginId":loginId},
    			dataType:"JSON",
    			success:function(data){
    				console.log(data);
    				$(".followerFriends").empty();
    				for (var i = 0; i < data.arrList.length; i++) {
    					var id = data.arrList[i].id;
    					var name = data.arrList[i].name;
    					
    					$(".followerFriends").append("<tr>"
    							+"<td><img src='/profilePhoto/"+id+"profilephoto.jpg' class='profileImg2'/></td>"
    				            +"<td><a href='loadMinihome?id="+id+"' target='_blank'>"+id+"</a></td>"
    				            +"<td>"+name+"</td>"
    				            +"</tr>");
    				}
    			},
    			error:function(e){
    				console.log(e);
    			}
    		});
		}
    	
    	function followingView() {//팔로우 목록 보기
    		$(".followingFriends").css({"display":"block"});
    		$(".followerFriends").css({"display":"none"});
    		$("#following").css({"font-weight":"700"});
    		$("#follower").css({"font-weight":"400"});
		}
		function followerView() {//팔로워 목록 보기
			$(".followingFriends").css({"display":"none"});
    		$(".followerFriends").css({"display":"block"});
    		$("#following").css({"font-weight":"400"});
    		$("#follower").css({"font-weight":"700"});
		}

		$(".unfollowBtn").click(function () {
			var a = $(this);
			console.log(a);
		})

      //친구 검색, 불러오기
    	$("#srchBtn").click(function () {
    		var srchName = $("input[name='srchName']").val();
    		console.log("srchName : "+srchName);
    		
    		$.ajax({
    			type:"get",
    			url:"memberSearch",
    			data:{"srchName":srchName},
    			dataType:"JSON",
    			success:function(data){
    				console.log(data);
    				if(!data.result){
    					alert("친구가 없어요");
    				}else{
    					$("#searchResult").empty();
    					
    					for (var i = 0; i < data.arrList.length; i++) {
        					var id = data.arrList[i].id;
        					var name = data.arrList[i].name;
        					$("#searchResult").append("<tr>"
        							+"<td><img src='/profilePhoto/"+id+"profilephoto.jpg' class='profileImg'/></td>"
        				            +"<td><a href='loadMinihome?id="+id+"' target='_blank'>"+id+"</a></td>"
        				            +"<td>"+name+"</td>"
        				            +"</tr>");
        					
        				}
    					
    				}
    					
    			},
    			error:function(e){
    				console.log(e);
    			}
    		});
    	})
    	//로고 클릭시 검색창 없어짐
    	$(".logo").click(function () {
    		$("#searchResult").empty();
		});
		
		//검색창 보이기
	       $('document').ready(function(){
	          $("#srchBtn").click(function(e){
	             console.log(e);
	             $('#srchResult').show();
	          });
	       });
	       
	       $(document).keydown(function(event) {
	           if ( event.keyCode == 27 || event.which == 27 ) {
	              $("#srchResult").hide();
	           }
	       });
	       
	       $(document).keydown(function(event) {
	           if ( event.keyCode == 13 || event.which == 13 ) {
	              $("#srchBtn").click();
	           }
	       });
		
	       //무한 스크롤
	       //팔로워 수가 많아질경우를 대비. 현재(5명 -> 130명 일 경우라면? 무한스크롤 필요.
    	  /*   var appendDocument = function(){
    		   for (var i=0; i< 1000; i++){
    			   $('<h1>Infinity Scroll</h1>').appendTo('#infinityScroll');
    		   }
    	   };
    	   appendDocument();
    	   $(window).scroll(function (){
    		   var scrollHeight = $(window).scrollTop() + $(window).height();
    		   var documentHeight = $(document).height();
    		   if(scrollHeight == documentHeight){
    			   appendDocumnet();
    		   }
    	   }); */
    </script>
</html>
