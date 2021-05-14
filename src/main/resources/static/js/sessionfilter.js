layui.use(['jquery', 'layer'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    // 设置ajax参数
    $.ajaxSetup({
        //规定是否为请求触发全局 AJAX 事件处理程序。默认是 true
        global:true,
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
        // 在请求成功之后调用。在处理success之前
        // 用于处理 XMLHttpRequest 原始响应数据的函数。
        // data是ajax返回的原始数据，type是调用jQuery.ajax时传入的dataType
        // response.setContentType("text/html;charset=UTF-8"); 未设置时，需做如[object XMLDocument]处理
        dataFilter:function (data, type) {
            if (data == "timeOut" || data == "[object XMLDocument]") {
                window.location.reload();
            }else {
                return data;
            }
        }
    })
})