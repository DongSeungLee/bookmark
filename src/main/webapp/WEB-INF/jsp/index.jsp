<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>Bookmark!</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.0.0.min.js"></script>
<%--    <link rel="stylesheet" href = "/static/css/bookmark.css"/>--%>
    <link rel="stylesheet" href = "/static/css/checkout.css"/>
</head>
<body>

<div class="fav-aside">
    <div>
        <input type="button" id="download_btn"onclick="download_1()"value="download">
    </div>
    <div>
        <input type="checkbox"id="lb_country"/>
        <label >
            check if you want the same value to be inserted!
        </label>
        <label id="error" style="background-color:red">
            error!
        </label>
    </div>

    <div id="option_div">
        <select id="select_country">
            <option value="0" selected>Selected Country</option>
            <option value="1" val="Korea">Korea</option>
            <option value="2" val="Japan">Japan</option>
            <option value="3" val="China">China</option>
        </select>
        <select id="select_country_1">
            <option value="0" selected>Selected Country</option>
            <option value="1" val="Korea">Korea</option>
            <option value="2" val="Japan">Japan</option>
            <option value="3" val="China">China</option>
        </select>

    </div>
    <div id="country_div">
        <input id="country" type="text" value="hoho"/>
        <input id="country_copy" type="text"/>
    </div>
    <div class="aside-filter__body">
        <ul>
            <li>
                <div class="blue-checkbox">
                    <label>
                        <input type="checkbox" class="js-fav-industry" data-industry="Women's Clothing" />
                        <div class="check-square">
                            <div class="check-inner-square"></div>
                        </div>
                        <div class="label">Women</div>
                    </label>
                </div>
            </li>
            <li>
                <div class="blue-checkbox">
                    <label>
                        <input type="checkbox" class="js-fav-industry" data-industry="Shoes" />
                        <div class="check-square">
                            <div class="check-inner-square"></div>
                        </div>
                        <div class="label">Shoes</div>
                    </label>
                </div>
            </li>
            <li>
                <div class="blue-checkbox">
                    <label>
                        <input type="checkbox" class="js-fav-industry" data-industry="Accessories" />
                        <div class="check-square">
                            <div class="check-inner-square"></div>
                        </div>
                        <div class="label">Accessories</div>
                    </label>
                </div>
            </li>
            <li>
                <div class="blue-checkbox">
                    <label>
                        <input type="checkbox" class="js-fav-industry" data-industry="Handbag" />
                        <div class="check-square">
                            <div class="check-inner-square"></div>
                        </div>
                        <div class="label">Handbags</div>
                    </label>
                </div>
            </li>
            <li>
                <div class="blue-checkbox">
                    <label>
                        <input type="checkbox" class="js-fav-industry" data-industry="Man's Clothing" />
                        <div class="check-square">
                            <div class="check-inner-square"></div>
                        </div>
                        <div class="label">Men</div>
                    </label>
                </div>
            </li>
            <li>
                <div class="blue-checkbox">
                    <label>
                        <input type="checkbox" class="js-fav-industry" data-industry="Children's Clothing" />
                        <div class="check-square">
                            <div class="check-inner-square"></div>
                        </div>
                        <div class="label">Kids</div>
                    </label>
                </div>
            </li>
            <li>
                <div class="blue-checkbox">
                    <label>
                        <input type="checkbox" class="js-fav-industry" data-industry="Beauty" />
                        <div class="check-square">
                            <div class="check-inner-square"></div>
                        </div>
                        <div class="label">Beauty</div>
                    </label>
                </div>
            </li>
            <li>
                <div class="blue-checkbox">
                    <label>
                        <input type="checkbox" class="js-fav-industry" data-industry="Others" />
                        <div class="check-square">
                            <div class="check-inner-square"></div>
                        </div>
                        <div class="label">Others</div>
                    </label>
                </div>
            </li>
        </ul>
    </div>


    <div class="expansion"style="display:none">
        <ul class="expansion-ul">
            <!-- 오늘 bookmark 갯수 알려주기-->
            <li><a style="cursor:pointer"onclick="bookmark(0)"class="js-go-favweek" data-week="0" data-title="Today"data-count="1">Today  <span class="count" >(5)</span></a></li>
            <li><a style="cursor:pointer"onclick="bookmark(1)"class="js-go-favweek" data-week="1" data-title="Yesterday"data-count="0">Yesterday  <span class="count">(0)</span></a></li>
            <li><a style="cursor:pointer"onclick="bookmark(2)" class="js-go-favweek" data-week="2" data-title="Last 7 days"data-count="1">Last 7 days <span class="count">(12)</span></a></li>
        </ul>
    </div>

    <h2>bookmark</h2>
    <input type="hidden"id="bookmarkcategory"value="${category}"/>
    <input type="hidden"id="todayValue"value="${todayValue}"/>
    <div class="fav-aside recent_bookmarks">
        <div class="banner_style-match">
            <p class="question_txt">What is Style Match+ ?</p>
            <a href="#" class="learn_more">LEARN MORE</a>
        </div>
        <!-- ========== ↑↑↑↑↑↑↑↑↑↑ 2020.05.15 nhnst-uit add to ↑↑↑↑↑↑↑↑↑↑ ========== -->
        <h2>SAVED IMAGES</h2>

        <div class="js-side-favdate-list">
            <ul class="fav-aside__weekly">
                <!-- 오늘 bookmark 갯수 알려주기-->
                <li><a style="cursor:pointer"onclick="bookmark(0)"class="js-go-favweek" data-week="0" data-title="Today"data-count="1">Today  <span class="count" id="todayCount">(5)</span></a></li>
                <li><a style="cursor:pointer"onclick="bookmark(1)"class="js-go-favweek" data-week="1" data-title="Yesterday"data-count="0">Yesterday  <span class="count"id="yesterdayCount">(0)</span></a></li>
                <li><a style="cursor:pointer"onclick="bookmark(2)" class="js-go-favweek" data-week="2" data-title="Last 7 days"data-count="1">Last 7 days <span class="count"id="last7daysCount">(12)</span></a></li>
            </ul>
        </div>
    </div>
    <div class="cont_wrap bookmarks_wrap">
        <div class="fav-cont">
            <div class = "title-notice">
                <h1 class="page-title"><span class="js-favgroup-title"id="js-favgroup-title">Today</span>
                    <span class="cnt js-favgroup-count"id="js-favgroup-count"></span>
                </h1>
                <div class="bookmark_notice" style="color:red">Bookmarked image will be deleted after 7 days from the day you saved.</div>
            </div>

            <div id="delete-loading" class="delete-loading display-none" >
                <div class="delete-loading-div">
                    <img src="/static/img/delete-loading.gif"/>
                </div>
            </div>
            <div class="product_wrap">
                <ul class="lst_pdt fav_style js-favitem-list" id="lst_pdt">

                </ul>
            </div>


        </div>
    </div>
    <!-- go top btn -->
    <div id="scroll-top" class="go-top">
        <div class="go-top__container">
            <span class="go-top__container__icon"></span>
            <span>Top</span>
        </div>
    </div>
</div>



<script src="/static/js/fileSaver.js"></script>
<script src="/static/js/moment.js"></script>
<script src="/static/js/bookmark.js"></script>
<script>
    copy_function();
</script>
</body>
</html>
