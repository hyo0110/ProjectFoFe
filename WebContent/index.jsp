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
        <link rel="icon" href="images/icon_ff.png">
        <title>포플</title>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <style>
            /*로그인, 회원가입*/
            .login {position: fixed;bottom: 0;z-index: 101;left: 0px;min-width: 100%;min-height: 75px;font-size: 12pt;border-top: 1px solid #5aad01; background-color: #3b7200;}
            .login_box {margin-right: 15px;position: absolute;top: 15px;right: 15%;}
            .login :link{color: #5aad01; text-decoration:none;}
            .login :visited{color: #5aad01;}
            .login :hover{color: #3b7200;}
            .login :active{color: #5aad01;}

            /*로고*/
            .logo {text-align: left;position: absolute; left: 15%;}

            /*검색창*/
            .search {position: fixed; z-index: 100; min-width: 1230px;min-height: 50px;text-align: center;position: relative;top: 15px;}
            #srch {width: 390px;height: 25px;border-radius: 20px;color: #5aad01;font-size: 12pt;font-weight: bold;border: 1px solid #8aca46;background-image: url();background-repeat: no-repeat;padding: 5px 37px 5px 33px;background-color: white;}
            #srch:focus {outline:none;}
            #srch:hover {border: 1px solid #6ebd19;}
            #srch::placeholder {color: #6ebd19; text-align:center;}
            #srchBtn{height: 36px;border-radius: 10px;color: #8aca46;font-size: 12pt;font-weight: bold;border: 1px solid #8aca46;background-image: url();background-repeat: no-repeat;padding: 5px; background-color: white}
            #srchResult {display: none;position: fixed;z-index: 99;bottom: 45px;left: 50%;margin-left: -256px;width: 420px;padding: 10px 10px 23px 30px;border-radius: 15px 15px 0px 0px;font-size: 12pt;border: 1px solid #8aca46;background-color: white;}
         
            /*친구목록*/
            .friends_po{width: 100%; min-width:1230px; text-align: center;}
            .friends{width: 100%; height:300px; text-align: center; border-collapse: collapse;}
            .friendsPhoto{width: 300px; height: 300px; text-align:center;}
            .ImgRadius{max-width: 300px; max-height: 300px; border-radius:30px;}
            td{max-width: 300px; max-height: 300px;}
            .friendName :link{color: #5aad01; text-decoration:none;}
            .friendName :visited{color: #5aad01;}
            .friendName :hover{color: #3b7200;}
            .friendName :active{color: #5aad01;}
            .photo{cursor: pointer;}

            /*웹폰트 적용*/
            @import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&display=swap');
            body{font-family: 'Black Han Sans', sans-serif; margin: 0px;}

            /*더보기 버튼*/
            #button{position: absolute; width:70px; height:30px; left: 48%; top: 96%; cursor: pointer;
            border: 1px solid #86d356; border-radius:30px; background-color: #86d356; font-size: 12pt; color: white;}
            
            
            
            
            
            
            
            /*미니홈피*/
            #whole{
         position: absolute;
         width: 1310px;
         height: 830px;
         background-color: grey transparent;
         float: left;
         border-radius: 20px;
         left: 50%;
         top: 50%;
         margin-left: -700px;
         margin-top: -415px;
      }
   
      #minihome{
         width: 100%;
         height: 100%;
         background-color: transparent;
         margin-left: auto;
         margin-right: auto;
         border-radius: 15px;
         font-size: 32px;
      }
      #top{
         width: 100%;
         height: 55px;
         float: left;
         padding: 0;
         margin: 0;
         background-color: transparent;
      }
      #top-menu{
         float: right;
      }
      #minihome_name{
         position: relative;
         width: 400px;
         height: 40px;
         margin-bottom: 10px;
         margin-right: 120px;
         background-color: transparent;
         float: left;
      }
      #minihome_nameBtn{
         position: absolute;
         top: 20px;
         left: 400px; 
      }
      #minihome_nameEdit{
         position: absolute;
         top: 20px;
         left: 400px; 
      }
      #top_menu{
         width: 100px;
         height: 56px;
         font-size: 11px;
         background-color: white;
         border: 1px solid white;
         border-radius: 3px;
         box-shadow: 0px 6px 21px 1px #d3d3d3;
      }
      .top_menu {
         width: 100px;
         height: 56px;
         font-size: 11px;
         background-color: white;
         border: 1px solid white;
         border-radius: 3px;
         box-shadow: 0px 6px 21px 1px #d3d3d3;
      }
      #side_menu{
         box-sizing: border-box;
         width: 250px;
         height: 730px;
         background-color: #caebc7;
         border-radius: 10px 0px 0px 10px;
         box-shadow: 4px 17px 55px 1px #d3d3d3;
         float: left;
      }
      #profile_image{
         width: 220px;
         height: 220px;
         border-radius: 50%;
         margin: 15px;
         border: 1px solid white;
      }
      /* 팔로우 영역-----------------------------------------------*/
      #followArea{
         position: relative;
         height: 30px;
         margin-bottom: 10px;
      }
      #follow{
         background-color: white;
         position: absolute;
         width: 210px;
         height: 23px;
         border-radius: 20px;
         left: 50%;
         margin-left: -105px;
         border: 0px;
      }
      #unFollow{
         background-color: black;
         color: white;
         position: absolute;
      }
      /* 팔로우 영역----------------------------------------------- */
      #profile_message{
         position: relative;
         width: 250px;
         height: 100px;
         margin-top: 50px;
         font-size: 14px;
         text-align: center;
      }
      #profile_messageBtn{
         position: absolute;
         left: 193px;
         top: 100px;
      }
      input[name="minihome_nameEdit"]{
         width: 396px;
         height: 40px;
         font-size: 32px;
         border: none;
      }
      input[name="profile_messageEdit"]{
         position: absolute;
         left: 1px;
         top: 2px;
         width: 240px;
         height: 100px;
         border: none;
      }
      #email{
         width: 240px;
         height: 50px;
         margin-left: 30px;
         position: absolute;
         bottom: 60px;
         font-size: 14px;
      }
      #changer {
         box-sizing: border-box;
         width: 1060px;
         height: 730px;
         background-color: snow;
         border-radius: 0px 0px 10px 0px;
         box-shadow: 4px 17px 55px 1px #d3d3d3;
         /* border: 2px solid gray; */
         float: right;
      }
      audio{
         width: 150px;
         height: 30px;
      }
      iframe {
         width: 1040px;
          height: 710px;
         left: 50%;
         top: 50%;
         margin-left: -547.5px;
         margin-top: -377.5px;
      }
      #homeView{display: block; margin: 10px 0px 0px 10px;}
      #profileView{display: none; margin: 10px 0px 0px 10px;}
      #diaryView{display: none; margin: 10px 0px 0px 10px;}
      #albumView{display: none; margin: 10px 0px 0px 10px;}
      #guestBookView{display: none; margin: 10px 0px 0px 10px;}
      #manageView{display: none; margin: 10px 0px 0px 10px;}
      .profileImg{
      border-radius: 50%;
      width: 35px;
      height: 35px;
      }
        </style>
    </head>
    <body >
        <!--1페이지-->
        <div style="width: 100%; height: 100%; padding: 0; margin: 0; border: 0;">
            <div>
                <!--로그인, 회원가입-->
            <div class="login">
                    <div>
                        <!--검색창-->
                        <div class="search">
                            <!--로고-->
                            <div class="logo">
                                <a href="javascript:window.location.reload();"><img src="images/logo_fofriends.png" width="55px"></a>
                            </div>
                            <input id="srch" type="text" name="srchText" placeholder="포플 친구를 검색하세요!">
                            <input type="button" id="srchBtn" value="검색">
                            <div class="login_box">
                                <span><a href="login.jsp">로그인</a></span>
                                <span><a href="joinForm.jsp">회원가입</a></span>
                            </div>
                            <!--<a href="Profile.jsp">프로필 테스트 중. 추후 지울 링크</a> -->
                        </div>
                    </div>
                    <div>
                    <div id="srchResult">
                       <table id="searchResult"></table>
                   </div>
            </div>
            </div>
        </div>
        
        
        
        <!-- 2페이지 -->
        <div style="background-color: #caebc7; width: 101%; height: 101%;" id="hmpclick">
      <div id="whole">
         <div id="minihome">
            <div id="top">
               <div id="minihome_name"><!--최상단의 미니홈피 이름 구역-->
                  <div id="minihome_nameDetail">FOREST FRIENDS</div>
               </div>
               <div style="position: absolute;top: 11px; left: 475px;">
                  <audio autoplay controls loop>
                        <source src="#" type="audio/mp3">
                  </audio>
               </div>
               <div id="top-menu">
                  <button class="top_menu" id="manageBtn">관리</button>
                  <button class="top_menu" id="profileBtn">프로필</button>
                  <button class="top_menu" id="diaryBtn">다이어리</button>
                  <button class="top_menu" id="albumBtn">사진첩</button>
                  <button class="top_menu" id="guestBookBtn">방명록</button>
                  <button class="top_menu" id="homeBtn">홈</button>
               </div>
            </div>
            <div id="center">
               <div id="side_menu">
                  <div id="profile_image"><img alt="" src="images/mainProfile.png" width="230px" style="border-radius:50%" height="230px"></div>
                  <div id="followArea"><input type="button" name="followbtn" id="follow" onclick="follow()" value="팔로우"></div>
                  <div id="profile_message">
                     <div id="profile_messageDetail">새로운 SNS의 시작, Forest Friend</div>
                  </div>
                  <div id="email">forest friends</br>forestfriends@ff.kr</div>
               </div>
               <div id="changer"><!-- 게시판 내용 나타나는 구역 homeView만 나타나있고 나머지는 메뉴버튼 클릭 시 나타남 -->
               		<img alt="" src="images/Main.png" width="100%">
               </div>
            </div>
         </div>
      </div>
         <input type="hidden" name="homephost" value="${minihome.id}"><!-- 현재 홈페이지 주인 확인용 hidden -->
   </div>
    </body>
    <script>
    $(document).ready(function () { $('.profileImg').attr('src',function () { return $(this).attr('src') + "?a=" + Math.random() }); });//이미지 캐싱 방지
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
              $("#searchResult").empty();
           }
       });
       
       $(document).keydown(function(event) {
           if ( event.keyCode == 13 || event.which == 13 ) {
              $("#srchBtn").click();
           }
       });
    
       $("#srchBtn").click(function () {
          var srchName = $("input[name='srchText']").val();
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
                       $("#searchResult").css({"display":"block"});
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
      
    </script>
</html>