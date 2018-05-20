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
							<form role="form" method="post" action="/wei-sell/seller/product/save" >
								<div class="form-group">
									 <label for="productName">商品名称</label>
									 <input name="productName" type="text" class="form-control" value="${(productInfo.productName)!''}" id="productName" placeholder="请输入商品名称" required/>
								</div>
								<div class="form-group">
									 <label for="productPrice">商品价格</label>
									 <input name="productPrice" type="number" class="form-control" value="${(productInfo.productPrice)!''}" id="productPrice" placeholder="请输入商品价格" required/>
								</div>
								<div class="form-group">
									 <label for="productStock">商品库存</label>
									 <input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)!''}" id="productStock" placeholder="请输入商品库存" required/>
								</div>
								<div class="form-group">
									 <label for="productDescription">商品描述</label>
									 <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!''}" id="productDescription" placeholder="请输入商品描述" required />
								</div>
								<div class="form-group">
									 <label for="productIcon">商品图片</label>
									 
										 <#if (productInfo.productIcon)?? >
										     <div class="productIcon" style="margin-bottom:8px">
										         <img id="img" height="100" width="100" src="${(productInfo.productIcon)!''}"  alt="请输入商品图片地址" >
										     </div>
										 <#else>
										     <div class="productIcon" style="margin-bottom:8px">
										         <img src="" id="img">
										     </div>
										 </#if>   
										 <input name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)!''}" id="file"  placeholder="请输入图片地址" accept="image/*" required/>
										 
										 <!-- 选择本地图片时即时预览 -->
										 <!-- 
									     <script>
											 var fileEle = document.getElementById('file');
											 var imgEle = document.getElementById('img');
											 fileEle.onchange = function(e) {
											     var file1 = e.target.files[0];
											     var url1 = window.URL.createObjectURL(file1);
											     imgEle.src = url1;
											     imgEle.height = "100";
											     imgEle.width = "100";
											 }
										 </script>
										  -->	 
							    </div>
								<div class="form-group">
									 <label for="categoryType">商品类目</label>
									 <select name="categoryType" class="form-control"  id="categoryType" >
									     <#list categoryList as category>
									         <option value="${category.categoryType}"
									             <#if (productInfo.categoryType)?exists && (productInfo.categoryType == category.categoryType)>
									                 selected
									             </#if>
									             >${category.categoryName}
									         </option>
									     </#list>
									 </select>
								</div>
								<input hidden type="text" name="productId" value="${(productInfo.productId)!''}"> 
								<div class="btn-group">
								    <button type="submit" class="btn btn-lg">提交</button>
								</div>
								<div class="btn-group">
								    <a href="/wei-sell/seller/product/list?page=${page}&size=${size}" type="button" class="btn btn-success btn-lg">返回</a>
								</div>
							</form>
						</div>
					</div>
				</div>
            </div>
        
        </div>
        
		
    </body>
</html>