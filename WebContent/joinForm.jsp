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
        <title>01_start.html</title>
        
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <style>
            #join{
            	box-sizing: border-box;
                margin-left: auto;
                margin-right: auto;
                margin-top: 10px;
                border: 1px solid black;
                width: 400px;
                height: 500px;
                padding: 50px 25px 0 25px;
                border-radius: 30px;
            }
            #joinbutton{
                border-radius: 30px;
                background-color: black;
                color: blanchedalmond;
                width: 200px;
                height: 40px;
                margin: 25px;
            }
            #jointitle{
                font-size: 20px;
                margin-left: auto;
                margin-right: auto;
                margin-top: 100px;
                width: 400px;
                height: 200px;
            }
            #joinchild{
                font-size: 20px;
                width: 310px;
                height: 300px;
               	margin-left: auto;
               	margin-right: auto;
            }
           	#checkMessage{
           		width: 200px;
           		height: 8px;
           		font-size: 7px;
           		margin-bottom: 5px;
           	}
            #sidelogo{
                position: absolute;
                top: 30%;
                left: 1%;
                /* display: none;<!--잠깐 지웠음--> */
            }
            #background{
                position: relative;
                width: 100%;
                height: 100%;
                background-image: url(#);
            }
            #x{
                position: absolute;
                top: 25%;
                left: 58%;
                width: 30px;
                height: 20px;
            }
            #makeJoinbuttonCenter{
            	width: 300px;
            	height: 50px;
         		text-align: center;
            }
            .forMargin{
            	margin-bottom: 10px;
            }
            input[type="text"]{
            border : none;
            border-bottom: 1px solid black;
            
            }
            input[type="password"]{
            border : none;
            border-bottom: 1px solid black;
            
            }
        </style>
    </head>
    <body>
        <div id="sidelogo"></div><!-- 수정 편의를 위해 이 그림도 잠깐 지웠음. style -->
           <!-- <img id="x" src="images/x.png"> -->
		<div id="jointitle" style="font-size: 40px;"><img  src="images/로고.png" / width=100% height=100%></div>
            <div id="join">
                <!--아이디 부분은 중복확인을 위해 따로 데이터전송을 해줘야하므로 그 부분은 추후에 추가-->
	            <div id="joinchild">
	               	아이디<br/>
	                <input type="text" placeholder="아이디" value="${checkingId}" name="id" style="width:190px; height:30px"/>
	                <input type="button" id="idCheck" value="중복확인" style="text-align:center; width:100px; height:30px"/><br/>
	                <div id="checkMessage"></div>
	               	비밀번호<br/>
	                <input type="password" placeholder="비밀번호(8~32자리)" name="pw" class="forMargin" style="width:298px; height:30px" /><br/>
	               	이름<br/>
	                <input type="text" placeholder="이름을 입력해주세요" name="name" class="forMargin" style="width:298px; height:30px"/><br/>
	               	전화번호<br/>
	                <input type="text" placeholder="10-0000-0000" name="phone" class="forMargin" style="width:298px; height:30px"/><br/>
	               	이메일<br/>
	                <input type="text" placeholder="이메일을 입력해주세요" name="email" style="width:298px; height:30px"/><br/> 
	                <div id="makeJoinbuttonCenter">
	                	<input id="joinbutton" type="button" value="회원가입">
	                </div>
                </div>
            </div>
        
    </body>
    <script>
    $(document).ready(function(){
    	var ability=false;
		$("#idCheck").click(function(){
			var id=$("input[name='id']").val();
			console.log(id);
			$.ajax({
				type:"get",
				url:"idCheck",
				data:{"id":id},
				dataType:"JSON",
				success:function(data){
					if(id==""){
						alert("아이디를 입력하세요.");
						$id.focus();
					}else if(data.idCheck){
						$("#checkMessage").html("사용 중인 아이디입니다.").css({"color":"red"});
						$("input[name='id']").val("");
						$("input[name='id']").focus();
					}else{
						$("#checkMessage").html("사용 가능한 아이디입니다.").css({"color":"green"});
						ability=true;
					}
				},
				error:function(e){
					console.log(e);
				}
			});
		});		
		
		$("#joinbutton").click(function(){
			if(ability){
				if($("input[name='pw']").val()==""){
					alert("비밀번호를 입력하세요.");
					$("input[name='pw']").focus();
				}else if($("input[name='name']").val()==""){
					alert("이름을 입력하세요.");
					$("input[name='name']").focus();
				}else if($("input[name='phone']").val()==""){
					alert("전화번호를 입력하세요.");
					$("input[name='phone']").focus();
				}else if($("input[name='email']").val()==""){
					alert("이메일을 입력하세요.");
					$("input[name='email']").focus();
				}else{
					var para={};
					para.id=$("input[name='id']").val();
					para.pw=$("input[name='pw']").val();
					para.name=$("input[name='name']").val();
					para.phone=$("input[name='phone']").val();
					para.email=$("input[name='email']").val();
					console.log("성공1");
					$.ajax({
						type: "post",
						url: "join",
						data: para,
						dataType: "JSON",
						success: function(data){
							if(data.join){
								console.log("성공2");
								alert("회원가입에 성공했습니다.");
								location.href="index.jsp";
							}else{
								alert("회원가입에 실패했습니다.");
							}
						},
						error: function(e){
							console.log(e);
						}
					});
				}
			}
		});
		
    });


    </script>
</html>