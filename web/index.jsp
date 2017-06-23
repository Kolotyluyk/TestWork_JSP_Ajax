<%--
  Created by IntelliJ IDEA.
  User: Сергій
  Date: 12.06.2017
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="./codebase/dhtmlxgrid.js"></script>
    <link rel="stylesheet" href="./codebase/dhtmlxgrid.css">
  <script src="./loadData.js"></script>
</head>
<body onload="doOnLoad()">
<div>
    <div id="gridboxTeacher" style="width:500px; height:350px; background-color:white;"></div>
    <form id="addTeacher">
        <input type="text" id="teacherName" name="teacherName" placeholder="Name">
        <input id="addTeacherBtn" type="button"   onclick="addTeacher();">Add</input>
    </form>
</div>
<div id="gridboxLerner" style="width:500px; height:350px; background-color:white;"></div>
<br>

<form id="addLerner">
    <input type="text" name="lernerName" id="lernerName" placeholder="Name">
    <select name="teacher" id="teacher">
    </select>
    <input id="add" type="button"   onclick="addLerner();">Add</input>
</form>
   <p id="content" />
</body>
</html>
