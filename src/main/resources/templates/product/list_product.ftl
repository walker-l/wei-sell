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
										<th>商品ID</th>
										<th>名称</th>
										<th>图片</th>
										<th>单价</th>
										<th>库存</th>
										<th>描述</th>
										<th>类目</th>
										<th>创建时间</th>
										<th>更新时间</th>
										<th colspan="2">操作</th>
									</tr>
								</thead>
								<tbody>
								    <#list productInfoPage.content as productInfo>
										<tr>
											<td>${productInfo.productId}</td>
											<td>${productInfo.productName}</td>
											<td><img alt="" src="${productInfo.productIcon}" height="100" width="100"></td>
											<td>${productInfo.productPrice}</td>
											<td>${productInfo.productStock}</td>
											<td>${productInfo.productDescription}</td>
											<td>${productInfo.categoryType}</td>
											<td>${productInfo.createTime}</td>
											<td>${productInfo.updateTime}</td>
											<td><a href="/wei-sell/seller/product/index?productId=${productInfo.productId}&page=${currentPage}&size=${size}">修改</a></td>
											<td>
											    <#if productInfo.getProductStatusEnum().message == "在架">
											        <a href= "/wei-sell/seller/product/off_sale?productId=${productInfo.productId}&page=${currentPage}&size=${size}">下架</a>
											    <#else>
											        <a href= "/wei-sell/seller/product/on_sale?productId=${productInfo.productId}&page=${currentPage}&size=${size}">上架</a>
											    </#if>
											</td>
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
										<li><a href="/wei-sell/seller/product/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
									</#if>
									
									<#list 1..productInfoPage.getTotalPages() as index>
									    <#if currentPage == index>
										    <li class="active"><a href="#">${index}</a></li>
										<#else>   
										    <li><a href="/wei-sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>
										</#if>
									</#list>
									
									<#if currentPage gte productInfoPage.getTotalPages()>
										<li class="disabled"><a href="#">下一页</a></li>
									<#else>
										<li><a href="/wei-sell/seller/product/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
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