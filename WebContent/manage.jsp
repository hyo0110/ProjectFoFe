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
        <title></title>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <style>
	        	body{
	        	margin: 50px;
				
	        	}
        	#color{
				box-sizing: border-box;
				width: 580px;
				height: 200px;
				margin-left: auto;
				margin-right: auto;
				border: 1px solid gray;
				margin-bottom: 5px;
				border-radius: 30px;
			}
			p{
				font-size: 20px;
				font-weight: 800;
				margin: 5px 0 0 30px;
				clear: left;
				margin: 20px;
				margin-left: 210px;
			}
			.color{
				font-size: 20px;
				margin: 12px;
			}
			#choice{
				width: 289px;
				height: 200px;
				float: left;			
				
			}
			#preveal{
				box-sizing: border-box;
				width: 289px;
				height: 198px;
				padding: 20px;
				float: left;
				background-color: white;
				border-radius: 30px;
			}
			#manage_photo{
				box-sizing: border-box;
				width: 580px;
				height: 220px;
				margin-left: auto;
				margin-right: auto;
			}
			.manage_profile{
				width: 284px;
				height: 200px;
				border: 1px solid gray;
				float: left;
				margin: 2px;
				padding-top: 10px;
				text-align: center;
			}
			#manage_profile_preveal{
				width: 150px;
				height: 130px;
				border: 1px solid gray;
				margin-left: auto;
				margin-right: auto;
			}
			#manage_gate_preveal{
				width: 200px;
				height: 130px;
				border: 1px solid gray;
				margin-left: auto;
				margin-right: auto;
			}
            .music{
                box-sizing: border-box;
				width: 580px;
				height: 50px;
				padding: 10px;
				margin-left: auto;
				margin-right: auto;
				border: 1px solid gray;
				text-align: center;
				border-radius: 20px;
            }
            #bgupload{
            	text-align: center;
            	margin-left: 116px;
}
            }
               
        </style>
    </head>
    <body>
        <p>배경색 변경</p>
		<div id="color">			
			<div id="choice">
				<form action="backgrounduplolad" method="POST">
                        <div class="color"><label><input name="background" type="radio" value="white"/>우유</label></div>
                        <div class="color"><label><input name="background" type="radio" value="#fff591"/>바나나 우유</label></div>
                        <div class="color"><label><input name="background" type="radio" value="#ffaaa5"/>딸기 우유</label></div>
                        <div class="color"><label><input name="background" type="radio" value="#e0f9b5"/>메론맛 우유</label></div>
                        <input id="bgupload" type="submit" value="업로드"/>
                    </form>
			</div>
			<div id="preveal">
			</div>
		</div>
		<p>프로필 변경</p>
        <div class="music">
             <form action="profileUpload" method="POST" enctype="multipart/form-data">
                 <input id ="teen" type="file" name="profile"/>
                 <input type="submit" value="업로드"/>
             </form>
        </div>
        <p>대문사진 변경</p>
        <div class="music">
             
             <form action="mainPhotoUpload" method="POST" enctype="multipart/form-data">
                 <input type="file" name="mainPhoto"/>
                 <input type="submit" value="업로드"/>
             </form>
        </div>
		
		<p>BGM 변경</p>
        <div class="music">
             <form action="BgmUpload" method="POST" enctype="multipart/form-data">
                 <input type="file" name="BGM"/>
                 <input type="submit" value="업로드"/>
             </form>
        </div>
        
        
        
    </body>
    <script>
	    $(document).ready(function(){});
		$("input[type='radio']").click(function(){
			$("#preveal").css({"background-color":$(this).val()});//미리보기 색상 변경
		});
		var msg ="${msg}"
		
		var newfilename = "${newfilename}"
		if(msg!=""){
			alert("업로드 성공~!");
			parent.document.location.reload();
		}
    </script>
</html>