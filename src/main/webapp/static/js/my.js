$(document).ready(function () {

    //全部图片
    $("img").addClass("img-responsive");
    //鼠标悬停更换返回顶部图片
    $(".imgdiv").mouseover(function () {
        $(".imgsrc").attr("src", "/static/images/top4.png");
    });
    $(".imgdiv").mouseout(function () {
        $(".imgsrc").attr("src", "/static/images/top2.png");
    });
});

//倒计时时间,用于再次获取验证码
let countdown = 60;

//点击获取验证码按钮后,使按钮不可用,60秒时恢复可用
function setTime(val, type) {
    let existEmail;
    if (type === 1) {
        let email = $("#emailRegister").val();
        if (email == null || email === "") {
            return false;
        }
    } else if (type === 2) {
        let email = $("#emailResetPassword").val();
        if (email == null || email === "") {
            return false;
        }
        //找回密码时需要先确认数据库中是否存在该邮箱
        $.ajax({
            url: "/user/existEmail",
            type: "get",
            data: {email: email},
            async: false,
            success: function (result) {
                existEmail = !!result.success;
            }
        });
        if (existEmail !== true) {
            return false;
        }
    }
    if (countdown === 0) {
        val.removeAttribute("disabled");
        val.className = 'btn-warning btn btn-sm';
        val.value = "获取验证码";
        countdown = 60;
        return false;
    } else {
        val.setAttribute("disabled", true);
        val.className = 'btn-default btn btn-sm disabled';
        val.value = "重新发送(" + countdown + ")";
        countdown--;
    }
    setTimeout(function () {
        setTime(val);
    }, 1000);
}

//返回顶部图标出现或者消失
$(function () {
    $(function () {
        $(window).scroll(function () {
            if ($(window).scrollTop() > 100) {
                $("#gotop").fadeIn(1000);//一秒渐入动画
            } else {
                $("#gotop").fadeOut(1000);//一秒渐隐动画
            }
        });

        $("#gotop").click(function () {
            $('body,html').animate({scrollTop: 0}, 1000);
        });
    });
});

//检验用户名是否存在
function checkUserName(userName) {
    if (userName === "") {
        $("#userNameFail").html("用户名不能为空！请输入用户名后再注册!");
        $("#userNameSuccess").html("");
        $("#userName").focus();
        return;
    }
    $.post("/user/existUserWithUserName", {userName: userName},
        function (result) {
            if (result.success) {
                $("#userNameFail").html("该用户名已被注册,请重新输入后再注册!");
                $("#userNameSuccess").html("");
                $("#userName").focus();
            } else {
                $("#userNameSuccess").html("用户名可以使用!");
                $("#userNameFail").html("");
            }
        }
    );
}

//检验邮箱是否存在--注册时
function checkEmail(email) {
    if (email === "") {
        $("#emailFail").html("邮箱不能为空！请输入邮箱后再注册！");
        $("#emailSuccess").html("");
        $("#emailRegister").focus();
        return;
    }
    $.post("/user/existEmail", {email: email},
        function (result) {
            if (result.success) {
                $("#emailFail").html("该邮箱已被注册,请重新输入后再注册!");
                $("#emailSuccess").html("");
                $("#emailRegister").focus();
            } else {
                $("#emailFail").html("");
                $("#emailSuccess").html("邮箱可以使用!");
            }
        }
    );
}

//向后端发送获取验证码请求,参数为验证码类型(type=1时为注册,type=2时为找回密码)
function getVerificationCode(type) {
    let email
    if (type === 1) {
        email = $("#emailRegister").val();
    } else if (type === 2) {
        email = $("#emailResetPassword").val();
        //找回密码时需要先确认数据库中是否存在该邮箱
        let existEmail;
        $.ajax({
            url: "/user/existEmail",
            type: "get",
            data: {email: email},
            async: false,
            success: function (result) {
                existEmail = result.success;
            }
        });
        if (existEmail === false) {
            alert("你输入的邮箱地址没有在本站注册!!")
            return false;
        }
    }
    if (email == null || email === "") {
        alert("邮箱不能为空!!");
        return false;
    }
    $.ajax({
        url: "/user/getVerificationCode",
        type: "get",
        data: {type: type, email: email},
        success: function (result) {
            if (result.success) {
                if (type === 1) {
                    alert("注册的验证码已经发送到你的邮箱,请注意查收!!")
                } else if (type === 2) {
                    alert("找回密码的验证码已经发送到你的邮箱,请注意查收!!")
                }
            }
        }
    });
}

//注册时验证
function checkRegisterValue() {

    let password = $("#passwordRegister").val();
    let password2 = $("#password2Register").val();
    let registerCode = $("#registerCode").val();
    let imageCode;
    if (password.length < 6) {
        alert("密码长度要大于5!");
        return false;
    }
    if (password !== password2) {
        alert("密码和确认密码不相同,请重新输入!");
        return false;
    }
    $.ajax({
        url: "/user/getRegisterCode",
        type: "get",
        async: false,
        success: function (result) {
            if (result.success) {
                imageCode = result.imageCode;
            }
        }
    });
    if (registerCode !== imageCode) {
        alert("验证码不正确,请重新输入!");
        return false;
    }
    return true;
}

function logoutUser() {
    if (confirm("您确定要退出登录吗?")) {
        window.location.href = "/user/logout";
    }
}

//找回密码时验证
function checkResetPasswordValue() {

    let password = $("#passwordResetPassword").val();
    let password2 = $("#password2ResetPassword").val();
    let resetPasswordCode = $("#resetPasswordCode").val();
    let imageCode;
    if (password.length < 6) {
        alert("密码长度要大于5!");
        return false;
    }
    if (password !== password2) {
        alert("密码和确认密码不相同,请重新输入!");
        return false;
    }
    $.ajax({
        url: "/user/getResetPasswordCode",
        type: "get",
        async: false,
        success: function (result) {
            if (result.success) {
                imageCode = result.imageCode;
            }
        }
    });
    alert(resetPasswordCode + "," + imageCode)
    if (resetPasswordCode !== imageCode) {
        alert("验证码不正确,请重新输入!");
        return false;
    }
    return true;
}

//修改个人信息时验证
function checkModifyValue() {
    let password = $("#passwordModify").val();
    let password2 = $("#password2Modify").val();
    if (password !== password2) {
        alert("密码和确认密码不相同,请重新输入!");
        return false;
    }
    return true;
}

//留言时验证
function checkContactValue() {
    let userId = $("#userId").val();
    if (userId == null || userId === '') {
        alert("你的登录状态已到期，请重新登录！！");
        return false;
    }
    return true;
}

function deleteContact(id) {
    if (confirm("您确定要删除这个留言吗?")) {
        window.location.href = "/contact/delete?id=" + id;
    }
}

function seeContactDetails(id) {
    $.ajax({
        url: "/contact/findById",
        type: "get",
        data: {id: id},
        success: function (result) {
            if (result.success) {
                $("#contactId").html(result.contact.id);
                $("#contactTime").html(result.contact.time);
                $("#contactContent").html(result.contact.content);
                if (result.contact.reply == null) {
                    $("#contactReply").html("<span style='color: red'>未答复</span>");
                } else {
                    $("#contactReply").html(result.contact.reply);
                }
            } else {
                alert("查看详情失败！！");
            }
        },
    });
}

function modifyContact(id) {
    $("#contactIdModify").val(id);
    $.ajax({
        url: "/contact/findById",
        type: "get",
        data: {id: id},
        success: function (result) {
            if (result.success) {
                $("#contentContactModify").val(result.contact.content);
            } else {
                alert("查看详情失败！！");
            }
        },
    });
}

//检验联系方式名称是否重复
function checkSaveContactInformationName(type) {
    let name;
    let userId;
    if (type === 1) {
        name = $("#nameAddContactInformation").val();
        userId = $("#userIdAddContactInformation").val();
    } else if (type === 2) {
        name = $("#nameModifyContactInformation").val();
        userId = $("#userIdModifyContactInformation").val();
    }
    let nameIsExist;
    $.ajax({
        url: "/contactInformation/checkSaveContactInformationName",
        type: "get",
        async: false,
        data: {name: name, userId: userId},
        success: function (result) {
            if (result.success) {
                nameIsExist = true;
                alert("请重新输入联系方式的名称，因为名称为" + name + "的联系方式已存在！！");
            } else {
                nameIsExist = false;
            }
        },
    });
    return nameIsExist !== true;
}

function seeContactInformationDetails(id) {
    $.ajax({
        url: "/contactInformation/findById",
        type: "get",
        data: {id: id},
        success: function (result) {
            if (result.success) {
                $("#nameSeeContactInformation").val(result.contactInformation.name);
                $("#contentSeeContactInformation").val(result.contactInformation.content);
            } else {
                alert("查看详情失败！！");
            }
        },
    });
}

function modifyContactInformation(id) {
    $("#idModifyContactInformation").val(id);
    $.ajax({
        url: "/contactInformation/findById",
        type: "get",
        data: {id: id},
        success: function (result) {
            if (result.success) {
                $("#nameModifyContactInformation").val(result.contactInformation.name);
                $("#contentModifyContactInformation").val(result.contactInformation.content);
            } else {
                alert("查看详情失败！！");
            }
        },
    });
}

function deleteContactInformation(id) {
    if (confirm("您确定要删除这个联系方式吗?")) {
        window.location.href = "/contactInformation/delete?id=" + id;
    }
}

function checkAddGoodsValue() {
    let content = CKEDITOR.instances.contentAddGoods.getData();
    if (content === "" || content === null) {
        alert("商品详情不能为空!");
        return false;
    }
    return true;
}

function resetSearchGoodsValue() {
    $("#nameSearchGoods").val("");
    $("#goodsTypeIdSearchGoods").val("");
    $("#stateSearchGoods").val("");
    $("#isRecommendSearchGoods").val("");
}

// 添加商品到购物车
function addGoodsToShoppingCart(goodsId) {
    if (confirm("您确定要将这个商品加入购物车吗?")) {
        $.ajax({
            url: "/goods/addGoodsToShoppingCart",
            type: "post",
            data: {goodsId: goodsId},
            success: function (result) {
                if (result.success) {
                    alert("加入购物车成功！！");
                } else {
                    alert(result.errorInfo);
                }
            },
        });
    }
}

function deleteGoodsInShoppingCart(goodsId) {
    if (confirm("您确定要将这个商品从购物车中删除吗?")) {
        $.ajax({
            url: "/goods/deleteGoodsInShoppingCart",
            type: "post",
            data: {goodsId: goodsId},
            success: function (result) {
                if (result.success) {
                    alert("删除成功！！");
                    window.location.href = "/toMyShoppingCart";
                } else {
                    alert(result.errorInfo);
                }
            },
        });
    }
}

function pay(goodsId) {
    if (confirm("您确定要支付吗?")) {
        $.ajax({
            url: "/payRecord/pay",
            type: "post",
            data: {goodsId: goodsId},
            success: function (result) {
                if (result.success) {
                    alert("支付成功！！");
                    $.ajax({
                        url: "/goods/deleteGoodsInShoppingCart",
                        type: "post",
                        data: {goodsId: goodsId},
                        success: function (result) {
                            if (result.success) {
                                window.location.href = "/toMyShoppingCart";
                            } else {
                                alert(result.errorInfo);
                            }
                        },
                    });
                } else {
                    alert("支付失败！！");
                }
            },
        });
    }
}