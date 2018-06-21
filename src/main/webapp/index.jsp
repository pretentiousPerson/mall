<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

springMVC上传文件 
<form name = "form" action = "manage/product/upload.do" method = "post" enctype = "multipart/form-data">
	<input type="file" name="upload_file">
	<input value = "图片上传文件" type="submit">
</form>

</br></br>

富文本文件上传
<form name = "form2" action = "manage/product/richtext_img_upload.do" method = "post" enctype = "multipart/form-data">
	<input type="file" name="upload_file">
	<input value = "富文本图片上传文件" type="submit">
</form>
<br><br><br><br>
<form name="form3" action="manage/product/save.do" method = "post">
	商品类别：<input type="text" value=0 name="categoryId"><br>
	商品名称：<input type="text" value="朴宝英" name="name"><br>
	商品副标题：<input type="text" value="自然生动的演技和可爱单纯的风格深受大众喜欢" name="subtitle"><br>
	商品主图：<input type="text" value="http://www.837579301.cn/mall/product/4aada7d7-f55d-4a40-89da-4f85c608ca8e.jpg" name="mainImage"><br>
	商品子图：<input type="text" value="http://www.837579301.cn/mall/product/f2a28bf8-175f-466b-a8fe-8caf9c3fa69e.jpg" name="subImages"><br>
	商品详情：<input type="text" value="http://www.837579301.cn/mall/product/8269cb42-b8e6-41c3-a24a-6c150e697716.jpg" name="detail"><br>
	商品价格：<input type="text" value="4589" name="price"><br>
	商品库存：<input type="text" value="3" name="stock"><br>
	上架状态：<input type="text" value=1 name="status"><br>
	<input type="submit" value="提交">
</form>


</body>
</html>