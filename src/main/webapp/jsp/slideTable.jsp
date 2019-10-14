<%@page isELIgnored="false" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"/>
<%--轮播图--%>
<script>
    $(function () {
        $("#list").jqGrid({
            autowidth: true,
            styleUI: "Bootstrap",
            url: "${app}/slide/queryByPage",
            editurl: "${app}/slide/edit",
            pager: "#pager",
            multiselect: true,
            rownumbers: true,
            height: "250px",
            datatype: "json",
            page: 1,
            rowNum: 2,
            rowList: [2, 4, 6, 10],
            viewrecords: true,
            colNames: ["编号", "标题", "状态", "描述", "创建时间", "图片"],
            colModel: [
                {
                    name: "id",
                    align: "center",
                },
                {
                    name: "title",
                    align: "center",
                    editable: true
                },
                {
                    name: "status",
                    align: "center",
                    editable: true,
                    edittype: "select",
                    editoptions: {value: "显示:显示;不显示:不显示"}


                },
                {
                    name: "intr",
                    align: "center",
                    editable: true
                },
                {
                    name: "creat_time",
                    align: "center",
                    editable: true,
                    edittype: "date",
                },
                {
                    name: "imgpath",
                    align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img style='whidth:120px;height:60px' src='${app}/img/" + cellvalue + "'/>"
                    },
                    editable: true,
                    edittype: "file"
                }
            ]
        }).jqGrid("navGrid", '#pager',
            {search: false},
            {
                //    编辑
                closeAfterEdit: true,
                beforeShowForm: function (obj) {
                    obj.find("#imgpath").attr("disabled", true)
                }
            },
            {
                //添加
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var id = response.responseText;
                    $.ajaxFileUpload({
                        url: "${app}/slide/upload",
                        fileElementId: "imgpath",
                        data: {id: id},
                        success: function () {
                            $("#list").trigger("reloadGrid")
                        }
                    })
                    return response;
                }
            },
            {
                /* closeAfterDel:true,
                 afterSubmit:function (response) {
                     alert(123)
                     refresh;
                     return "asddc";
                 }*/
            }
        )
    })

    function importExcel() {
        $.ajax({
            url: "${app}/slide/import",
            datatype: "json",
            type: "post",
            success: function (data) {

            }
        })
    }
</script>

<%--页头--%>
<div class="page-header">
    <h1>轮播图管理
        <small>轮播图信息</small>
    </h1>
</div>

<div>
    <%--onclick="importExcel()"--%>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">轮播图信息</a></li>
        <li role="presentation"><a href="${app}/slide/import">导出Excel</a></li>
    </ul>
</div>


<table id="list">

</table>
<div id="pager">

</div>