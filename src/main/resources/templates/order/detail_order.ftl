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
						<div class="col-md-6 column">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>订单ID</th>
										<th>订单总金额</th>
										<th>订单状态</th>
									</tr>
								</thead>
								<tbody>
								    <tr>
										<td>${orderDTO.orderId}</td>
										<td>${orderDTO.orderAmount}</td>
										<td>${orderDTO.getOrderStatusEnum().message}</td>
									</tr>
								</tbody>
							</table>
						</div>
					    
					     <!-- 订单详情表数据 -->
					    <div class="col-md-12 column">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>商品ID</th>
										<th>商品名称</th>
										<th>价格</th>
										<th>数量</th>
										<th>总额</th>
									</tr>
								</thead>
								<tbody>
								    <#list orderDTO.orderDetailList as orderDetail>
										<tr>
											<td>${orderDetail.productId}</td>
											<td>${orderDetail.productName}</td>
											<td>${orderDetail.productPrice}</td>
											<td>${orderDetail.productQuantity}</td>
											<td>${orderDetail.productPrice * orderDetail.productQuantity}</td>
										</tr>
									</#list>
								</tbody>
							</table>
						</div>
					    
					    <!-- 操作 -->
					    <div class="col-md-12 column">
					        <#if orderDTO.getOrderStatusEnum().message == "新订单">
							   <a href="/wei-sell/seller/order/finish?orderId=${orderDTO.orderId}&page=${page}&size=${size}"  type="button" class="btn btn-primary btn-lg">完结订单</a>
							   <a href="/wei-sell/seller/order/cancel?orderId=${orderDTO.orderId}&page=${page}&size=${size}"  type="button" class="btn btn-lg btn-danger">取消订单</a>
						    </#if>
						</div>
						
						<!-- 返回按钮 -->
						<div class="col-md-12 column">
							 <a href="/wei-sell/seller/order/list?page=${page}&size=${size}"  type="button" class="btn btn-lg btn-success" style="float:right">返回</a>
						</div>
					</div>
				</div>
		    
            </div>
        
        </div>
        
    </body>
</html>