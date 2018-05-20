<html>
    <#include "../common/header.ftl">
    
    <body>
        <div id="wrapper" class="toggled">
            
            <!-- 边栏sidebar -->
            <#include "../common/nav.ftl">
            
            <!-- 主要内容content -->
            <div id="page-content-wrapper">
                <div class="container-fluid">
					<div class="row clearfix">
						<div class="col-md-12 column">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>类目ID</th>
										<th>类目名称</th>
										<th>类目编号</th>
										<th>创建时间</th>
										<th>更新时间</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
								    <#list categoryPage.content as category>
										<tr>
											<td>${category.categoryId}</td>
											<td>${category.categoryName}</td>
											<td>${category.categoryType}</td>
											<td>${category.createTime}</td>
											<td>${category.updateTime}</td>
											<td><a href="/wei-sell/seller/category/index?categoryId=${category.categoryId}&page=${currentPage}&size=${size}">修改</a></td>
										</tr>
									</#list>
								</tbody>
							</table>
						</div>
					    
					    <!-- 分页 -->
					    <div class="col-md-12 column">
					        <ul class="nav" style="text-align: center">
								<ul class="pagination" >
								    <#if currentPage lte 1>
								    	<li class="disabled"><a href="#">上一页</a></li>
								    <#else>
										<li><a href="/wei-sell/seller/category/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
									</#if>
									
									<#list 1..categoryPage.getTotalPages() as index>
									    <#if currentPage == index>
										    <li class="active"><a href="#">${index}</a></li>
										<#else>   
										    <li><a href="/wei-sell/seller/category/list?page=${index}&size=${size}">${index}</a></li>
										</#if>
									</#list>
									
									<#if currentPage gte categoryPage.getTotalPages()>
										<li class="disabled"><a href="#">下一页</a></li>
									<#else>
										<li><a href="/wei-sell/seller/category/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
									</#if>
								</ul>
					        </ul>
					    </div>
					</div>
				</div>
            </div>
        
        </div>
        
		
    </body>
</html>