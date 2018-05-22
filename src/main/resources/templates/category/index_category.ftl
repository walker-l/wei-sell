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
							<form role="form" method="post" action="/wei-sell/seller/category/save" >
								<div class="form-group">
									 <label for="categoryName">类目名称</label>
									 <input name="categoryName" type="text" class="form-control" value="${(category.categoryName)!''}" id="categoryName" placeholder="请输入类目名称" required/>
								</div>
								<div class="form-group">
									 <label for="categoryType">类目编号</label>
									 <input name="categoryType" type="number" class="form-control" value="${(category.categoryType)!''}" id="categoryType" placeholder="请输入类目编号" required/>
								</div>
								
								<input hidden type="text" name="categoryId" value="${(category.categoryId)!''}"> 
								<div class="btn-group">
								    <button type="submit" class="btn btn-lg">提交</button>
								</div>
								<div class="btn-group">
								    <a href="/wei-sell/seller/category/list?page=${page}&size=${size}" type="button" class="btn btn-success btn-lg">返回</a>
								</div>
							</form>
						</div>
					</div>
				</div>
            </div>
        
        </div>
        
		
    </body>
</html>