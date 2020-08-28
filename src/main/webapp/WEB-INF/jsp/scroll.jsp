<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>Bookmark!</title>
    <style type="text/css">
        .big-box {
            width: 100%;
            background-color: gray;
            height: 100vh;
            border-top: 1px solid black;
        }

        body {
            margin: 0px;
            padding: 0px;
        }
    </style>
</head>
<body>
    <div class="fav-aside">
        <div class="big-box"><h1>Page 1</h1></div>
        <div class="big-box"><h1>Page 2</h1></div>
    </div>
</body>
</html>
