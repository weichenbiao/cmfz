<%@page isELIgnored="false" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"/>
<%--专辑和音频--%>
<script>
    $(function () {
        $("#albumList").jqGrid({
            autowidth: true,
            styleUI: "Bootstrap",
            url: "${app}/album/queryByPage",
            editurl: "${app}/album/edit",
            pager: "#albumPager",
            multiselect: true,
            rownumbers: true,
            height: "350px",
            datatype: "json",
            page: 1,
            rowNum: 3,
            rowList: [2, 4, 6, 10],
            viewrecords: true,
            colNames: ["编号", "标题", "分数", "作者", "播音员", "章节数", "专辑简介", "状态", "发行时间", "上传时间", "插图"],
            colModel: [
                {name: "id", hidden: true},
                {name: "title", align: "center", editable: true},
                {//分数
                    name: "score",
                    align: "center",
                    editable: true
                },
                {//作者
                    name: "author",
                    align: "center",
                    editable: true
                },
                {
                    //播音员
                    name: "beam",
                    align: "center",
                    editable: true
                },
                {
                    //章节数
                    name: "count",
                    align: "center"
                },
                {
                    //专辑简介
                    name: "content",
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
                    //发行时间
                    name: "printTime",
                    align: "center",
                    editable: true,
                    edittype: "date",
                },
                {
                    //上传时间
                    name: "upTime",
                    align: "center",
                    edittype: "date",
                },
                {
                    name: "photo",
                    align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img style='whidth:120px;height:60px' src='${app}/img/" + cellvalue + "'/>"
                    },
                    editable: true,
                    edittype: "file"
                }
            ],
            subGrid: true,
            subGridRowExpanded: function (subgrid_id, row_id) {
                addSubGrid(subgrid_id, row_id)
            }
        }).jqGrid("navGrid", '#albumPager',
            {search: false},
            {
                //    编辑
                closeAfterEdit: true,
                beforeShowForm: function (obj) {
                    obj.find("#photo").attr("disabled", true)
                }
            },
            {
                //添加
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var id = response.responseText;
                    $.ajaxFileUpload({
                        url: "${app}/album/upload",
                        fileElementId: "photo",
                        data: {id: id},
                        success: function () {
                            $("#albumList").trigger("reloadGrid")
                        }
                    })
                    return response;
                }
            },
            {}
        )
    })

    function addSubGrid(subgrid_id, row_id) {
        var subId = subgrid_id + "table";
        var pageId = subgrid_id + "page";
        $("#" + subgrid_id).html("<table id='" + subId + "'></table><div id='" + pageId + "'></div>");

        $("#" + subId).jqGrid({
            autowidth: true,
            styleUI: "Bootstrap",
            url: "${app}/audio/queryByPage?aid=" + row_id,
            editurl: "${app}/audio/edit?aid=" + row_id,
            pager: "#" + pageId,
            multiselect: true,
            rownumbers: true,
            datatype: "json",
            page: 1,
            rowNum: 2,
            rowList: [2, 4, 6, 10],
            viewrecords: true,
            colNames: ["编号", "标题", "大小", "时长", "上传时间", "音频", "操作"],
            colModel: [
                {
                    name: "id",
                    hidden: true
                },
                {
                    name: "title",
                    align: "center",
                    editable: true
                },
                {
                    name: "size",
                    align: "center",
                },
                {
                    name: "timelong",
                    align: "center"
                },
                {
                    name: "printTime",
                    align: "center",
                    edittype: "date"
                },
                {
                    name: "audio",
                    align: "center",
                    editable: true,
                    edittype: "file"
                },
                {
                    name: "audio",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<a onclick=\"play('" + cellvalue + "')\" href='#'><span class='glyphicon glyphicon-play-circle'></span></a>" + "            " +
                            "<a href='${app}/audio/download?filename=" + rowObject.audio + "'><span class='glyphicon glyphicon-download'></span></a>"
                    }
                }
            ]
        }).jqGrid("navGrid", '#' + pageId,
            {search: false},
            {
                //    编辑
                closeAfterEdit: true,
                beforeShowForm: function (obj) {
                    obj.find("#audio").attr("disabled", true)
                    obj.find("#printTime").attr("readonly", true)
                }
            },
            {
                //添加
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var id = response.responseText;
                    $.ajaxFileUpload({
                        url: "${app}/audio/upload",
                        fileElementId: "audio",
                        data: {id: id},
                        success: function () {
                            $("#" + subId).trigger("reloadGrid")
                            $("#albumList").trigger("reloadGrid")
                        }
                    })
                    return response;
                }

            },
            {
                afterSubmit: function () {
                    $("#" + subId).trigger("reloadGrid")
                    $("#albumList").trigger("reloadGrid")
                    return "dfd";
                }

            }
        )
    }

    function play(d) {
        $("#dialogId").modal("show");
        $("#audioId").attr("src", "${app}/audio/" + d);
    }
</script>


<div id="dialogId" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <audio id="audioId" controls src=""></audio>
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<table id="albumList"></table>
<div id="albumPager"></div>