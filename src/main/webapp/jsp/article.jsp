<%@page isELIgnored="false" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"/>
<script src="${app}/kindeditor/kindeditor-all-min.js"></script>
<script src="${app}/kindeditor/lang/zh-CN.js"></script>
<%--文章--%>
<script>
    $(function () {
        $("#articleTable").jqGrid({
            autowidth: true,
            styleUI: "Bootstrap",
            url: "${app}/article/queryAll",
            editurl: "${app}/article/edit",
            pager: "#articlePage",
            multiselect: true,
            rownumbers: true,
            height: "250px",
            datatype: "json",
            page: 1,
            rowNum: 2,
            rowList: [2, 4, 6, 10],
            viewrecords: true,
            colNames: ["编号", "标题", "作者", "状态", "创建时间", "内容", "操作"],
            colModel: [
                {
                    name: "id",
                    align: "center"
                },
                {
                    name: "title",
                    align: "center"
                },
                {
                    name: "author",
                    align: "center"
                },
                {
                    name: "status",
                    align: "center",
                    editable: true,
                    edittype: "select",
                    editoptions: {value: "显示:显示;不显示:不显示"}
                },

                {
                    name: "createDate",
                    align: "center",
                    formatter: "date"
                },
                {
                    name: "content",
                    align: "center",
                    hidden: true
                },
                {
                    name: "",
                    align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<a  href='#' onclick=\"lookModal('" + rowObject.id + "')\"><span class='glyphicon glyphicon-pencil'></span></a>"
                    }
                }
            ]
        }).jqGrid("navGrid", "#articlePage", {search: false, del: true, edit: false, add: false})
    })

    function showModal() {
        $("#myModal").modal('show');
        $("#addArticleFrom")[0].reset();
        KindEditor.html("#editor", "");
        KindEditor.create('#editor', {
            uploadJson: "${pageContext.request.contextPath}/kindeditor/upload",
            filePostName: "img",
            allowFileManager: true,
            fileManagerJson: "${pageContext.request.contextPath}/kindeditor/allImages",
            afterBlur: function () {
                this.sync();
            }
        });
        $("#modal_footer").html(
            "<button type=\"button\" onclick=\"addArticle()\" class=\"btn btn-primary\">添加</button>\n" +
            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>"
        )

    }

    function addArticle() {
        $.ajax({
            url: "${pageContext.request.contextPath}/article/add",
            datatype: "json",
            type: "post",
            data: $("#addArticleFrom").serialize(),
            success: function (data) {
                $("#myModal").modal('toggle');
                $("#articleTable").trigger("reloadGrid")
            }
        })
    }

    function lookModal(id) {
        $("#myModal").modal('show');
        $("#addArticleFrom")[0].reset();
        KindEditor.html("#editor", "");
        var value = $("#articleTable").jqGrid("getRowData", id);
        $("#title").val(value.title);
        $("#author").val(value.author);
        $("#status").val(value.status);
        $("#modal_footer").html(
            "<button type=\"button\" onclick=\"updateArticle('" + id + "')\" class=\"btn btn-primary\">修改</button>\n" +
            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>"
        );

        KindEditor.create('#editor', {
            allowFileManager: true,
            uploadJson: "${pageContext.request.contextPath}/kindeditor/upload",
            fileManagerJson: "${pageContext.request.contextPath}/kindeditor/allImages",
            filePostName: "img",
            afterBlur: function () {
                this.sync();
            }
        });
        KindEditor.appendHtml("#editor", value.content);
    }

    function updateArticle(id) {
        $.ajax({
            url: "${pageContext.request.contextPath}/article/update?id=" + id,
            datatype: "json",
            type: "post",
            data: $("#addArticleFrom").serialize(),
            success: function (data) {
                $("#myModal").modal('toggle');
                $("#articleTable").trigger("reloadGrid")
            }
        })
    }


</script>

<div>
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#">文章列表</a></li>
        <li role="presentation"><a href="#" onclick="showModal()">添加文章</a></li>
    </ul>
</div>

<table id="articleTable"></table>
<div id="articlePage"></div>

<div class="modal fade" id="myModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content" style="width:750px">w
            <!--模态框标题-->
            <div class="modal-header">
                <!--
                    用来关闭模态框的属性:data-dismiss="modal"
                -->
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                <h4 class="modal-title">编辑用户信息</h4>
            </div>

            <!--模态框内容体-->
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/article/editArticle" class="form-horizontal"
                      id="addArticleFrom">
                    <div class="form-group">
                        <label class="col-sm-1 control-label">标题</label>
                        <div class="col-sm-5">
                            <input type="text" name="title" id="title" placeholder="请输入标题" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">作者</label>
                        <div class="col-sm-5">
                            <input type="text" name="author" id="author" placeholder="作者姓名" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">状态</label>
                        <div class="col-sm-5">
                            <select class="form-control" name="status" id="status">
                                <option value="显示">显示</option>
                                <option value="不显示">不显示</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea id="editor" name="content" style="width:700px;height:300px;"></textarea>
                        </div>
                    </div>
                    <input id="addInsertImg" name="insertImg" hidden>
                </form>
            </div>
            <!--模态页脚-->
            <div class="modal-footer" id="modal_footer">
                <%--<button type="button" class="btn btn-primary">保存</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>--%>
            </div>
        </div>
    </div>
</div>
