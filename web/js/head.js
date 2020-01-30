//회원가입 버튼 클릭시
$(function () {
    $('#joinbtn').on('click', function (e) {
        location.href = '../join/agree.jsp';
    });
});

var link = document.location.href;
var secondLink = link.substring(link.lastIndexOf("/"),1);
var resultLink = secondLink.substring(secondLink.lastIndexOf("/")+1);
var index = document.getElementById("home");
var about = document.getElementById("about");
var hist = document.getElementById("hist1");
var hist2 = document.getElementById("hist2");
var gallery = document.getElementById("gallery");
var list = document.getElementById("list");
var intro = document.getElementById("intro");

var loginuid = document.getElementById('loginuid');
var pwd = document.getElementById('pwd');

if (resultLink == 'index') {
    index.className = "nav-link active";
}
else if (resultLink == 'about') {
    about.className = "nav-link active";
}
else if  (resultLink == 'hist1') {
    hist.className = "nav-link active";
}
else if (resultLink == 'dkdhist2') {
    hist2.className = "nav-link active";
}
else if (resultLink == 'gallery') {
    gallery.className = "nav-link active";
}
else if (resultLink == 'list') {
    list.className = "nav-link active";
    $('#navbar').css('marginTop','11%');
}
else if (resultLink == 'join') {
    $('#navbar').css('marginTop','11%');
}

else if (resultLink == 'intro') {
    intro.className = "nav-link active";
}
//시간 출력 변수
var showtime = document.getElementById('showTime');
var showdate = document.getElementById('showDate');
var showampm = document.getElementById('showAmpm');
var showsecond = document.getElementById('showSecond');
var header = document.getElementById("header");
var title = document.getElementById("title");
var ampm;
var week = ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'];
var day;
var hours;
var minutes;
var seconds;
var dayBg = '../../img/bg/bg_day.png';
var dayTitle = '../../img/bg/title_day.png';
var nightBg = '../../img/bg/bg_night.png';
var nightTitle = '../../img/bg/title_night.png';


//시간 관련 함수
function clockInfo() {
    var date = new Date;
    minutes = date.getMinutes();
    seconds = date.getSeconds();
    //오전 오후. 시간도 계산
    if (date.getHours() > 12) {
        hours = date.getHours() - 12;
        ampm = 'PM';
    }
    else {
        ampm = 'AM';
        hours = date.getHours();
    }
    //요일을 배열대로 매핑
    day = week[date.getDay()];

    //자리수 맞추기
    if (hours < 10) {
        hours = '0' + hours;
    }
    if (minutes < 10) {
        minutes = '0' + minutes;
    }
    if (seconds < 10) {
        seconds = '0' + seconds;
    }

    if ((hours >= 6 && ampm == 'AM') || (hours < 6 && ampm == 'PM')) {
        header.style.backgroundImage = "url(" + dayBg + ")";
        title.innerHTML = "<a href='../index/index.jsp'><img style=\"height: auto; width: 15vw\" src='" + dayTitle + "'></a>"
    }
    else {
        header.style.backgroundImage = "url(" + nightBg + ")";
        title.innerHTML = "<a href='../index/index.jsp'><img style=\"height: auto; width: 15vw\" src='" + nightTitle + "'></a>"
    }

    var dates = date.getFullYear() + "년 " + (date.getMonth()+1) + "월 " + date.getDate() + "일 " + day;
    var time = "<a style='font-size: 50px; font-weight: lighter'>" + hours + "<small>&colon;</small>" + minutes + "<small>&colon;</small>" +  seconds  + "</a>" + '<a>' + ampm +  '</a>' + '<br>' + '<a>' + dates + '</a>';

    showtime.innerHTML = time;
}

//실행되도록...
function init() {
    clockInfo();
    setInterval(clockInfo,1000);
}

init();

//로그인 처리 함수
function loginbtn() {
    var msg = document.getElementById('msg');

    if (loginuid.value == null || loginuid.value == "") {
        msg.innerHTML = '아이디를 입력하십시오.';
        loginuid.focus();
    }
    else if (pwd.value == null || pwd.value =="") {
        msg.innerHTML = '비밀번호를 입력하십시오.';
        pwd.focus();
    }

    else {
        $.ajax({
            async:true,
            type: 'POST',
            data:{"uid":loginuid.value, "pwd":pwd.value},
            url : "http://localhost:8080/loginServlet",
            success : function (data) {
                if (data == "success") {
                    location.reload();
                }
                else {
                    msg.innerHTML = '없는 아이디거나 비밀번호가 일치하지 않습니다.';
                    loginuid.value = "";
                    pwd.value = "";
                    loginuid.focus();
                }
            },
            error : function () {
                msg.innerHTML = '로그인 도중 오류가 발생했습니다.'
            }
        })
    }
}

function logoutbtn() {
    $.ajax({
        async:true,
        type: 'POST',
        url: "http://localhost:8080/logoutServlet",
        success : function (data) {
            alert('로그아웃되었습니다.');
            location.reload();
        },
        error : function () {
            alert('로그아웃 도중 오류가 발생하였습니다.')
        }
    })
}

function memberInfo() {
    location.href = '../info/info.jsp'
}