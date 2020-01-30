var uid = document.getElementById('uid');
var pwd2 = document.getElementById('pwd2');
var pwd2b = document.getElementById('pwd2b');
var psname = document.getElementById('psname');
var email = document.getElementById('email');
var statecity = document.getElementById('statecity');
var address1 = document.getElementById('address1');
var contact2 = document.getElementById('contact2');
var idchk = document.getElementById('idchk');
var checkbtn = document.getElementById('checkbtn');
var cap = document.getElementById('cap');
var capbtn = document.getElementById('capbtn');
var checkcap = document.getElementById('checkcap');

$(function () {
    $('#newjoinbtn').on('click', function (e) {
        var isID = false;
        var isNull = false;
        var isPwd2 = false;
        var isEmail = false;
        var isContact = false;
        var isCap = false;

        // 입력 칸 null 혹은 빈 공간 확인
        if (pwd2.value == null || pwd2.value == "") {
            alert('사용할 비밀번호를 입력하십시오.');
            pwd2.focus();
            pwd2b = "";
            return false;
        }
        else if (pwd2b.value == null || pwd2b.value == "") {
            alert('비밀번호 확인 란이 비어 있습니다.');
            pwd2b.focus();
            pwd2 = "";
            return false;
        }
        else if (psname.value == null || psname.value == "") {
            alert('이름은 필수 입력 항목입니다.');
            psname.focus();
            return false;
        }
        else if (email.value == null || email.value == "") {
            alert('이메일은 필수 입력 항목입니다.');
            email.focus();
            return false;
        }
        else if (statecity.value == null || statecity.value == "") {
            alert('거주 중이신 주/ 도시를 입력하십시오.');
            statecity.focus();
            return false;
        }
        else if (address1.value == null || address1.value == "") {
            alert('주소는 필수 입력 항목입니다.');
            address1.focus();
            return false;
        }
        else if (contact2.value == null || contact2.value == "") {
            alert('연락처는 필수 입력 항목입니다.');
            contact2.focus();
            return false;
        }
        else {
            isNull = true;
        }


        //아이디 중복 체크 여부
        if (isNull) {
            if (idchk.value == 'N') {
                alert('아이디 중복검사를 하지 않았습니다.');
                uid.focus();
                return false;
            }
            else if (idchk.value == 'Y'){
                isID = true;
            }
            else {
                alert('예상치 못한 오류입니다.')
            }
        }
        else {
            return false;
        }

        //비밀번호 유효성 검사
        if(isID) {
            var ispass = /^{8,16}/g;
            if(ispass.test(pwd2.value) == false) {
                alert('해당 비밀번호는 사용할 수 없습니다.');
                pwd2.value = "";
                pwd2b.value = "";
                pwd2.focus();
            }
            else if (pwd2.value != pwd2b.value){
                alert('비밀번호 확인 란에 입력하신 비밀번호가 다릅니다.');
                pwd2.value = "";
                pwd2b.value = "";
                pwd2.focus();
            }
            else {
                isPwd2 = true;
            }
        }
        else {
            return false;
        }

        //이메일 유효성 검사
        if(isPwd2) {
            var isEre = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            if (!isEre.test(email.value)) {
                alert('올바른 이메일 형식이 아닙니다.');
                email.value = "";
                email.focus();
            }
            else {
                isEmail = true;
            }
        }
        else {
            return false;
        }

        //전화번호 유효성 검사
        if(isEmail) {
            var Cre = /\d{2,4}-\d{3,4}-\d{3,4}/;
            if(!Cre.test(contact2.value)) {
                alert('유효하지 않은 전화번호입니다.');
                contact2.value = "";
                contact2.focus();
            }
            else {
                isContact = true;
            }
        }
        else {
            return false;
        }

        //캡챠 여부
        if(isContact) {
            if (checkcap.value == 'N') {
                alert('자동 가입 방지 코드를 입력하지 않았습니다.');
                cap.focus();
                return false;
            }
            else if (checkcap.value == 'Y') {
                isCap = true;
            }
            else {
                alert('예상치 못한 오류입니다.')
                return false;
            }
        }
        else {
            return false;
        }

        //모든 유효성 검사를 마쳤을 때
        if(isCap) {
            return true;
        }
    });
});

function checkid() {
    var idReg = /^[a-z0-9_]{6,16}/g;
    if (uid.value == null || uid.value == "") {
        alert("아이디를 입력하지 않았습니다.");
        uid.focus();
        return false;
    } else if (!idReg.test(uid.value)){
        alert('해당 아이디는 사용할 수 없습니다.');
        uid.value = "";
        uid.focus();
        return false;
    } else {
        $.ajax({
            async:true,
            type: 'POST',
            data : {"uid":uid.value},
            url : "http://localhost:8080/checkIDServlet",
            success : function (data) {
                if(data == "success") {
                    alert('가입가능한 아이디입니다.');
                    idchk.value = 'Y';
                    uid.readOnly = true;
                    checkbtn.disabled = true;
                }
                else {
                    alert('이미 가입되어 있는 아이디입니다.');
                    idchk.value = 'N'
                }
            },
            error : function () {
                alert('아이디 중복 검사 도중 오류가 발생했습니다.');
            }
        })
    }
}

function checkCaptcha() {
    if (cap.value == "" || cap.value == null) {
        alert('자동 가입 방지 코드를 입력하지 않았습니다.');
        cap.focus();
        return false;
    } else {
        $.ajax({
            async:true,
            type: 'POST',
            data: {"cap": cap.value},
            url : "http://localhost:8080/checkCapServlet",
            success : function (data) {
                if(data == "success") {
                    alert('인증이 완료되었습니다.');
                    cap.value = "";
                    cap.disabled = true;
                    capbtn.disabled = true;
                    checkcap.value = "Y"
                } else {
                    alert('자동 가입 방지 코드가 틀립니다.');
                    cap.value = "";
                    checkcap.value = "N"
                }
            },
            error : function () {
                alert('인증 도중 오류가 발생했습니다.')
            }
        })
    }
}

