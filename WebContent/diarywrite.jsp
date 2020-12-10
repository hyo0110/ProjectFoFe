<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	
		html{
            background-color: #fff591;
            }
		.write{
				position: absolute;
			    top: 68px;
			    left: 244px;
                width: 600px;
                height: 590px;
                margin: 0px;
                border-radius: 10px;
                background-color:white;
                box-shadow: 0px 22px 31px 0px #dbd15c;
            }
            table{
                font-size: 20pt;
                text-align: center;
                width: 100%;
                height: 87%;
                margin-left: auto;
                margin-right: auto;
                margin-bottom: 4%;
                padding: 0px;
            }
            tr{
                border: solid 1px black; 
            }
            .a1{height: 20%;}
            .a2{height: 80%;}
            .complete{text-align: right; }
            
            form{
            	width:570px;
            	height:600px;
            	
            }
            #subject{
            position:absolute;
            top: 20px;
   			left: 38px;
            width:10px;
            height:10px
            }
            
            #content{
            position:absolute;
            top: 85px;
   			left: 38px;
            width:10px;
            height:10px
            }
	</style>
</head>
<body>
	<div class="write">
		<form action="diaryWrite" method="post">
	            <table>
	                <tr class="a1">
	                    <td style="width: 90%;">
	                        <input type="text" id="subject" name="subject" style="width: 86%;height: 8%; font-size: 20pt;" placeholder="제목을 입력해 주세요.">
	                    </td>
	                </tr>
	                <tr class="a2">
	                    <td>
	                        <textarea id="content" name="content" style="width: 86%; height: 74%; resize: none;" placeholder="내용을 입력해 주세요"></textarea>
	                    </td>
	                </tr>
	            </table>
            
	            <div class="complete">
	            	<input type="hidden" name="homephost" value="${homephost}"/>
	                <input type="submit" value="완료" style="width: 70px; height: 30px; margin-right: 30px; position: absolute; top: 537px; left: 490px;">
	            </div>
            </form>
     </div>
</body>
<script></script>
</html>