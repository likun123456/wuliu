/**
 奇趣源码商城 www.qiqucode.com
 奇趣源码

 @author
 @create 2017-07-24
 */
window.onload = function () {
    map.plugin(["AMap.ToolBar"], function () {
        map.addControl(new AMap.ToolBar());
    });
    if (location.href.indexOf('&guide=1') !== -1) {
        map.setStatus({scrollWheel: false})
    }
}