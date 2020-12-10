<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>

		
<html>
    <head>
    	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	   	<meta http-equiv="Pragma" content="no-cache" />
	   	<meta http-equiv="Expires" content="0" />
        <meta charset="UTF-8">
        <title>login</title> 
        <style>
            #helper{
            	width: 900px;
            	height: 500px;
            	margin-left: auto;
            	margin-right: auto;
            	margin-top: 150px;
            }
            #loginbox{
                position : relative;
                width: 400px;
                height: 600px;
                border: 1px solid black;
                float: left;
                text-align: center;
                border-radius: 30px;
                left : 27%;
            }
            #cyworld{
                margin-top: 10px;
                margin-bottom: 0px;
                text-align: center;
                position: relative;
                width: 100%;
                height: 40%;
            }

            #id{
                text-align: center;
                width: 100%;
                height: 25%;
            }
            #ps{
                text-align: center;
                width: 100%;
                height: 25%;
                background-color: chocolate;
            }
            #loginbutton{
                text-align: center;
                width: 100%;
                height: 20%;
            }
            #joining{
            	text-align: center;
            	margin-top: 15px;
            	width:300px; 
            	height:50px; 
            	background-color: black; 
            	color: white; 
            	border-radius: 15px;
            }

        </style>
    </head>
    <body>
    <div id="helper">
        
        <div id="loginbox">
        	<div id="cyworld"><a href="./"><img width="100%" height="100%" src="images/로고.png" alt=""></a></div>
        	<div id="login_texting">
	            <form action="login" method="post">
	                <div id="id">
	                    <input type="text" placeholder="Forest Friend계정(이메일 또는 전화번호)
	                    " name="id" style="width:80%; height:50px; border-right:0px; border-top:0px; border-left:0px;" /><br/><br/><br/>
	                    <input type="password" placeholder="비밀번호
	                    " name="pw" style="width:80%; height:50px; border-right:0px; border-top:0px; border-left:0px;"; /><br/>
	                </div>
	                <div id="loginbutton">
	                    <input type="submit" value="로그인" style="margin-top: 40px; width:300px; height:50px; background-color: black; color: white; border-radius: 15px;";><br/>
	                </div>
	            </form>
            </div>
            <a href="joinForm.jsp"><button id="joining">회원가입</button></a>
        </div>
    </div>
    </body>
    <script>
    </script>
</html>