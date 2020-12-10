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
            	padding: 10px;
            }
            #chart tr td{
            	background-color: #ebecf1;
            	border-radius: 5px;
            }
            #confirm{
				margin-left: 750px;
            }
        </style>
    </head>
    <body>
        <div id="profile">
            <form action="profileUpdate" method="post">
                <div id="chartChan">
                <table id="chart">
			        <tr><td class="tdMenu">이름</td><td><input type="text" name="nickname" value="${Profile.nickname}" style="width:250px; height:30px"></td></tr>
			        <tr><td class="tdMenu">생일</td><td><input type="text"  name="myBirth" value="${Profile.myBirth}" style="width:250px; height:30px"></td></tr>
			        <tr><td class="tdMenu">혈액형</td><td><input type="text" name="blood" value="${Profile.blood}" style="width:250px; height:30px"></td></tr>
			        <tr><td class="tdMenu">주소</td><td><input type="text" name="addr" value="${Profile.addr}" style="width:450px; height:30px"></td></tr>
			        <tr><td class="tdMenu">전공</td><td><input type="text" name="major" value="${Profile.major}" style="width:450px; height:30px"></td></tr>
			        <tr><td class="tdMenu">나만의 이성유혹법</td><td><input type="text" name="seduWay" value="${Profile.seduWay}" style="width:450px; height:30px"></td></tr>
			        <tr><td class="tdMenu">좌우명</td><td><input type="text" name="motto" value="${Profile.motto}" style="width:450px; height:30px"></td></tr>
			        <tr><td class="tdMenu">인생영화</td><td><input type="text" name="fMovie" value="${Profile.fMovie}" style="width:450px; height:30px"></td></tr>
		        </table>
		        <!--
                이름:&nbsp;<input type="text" name="nickname" value="${Profile.nickname}" style="width:190px; height:30px"><br/>
                생일:&nbsp;<input type="text"  name="myBirth" value="${Profile.myBirth}" style="width:190px; height:30px"><br/>
                혈액형:&nbsp;<input type="text" name="blood" value="${Profile.blood}" style="width:165px; height:30px"><br/>
                주소:&nbsp;<input type="text" name="addr" value="${Profile.addr}" style="width:190px; height:30px"><br/>
            
                전공:&nbsp;<input type="text" name="major" value="${Profile.major}" style="width:190px; height:30px"><br/>
                나만의 이성유혹법:&nbsp;<input type="text" name="seduWay" value="${Profile.seduWay}" style="width:335px; height:30px"><br/>
                좌우명:&nbsp;<input type="text" name="motto" value="${Profile.motto}" style="width:255px; height:30px"><br/>
                인생영화:&nbsp;<input type="text" name="fMovie" value="${Profile.fMovie}" style="width:230px; height:30px"><br/>

                -->
                </div>
            	<input type="hidden" name="homephost" value="${Profile.id}">
                <input id="confirm" type="submit" value="OK!" />
            </form>
        </div>
    </body>
    <script>
        
    </script>
</html>