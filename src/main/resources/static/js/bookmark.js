'use strict'
let endDate="";

let currentCount = 0;
let category=0;
let selectedIndustry = [];
let sameAddressFlag=false;
let recentBookmark = [0,0,0];
// 대용량
let billShipSameRegisterFlag = false;
(function(){
    $('.js-fav-industry').click(function(ev){
        //event bubbling cancellation
        ev.stopPropagation();
        //여기서 this는 event가 발생한 곳의 DOM
        //그래서 attr에 'data-industry'로 접근할 수 있다.
        var industryType = $(this).attr('data-industry');
        // 만약 이것이 :checked로 check가 되었다면
        if ($(this).is(':checked')) {
            selectedIndustry.push(industryType);
        }
        else{
            //클릭을 했는데 :checked가 아니라면 이는 취소이니
            //해당 industryType을 selectedIndustry에서 찾아서
            //삭제해준다. 이는 IndexOf로 찾아서
            //slice(idx,1)로 해당 index에서 1를 지운다는 것이다.
            let idx = selectedIndustry.indexOf(industryType);
            if(idx>-1){
                selectedIndustry.slice(idx,1);
            }
        }
    });
    let temp = "hoho";
    let tempNumber = Number(temp);
    console.log(tempNumber);
    if(tempNumber==="NaN"){
        console.log("zzz0");
    }
    if(isNaN(temp)){
        console.log("zzz01213");
    }
    bookmark(0);
    $("#lb_country").click(function(){
        if(document.getElementById('lb_country').checked){
            $("#error").css('display','inline');
        }
        else{
            $("#error").css('display','none');
        }
    });
})();
function download_1(){
    // var element = document.createElement('a');
    // var text = "hoho hihi!";
    // element.setAttribute('href','data:text/plain;charset=utf-8,'+encodeURIComponent(text));
    // element.setAttribute('download','hoho.txt');
    // element.style.display = 'none';
    // document.body.appendChild(element);
    // element.click();
    // document.body.removeChild(element);
    var content = "hohohoho!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
    var filename = "hoho.txt";
    var blob = new Blob([content],{type:"text/plain;charset=utf-8"});
    saveAs(blob,filename);
}
function copy_function(){
     // document.getElementById('lb_country').click(function(){
     //     $("#country_copy").val($("#country").val());
     //        console.log('hihi');
     // });
    $("#error").css("style","background-color:red");
    $("#error").css('display','none');
    document.getElementById('country').addEventListener('focus',function(ev){
        ev.stopPropagation();
        console.log("focus");
    });
    document.getElementById('country').addEventListener('blur',function(ev){
        ev.stopPropagation();
        console.log("blur");
    });
    document.getElementById('lb_country').addEventListener('click',function(){
        let val = $("#country").val();
        $("#country_copy").val(val);
        val = $("#select_country").val();
        console.log("select country "+val);
        $("#select_country_1").val(val);
    });
    $("#select_country").click(function(){
        if($("#lb_country").is(":checked")){
            let val = $(this).val();
            console.log(val);
            $("#select_country_1").val(val);
        }
    });

    let opt = document.createElement("option");
    opt.setAttribute('value','4');
    opt.setAttribute('val','United States');
    opt.textContent = 'United States';
    document.getElementById("select_country").append(opt);

    let targetCountry = document.getElementById('select_country').options[document.getElementById('select_country').selectedIndex].getAttribute('val');
    console.log(targetCountry);
    // $("#lb_country").click(function(){
    //     $("#country_copy").val($("#country").val());
    // });
    $("#country").keyup(function(){
        if(document.getElementById('lb_country').checked){
            $("#country_copy").val($(this).val());
        }
    }).keyup();
}
function bookmark(num){
    initBookmark();
    category = num;
    endDate = ""
    document.getElementsByClassName("js-go-favweek")[category].setAttribute("class"
        ,"js-go-favweek active");

    $("#lst_pdt").remove();
    let div = document.getElementsByClassName("product_wrap")[0];

    let ul = document.createElement("ul");
    ul.setAttribute("class","lst_pdt fav_style js-favitem-list");
    ul.setAttribute("id","lst_pdt");
    div.appendChild(ul);
    addImage();
    showCount();

    $(window).scroll(function() {
        if($(this).scrollTop() > 300) {
            $('#scroll-top').fadeIn();
        } else {
            $('#scroll-top').fadeOut();
        }

        if ($(window).scrollTop() >= $(document).height() - $(window).height()-10) {
            addImage();
        }
    });
    $('#scroll-top').click(function(e) {
        $('html, body').stop().animate({scrollTop : 0}, 100);
        return false;
    });
}
function initBookmark(){
    $.ajax({
        url: "/Favorite/bookmarks/init",
        type: 'get',
        async: false,
        contentType: 'application/json; charset=UTF-8',
        success: function (data) {
            recentBookmark[0]= data.data[0];
            recentBookmark[1]= data.data[1];
            recentBookmark[2]= data.data[2];
        }
    });
}
function showNoBookmark(){
    let ul = document.getElementById("lst_pdt");
    let div = document.createElement("div");
    div.setAttribute("class","empty-page style bookmark_emp");
    let div0 = document.createElement("div");
    div0.setAttribute("class","empty-page__icon");
    let div1 = document.createElement("div");
    div1.setAttribute("class","empty-page__title");
    div1.textContent = 'No Items ';

    let temp = document.createElement("span");
    temp.setAttribute("style","color:#FF0000;font-weight:bold");
    temp.textContent = "Saved";
    div1.appendChild(temp);

    let div2 = document.createElement("div");
    div2.setAttribute("class","empty-page__desc");
    div2.textContent = "You have no recently ";
    temp = document.createElement("span");
    temp.setAttribute("style","color:#FF0000");
    temp.textContent = "saved";
    div2.appendChild(temp);

    temp = document.createElement("span");
    temp.setAttribute("style","");
    temp.textContent = " items";
    div2.appendChild(temp);

    div.appendChild(div0);
    div.appendChild(div1);
    div.appendChild(div2);
    ul.appendChild(div);

}

function addImage(){
    let uri = "/Favorite/getbookmarks";
    if(endDate!==""){
        uri+="?endDate="+endDate;
    }
    if(endDate===""){
        uri+="?";
    }
    else{
        uri+="&";
    }
   uri+="category="+category;
    console.log(endDate);
    $.ajax({
        url:uri,
        type :'get',
        async: false,
        contentType: 'application/json; charset=UTF-8',
        beforeSend :function(xhr){
            xhr.setRequestHeader("hoho","hoho");
            xhr.setRequestHeader("hehe","hehe");
        },
        success:function(data){
            let body = data.data;
            let totalCount = body.contents.length;
            if(totalCount===0){

                return;
            }
            currentCount += totalCount;

            endDate = body.contents[totalCount-1].createdOn;
            let ul = document.getElementById("lst_pdt");
            for(let i=0,len = body.contents.length;i<len;i++){

                let div_pic = document.createElement("div");
                let li =document.createElement("li");
                li.setAttribute("id","bookmarkId"+body.contents[i].bookmarkId);
                li.setAttribute("created-on",body.contents[i].createdOn);
                div_pic.setAttribute("class","pic");

                let src = document.createElement("img");
                src.setAttribute("src",body.contents[i].thumbnailUrl);
                src.setAttribute("onerror","this.src='" + body.contents[i].originalImageUrl + "'");
                src.setAttribute("created-on",body.contents[i].createdOn);
                src.setAttribute("class","nclick");
                src.setAttribute("nclick-name","site.right.fitem.item");
                src.setAttribute("nclick-id",12150047);
                let imgfile = body.contents[i].originalImageUrl.split('/');
                src.setAttribute("alt",imgfile[imgfile.length-1]);
                div_pic.appendChild(src);

                let d_info = document.createElement("ul");
                d_info.setAttribute("class","d_info");
                let d_search = document.createElement("li");
                d_search.setAttribute("class","d_search");
                let d_search_atag = document.createElement("a");

                d_search_atag.setAttribute("class","cls-pview");
                d_search_atag.setAttribute("href",body.searchURLs[i]);
                d_search_atag.setAttribute("target","_new");
                d_search.appendChild(d_search_atag);
                d_info.appendChild(d_search);


                let d_dele = document.createElement("li");
                d_dele.setAttribute("class","d_dele");
                let d_dele_atag = document.createElement("a");


                d_dele_atag.setAttribute("class","cls-pview");
                d_dele_atag.setAttribute("value",body.contents[i].bookmarkId);
                d_dele_atag.textContent="dd";
                d_dele_atag.setAttribute("style","cursor:pointer");

                d_dele_atag.setAttribute("href","javascript:void(0);");
                d_dele.appendChild(d_dele_atag);
                d_info.appendChild(d_dele);

                div_pic.appendChild(d_info);
                li.appendChild(div_pic);
                ul.appendChild(li);

            }
            $(".d_dele > a").off().click(function(ev){
                ev.preventDefault();
                let bid = $(this).attr('value');
                deleteImage(bid);
            })

        },

    });

}
function showCount(){

    if(category==0){
        $("#js-favgroup-title").html("Today");
    }
    else if(category==1){
        $("#js-favgroup-title").html("Yesterday");
    }
    else{
        $("#js-favgroup-title").html("Last 7 days");
    }
    $("#js-favgroup-count").html("("+recentBookmark[category].toString()+")");
    currentCount = recentBookmark[category];
    $('#todayCount').html("(" + recentBookmark[0].toString() + ")");
    $('#yesterdayCount').html("(" + recentBookmark[1].toString() + ")");
    $('#last7daysCount').html("(" + recentBookmark[2].toString() + ")");
    if(recentBookmark[category]==0){
        showNoBookmark();
    }
}
function deleteImage(id){

    let todayValue = moment($("#todayValue").val());
    let created_on = moment($("#bookmarkId"+id).attr("created-on"));
    let diff = 0;
    console.log(1);
    if(created_on.diff(todayValue)>0){
        diff = 0;
    }
    else if(created_on.diff(todayValue.subtract(1,'days'))>0){
        diff = 1;
    }
    else{
        diff = 2;
    }
    $.ajax({
        url: "/Favorite/bookmarks/"+id,
        type: 'delete',
        contentType: 'application/json; charset=UTF-8',

        success:function(data){
            console.log(data);
            if(data.success === true){

                $("#bookmarkId"+id).remove();
                recentBookmark[diff]-=1;
                if(diff!=2)recentBookmark[2]-=1;
                if(recentBookmark[category]==0){
                    addImage();
                }
                showCount();

            }
        },
        beforeSend:function(){
            console.log("before");
            $(".d_dele a").attr('disabled',true);
            $("#delete-loading").removeClass('display-none');
        },
        complete:function(){
            $("#delete-loading").addClass('display-none');
        },

    });
}
function fileupload(){
    FileList = $('#attachment-file')[0].files;
    for (let i = 0; i < FileList.length; i++) {
        if (FileList[i].size > 10 * 1000 * 1000) {
            alert('일반파일은 최대 10MB까지 첨부 가능합니다.');
            return;
        }
    }
    console.log(FileList);
    const formData = new FormData();
    for(let i=0;i<FileList.length;i++){
        formData.append('file', FileList[i]);
    }
    $.ajax({
        url: '/fileupload',
        type: 'post',
        data: formData,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        cache: false,
        beforeSend:function(){
            $("#delete-loading").show();
        },
        complete:function(){
            $("#delete-loading").hide();
        },
        // eslint-disable-next-line no-loop-func
        success(data) {
            if (data.success === true) {

            } else {
                alert(data.message);
            }
        }

    });
}
