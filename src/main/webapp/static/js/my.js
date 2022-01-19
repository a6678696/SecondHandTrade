//一加载页面就进行的操作
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

//删除留言
function deleteContact(id) {
    if (confirm("您确定要删除这个留言吗?")) {
        window.location.href = "/contact/delete?id=" + id;
    }
}

//查看留言详情
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

//修改留言
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

//查看联系方式详情
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

//修改联系方式
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

//删除联系方式
function deleteContactInformation(id) {
    if (confirm("您确定要删除这个联系方式吗?")) {
        window.location.href = "/contactInformation/delete?id=" + id;
    }
}

//验证商品详情是否为空
function checkAddGoodsValue() {
    let content = CKEDITOR.instances.contentGoods.getData();
    if (content === "" || content === null) {
        alert("商品详情不能为空!");
        return false;
    }
    return true;
}

//重置搜索商品的条件(用户商品管理)
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

//删除购物车的商品
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

// 预订商品
function reserve(goodsId) {
    if (confirm("您确定要预订吗?")) {
        $.ajax({
            url: "/reserveRecord/reserve",
            type: "post",
            data: {goodsId: goodsId},
            success: function (result) {
                if (result.success) {
                    alert("成功预订，请联系卖家当面交易哦");
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
                    alert("预订失败！！");
                }
            },
        });
    }
}

//修改商品状态
function updateGoodsState(goodsId, state) {
    let stateName;
    if (state === 1) {
        stateName = "上架";
    } else if (state === 3) {
        stateName = "下架";
    } else if (state === 5) {
        stateName = "完成交易";
    }
    if (confirm("您确定要将商品状态设置为" + stateName + "吗?")) {
        $.ajax({
            url: "/goods/updateGoodsState",
            type: "post",
            data: {goodsId: goodsId, state: state},
            success: function (result) {
                if (result.success) {
                    alert("设置成功！！");
                    window.location.href = "/toGoodsManagePage";
                } else {
                    alert("设置失败！！");
                }
            },
        });
    }
}

//删除商品
function deleteGoods(goodsId) {
    if (confirm("您确定要删除这个商品吗?")) {
        $.ajax({
            url: "/goods/delete",
            type: "post",
            data: {goodsId: goodsId},
            success: function (result) {
                if (result.success) {
                    alert("删除成功！！");
                    window.location.href = "/toGoodsManagePage";
                } else {
                    alert("删除失败！！");
                }
            },
        });
    }
}

//查看商品详情
function seeOrModifyGoodsDetails(goodsId, type) {

    if (type === 1) {
        $("#modalHeadName").html("查看");
        $("#modifyButton").css("display", "none");
    } else if (type === 2) {
        $("#modalHeadName").html("修改");
        $("#modifyButton").css("display", "block");
    }
    $.ajax({
        url: "/goods/findById",
        type: "post",
        data: {goodsId: goodsId},
        success: function (result) {
            if (result.success) {
                $("#id").val(result.goods.id);
                $("#goodsName").val(result.goods.name);
                $("#priceNow").val(result.goods.priceNow);
                $("#goodsTypeId").val(result.goods.goodsTypeId);
                CKEDITOR.instances.contentGoods.setData(result.goods.content);
            } else {
                alert("删除失败！！");
            }
        },
    });
}

//获取卖家联系方式
function getContactInformation(goodsId) {

    $.ajax({
        url: "/contactInformation/getListByGoodsId",
        type: "post",
        data: {goodsId: goodsId},
        success: function (result) {
            if (result.success) {
                $("#contactInformationStr").html(result.contactInformationStr);
            } else {
                alert("删除失败！！");
            }
        },
    });
}

//修改预订记录状态
function updateReserveRecordState(reserveRecordId, state, stateNow) {

    let stateName;
    if (state === 1) {
        stateName = "预订已取消";
    }
    if (stateNow === 1) {
        alert("无需操作,状态已经是预订已取消！！");
        return false;
    }
    if (confirm("您确定要将预订记录状态设置为" + stateName + "吗?")) {
        $.ajax({
            url: "/reserveRecord/updateReserveRecordState",
            type: "post",
            data: {reserveRecordId: reserveRecordId, state: state},
            success: function (result) {
                if (result.success) {
                    alert("设置成功！！");
                    window.location.href = "/toMyReserveRecordPage";
                } else {
                    alert("设置失败！！");
                }
            },
        });
    }
}

//登录前检验用户是否被封禁
function checkLoginUserState() {

    let userName = $("#userName").val();
    let isBan = false;
    $.ajax({
        url: "/user/checkLoginUserState",
        type: "post",
        data: {userName: userName},
        async: false,
        success: function (result) {
            if (result.success) {
                let status = result.status;
                if (status === 0) {
                    isBan = true;
                }
            }
        },
    });
    if (isBan === true) {
        alert("你的账号已被封禁，如果要解封，请发邮件到管理员邮箱说明情况，管理员邮箱地址为：123141@qq.com");
        return false;
    }
    return true;
}