package com.jhlee.webviewtest;

public class Url {
    //    static String url = "test<script>var loadUrl='https://exdsdsdample.com/movies.json';var xhr = new XMLHttpRequest();xhr.open('GET', loadUrl, true);fetch(loadUrl);alert('complete');</script>";

    static String url = "<!DOCTYPE html>\\n<html>\\n<head>\\n<meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>\\n<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>\\n<meta http-equiv='Cache-Control' content='no-cache, no-store, must-revalidate'>\\n<meta http-equiv='Pragma' content='no-cache'>\\n<meta http-equiv='Expires' content='-1'>\\n<style> *{margin:0;padding:0;}html, body{width:100%;height:100%;padding:0;margin:0;border:0;overflow:hidden;}a{text-decoration:none;outline:0;-webkit-tap-highlight-color:rgba(0,0,0,0);-webkit-tap-highlight-color:transparent;}img{border:0;}</style>\\n<script>function customScaleThisScreen() { var contentWidth = document.body.scrollWidth, windowWidth = window.innerWidth,newScale = windowWidth / contentWidth; document.body.style.zoom = newScale; } window.onload=customScaleThisScreen;</script>\\n</head>\\n<body>\\n<a href='https://acetrader.co.kr/'>\\n<img src='https://gwx.adlibr.com/resources/img/sample/banner_640x100.png' style='z-index:1;max-width:100%;' />\\n</a>\\n</body>\\n</html>";

//    static String url = "<link rel=\"preconnect\" href=\"https://adx-exchange.toast.com\">\n" +
//            "<link rel=\"preconnect\" href=\"https://ad-exchange.toast.com\">\n" +
//            "<link rel=\"preconnect\" href=\"https://cm-exchange.toast.com\">\n" +
//            "<div id=\"nhn-ax-adbox\" name=\"nhn-ax-adbox\" margin=\"0\" scrolling=\"no\" frameborder=\"0\"\n" +
//            "    style=\"position:absolute;left:0px;top:0px;padding:0;margin:0\">\n" +
//            "    <script\n" +
//            "    type=\"text/javascript\">!function (t, e) { \"use strict\"; function a(e) { var t = 1; try { var a = new Image; a.height = \"1\", a.width = \"1\", a.fetchPriority = \"high\", a.src = e } catch (e) { t = 0 } return t } var c; if (1 != function (e) { var t = 1; try { window.navigator.sendBeacon ? window.navigator.sendBeacon(e) : t = 2 } catch (e) { t = 0 } return t }(t += \"&nonce=\" + Math.floor(1e18 * Math.random()))) try { if (window.XMLHttpRequest) c = new XMLHttpRequest; else try { c = new ActiveXObject(\"Microsoft.XMLHTTP\") } catch (e) { try { c = new ActiveXObject(\"MSXML2.XMLHTTP.6.0\") } catch (e) { try { c = new ActiveXObject(\"MSXML2.XMLHTTP.3.0\") } catch (e) { c = null } } } null !== c ? (c.open(\"GET\", t, e), c.setRequestHeader(\"Content-type\", \"application/x-www-form-urlencoded\"), c.setRequestHeader(\"Cache-Control\", \"no-cache, no-store, max-age=0, must-revalidate\"), c.setRequestHeader(\"Pragma\", \"no-cache\"), c.setRequestHeader(\"Keep-Alive\", \"timeout=2, max=5\"), c.send()) : (a(t), 0) } catch (e) { a(t), 0 } }(\"https://alpha-adserver.toast.com/ss_imp?page_url=http%3A%2F%2Fwww.foobar.com%2F1234.html&pub_code=1546722034&area_code=1749544180&dsp_code=pss2CKrcA3jH38gzc0RVVK5vOcKBMw9TcM19l2q8cZIeWgpBCs-NB1VDsF3-YEtnrtUujpi4kq14paG8U45B2RHN0LmuSdDZQvw1miOuFa3SvC83e-ecNWqjbIbsQZ_6nQ-_krSQOFJQU5CF1v5Z35Ca-cfQpvLhKBae9KAyCBfAsLSPK7FYC5ZkoZghjCk7&pag=&pkg=&mid=&udid=TEST-TEST-TEST-TESTPAGE&dom=&log=ed152fd3-d5b1-4603-81e6-948d5e87bc39&request_time=1699345643095&ri=FySu3-5bjPyo2rM0VJx5ak01xjNyOJWOPYRuFWF3DsdRDMvP0gTGCJJl-dIaoOK5cxrCzqqAoSHER1X2kvFszsvd0r0fDLXzn6LkF6AIuYQatHSV0INdJOClaMMWbxOz-WGscXWgRsEbiw_V5tErWJH8hicFkjPfKwEUKOjkMMs=&cs=31c66c31c4598b737373a8569d06fbf1&deal=&pa=&pdeal=&wc=0.1&bp=0.014862692307692311&cur=USD\", !1); </script>\n" +
//            "    <noscript>\n" +
//            "        <div style=\"visibility:hidden;position:absolute;width:0;height:0;padding:0;margin:0;\"><img\n" +
//            "    src=\"https://alpha-adserver.toast.com/ns_imp?page_url=http%3A%2F%2Fwww.foobar.com%2F1234.html&pub_code=1546722034&area_code=1749544180&dsp_code=pss2CKrcA3jH38gzc0RVVK5vOcKBMw9TcM19l2q8cZIeWgpBCs-NB1VDsF3-YEtnrtUujpi4kq14paG8U45B2RHN0LmuSdDZQvw1miOuFa3SvC83e-ecNWqjbIbsQZ_6nQ-_krSQOFJQU5CF1v5Z35Ca-cfQpvLhKBae9KAyCBfAsLSPK7FYC5ZkoZghjCk7&pag=&pkg=&mid=&udid=TEST-TEST-TEST-TESTPAGE&dom=&log=ed152fd3-d5b1-4603-81e6-948d5e87bc39&request_time=1699345643095&ri=FySu3-5bjPyo2rM0VJx5ak01xjNyOJWOPYRuFWF3DsdRDMvP0gTGCJJl-dIaoOK5cxrCzqqAoSHER1X2kvFszsvd0r0fDLXzn6LkF6AIuYQatHSV0INdJOClaMMWbxOz-WGscXWgRsEbiw_V5tErWJH8hicFkjPfKwEUKOjkMMs=&cs=31c66c31c4598b737373a8569d06fbf1&deal=&pa=&pdeal=&wc=0.1&bp=0.014862692307692311&cur=USD\"\n" +
//            "    width=\"0\" height=\"0\" /></div>\n" +
//            "    </noscript>\n" +
//            "    <div style=\"visibility:hidden;position:absolute;width:0;height:0;padding:0;margin:0;\"><img\n" +
//            "    src=\"https://adx-test.partner.com/nurl?test=1&wp=0.09007692307692308&cc=\" width=\"0\" height=\"0\" />\n" +
//            "    </div>\n" +
//            "    <div style=\"visibility:hidden;position:absolute;width:0;height:0;padding:0;margin:0;\"><img\n" +
//            "    src=\"https://alpha-adserver.toast.com/a_imp?page_url=http%3A%2F%2Fwww.foobar.com%2F1234.html&pub_code=1546722034&area_code=1749544180&dsp_code=pss2CKrcA3jH38gzc0RVVK5vOcKBMw9TcM19l2q8cZIeWgpBCs-NB1VDsF3-YEtnrtUujpi4kq14paG8U45B2RHN0LmuSdDZQvw1miOuFa3SvC83e-ecNWqjbIbsQZ_6nQ-_krSQOFJQU5CF1v5Z35Ca-cfQpvLhKBae9KAyCBfAsLSPK7FYC5ZkoZghjCk7&pag=&pkg=&mid=&udid=TEST-TEST-TEST-TESTPAGE&dom=&log=ed152fd3-d5b1-4603-81e6-948d5e87bc39&request_time=1699345643095&ri=FySu3-5bjPyo2rM0VJx5ak01xjNyOJWOPYRuFWF3DsdRDMvP0gTGCJJl-dIaoOK5cxrCzqqAoSHER1X2kvFszsvd0r0fDLXzn6LkF6AIuYQatHSV0INdJOClaMMWbxOz-WGscXWgRsEbiw_V5tErWJH8hicFkjPfKwEUKOjkMMs=&cs=31c66c31c4598b737373a8569d06fbf1&deal=&pa=&pdeal=&wc=0.1&bp=0.014862692307692311&cur=USD\"\n" +
//            "    width=\"0\" height=\"0\" /></div>\n" +
//            "    <div style=\"visibility:hidden;position:absolute;width:0;height:0;padding:0;margin:0;\"><img\n" +
//            "    src=\"https://alpha-adlc-exchange.toast.com/log?u=http%3A%2F%2Fwww.foobar.com%2F1234.html&pubcode=1546722034\"\n" +
//            "    width=\"0\" height=\"0\" /></div>\n" +
//            "    <div id=\"nhn-ax-adbox-placement\">\n" +
//            "\t<iframe id=\"nhn-ax-dsp-frame\" frameborder=\"0\" scrolling=\"no\" marginwidth=\"0\" marginheight=\"0\"\n" +
//            "    style=\"position:absolute;padding:0;margin:0;cellpadding:0;cellspacing:0;display:block;border:none;width:100;height:100;\"\n" +
//            "    src=\"data:text/html;charset=utf-8;base64,PGJhc2UgdGFyZ2V0PSJfdG9wIiAvPgo8YSBocmVmPSJodHRwczovL3d3dy53M3NjaG9vbHMuY29tLyI+CjxpbWcgc3JjPSJodHRwczovL3d3dy5hY2Nlc3NpYmlsaXR5Lm9yZy5hdS93cC1jb250ZW50L3VwbG9hZHMvMjAyMy8wNi93M2MtNDIweDQyMC5wbmciIC8+CjwvYT4=\" onload=\"axAdboxFrameLoaded(this);\"></iframe>\n" +
//            "    </div>\n" +
//            "</div>\n" +
//            "    <script>\n" +
//            "    function axAdboxFrameLoaded() { var i = new Image(); i.fetchPriority = \"high\"; i.loading = \"eager\"; i.width = 1; i.height = 1; i.src = \"https://aaa.com\"; };\n" +
//            ";alert('complete');" +
//            "</script>";
}
