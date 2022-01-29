<%--
  Created by IntelliJ IDEA.
  User: BOEN
  Date: 2022/1/24
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>$Title$</title>
    <script src="js/jquery.min.js"></script>
    <script>
      $(function () {
        //获取所有省份信息
        $.ajax({
            type:"GET",
            url:"areaController.do",
            data:{parentID:0},
            dataType:"json",
            success:function (areas) {
              $.each(areas,function (i,e) {
                $("#province").append('<option value = "'+e.areaid+'">'+e.areaname+'</option>');
              })
            }
        })
      })
      
      function showCity(val) {
        //获取指定省份的所有城市信息
        $.ajax({
          type:"GET",
          url:"areaController.do",
          data:{parentID:val},
          dataType:"json",
          success:function (areas) {
            $("#city").html('<option>-请选择-</option>');//清除之前的缓存
            $("#area").html('<option>-请选择-</option>');//清除之前的缓存
            $.each(areas,function (i,e) {
              $("#city").append('<option value = "'+e.areaid+'">'+e.areaname+'</option>');
            })
          }
        })
      }

      function showArea(val) {
        //获取指定省份的所有城市信息
        $.ajax({
          type:"GET",
          url:"areaController.do",
          data:{parentID:val},
          dataType:"json",
          success:function (areas) {
            $("#area").html('<option>-请选择-</option>');//清除之前的缓存
            $.each(areas,function (i,e) {
              $("#area").append('<option value = "'+e.areaid+'">'+e.areaname+'</option>');
            })
          }
        })
      }
    </script>
  </head>
  <body>
    籍贯：
    <select id="province" onchange="showCity(this.value)">
      <option>-请选择-</option>
    </select>
    <select id="city" onchange="showArea(this.value)">
      <option>-请选择-</option>
    </select>
    <select id="area">
      <option>-请选择-</option>
    </select>
  </body>
</html>
