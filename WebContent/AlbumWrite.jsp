<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>

    <head>
    	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	   	<meta http-equiv="Pragma" content="no-cache" />
	   	<meta http-equiv="Expires" content="0" />
        <meta charset="UTF-8">
        <title>01_start.html</title>
        
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <style>
            #gray{
                position: relative;
                width: 640px;
                height: 600px;
                background-color:black;
                opacity: 0.3;
                z-index: 1;
            }
            #upload{
                border-radius: 30px;
                position: absolute;
                background-color: white;
                width: 300px;
                height: 300px;
                top: 18%;
                left: 15%;
                z-index: 5;
            }
            #upload2{
                font-size: 17px;
                position: relative;
                height: 10%;
                padding-top: 10px;
                padding-left: 10px;

                
            }
            #submit{
                width: 200px;
                height: 50px;
            }
            .filebox{
                margin: 10px;
                text-align: center;
            }
            .filebox input{
                text-align: center;
                display: inline-block; 
                padding: .5em .75em; 
                color: #999; 
                font-size: inherit; 
                line-height: normal; 
                vertical-align: middle;
                background-color: #fdfdfd; 
                cursor: pointer; 
                border: 1px solid #ebebeb;
                border-bottom-color: #e2e2e2; 
                border-radius: .25em;
                width: 80%;
            }
            .filebox label{ 
                text-align: center;
                display: inline-block; 
                padding: .5em .75em; 
                color: #999; 
                font-size: inherit; 
                line-height: normal; 
                vertical-align: middle;
                background-color: #fdfdfd; 
                cursor: pointer; 
                border: 1px solid #ebebeb;
                border-bottom-color: #e2e2e2; 
                border-radius: .25em;
                width: 80%;
                } 

            .filebox input[type="file"]{
                position: absolute; 
                width: 1px; 
                height: 1px;
                padding: 0; 
                margin: -1px; 
                overflow: hidden; 
                clip:rect(0,0,0,0);
                border: 0; 
                }
         	 input{
         	 	outline: none;
         	 }
           
        </style>
    </head>
    <body>
        <div id="gray"></div><!-- 팝업뒷창 -->
        <div id="upload">
            <div>
                <div style="padding-top:5px; border-bottom: 1px solid black; font-size: 25px; text-align: center; padding: 10px 0px; width: 100%; height: 10%;" >게시물만들기</div> 
                <div id="upload2">
                    <img src=""/><!--주인프로필사진업로드-->
                    <td>ㅇㅇ</td><br/><!--주인이름-->
                </div>

        		<form action="albumupload" method="post" enctype="multipart/form-data">

        			<textarea style="width: 100%; height: 95px; border: none; resize: none; outline: none;" name="content" placeholder="ㅇㅇ님 무슨 생각을 하고계신가요?"></textarea>
                    <div class="filebox"> <label for="uploadFile">사진 가져오기</label> <input type="file" id="uploadFile" name="uploadFile"></div>
                    <div class="filebox"><input style="width: 88%; height: 13%;" type="submit" name="업로드" value="게시" /></div>
                </form>
            </div>
        </div>
        


    </body>
<script>






</script>
</html>