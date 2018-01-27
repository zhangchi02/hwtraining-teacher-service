# hwtraining-teacher-service

## 表格1  培训流程表 training_workflow  
class_id varchar  前台不显示（前台查询过滤条件）  
status int  0  未完成 1 完成  
hand_people varchar  
role varchar  
task varchar  
deadline varchar  
detail varchar  
comment varchar (前台传入 备注)  

## rest api  
put hwtraining/v1/task  
task  
class_id  
status 初始值0，前台确认完成传入1  
hand_people 获取登陆用户名  
comment  获取备注  




## 表格2  学员报名表  students  
class_id varchar  前台不显示（前台查询过滤条件）  
company_name  
name  
title  
phone_number  
email  
hwcloud_id 
comment 

## rest api 
post hwtraining/v1/student 
class_id varchar  前台不显示（前台查询过滤条件） 
company_name 
name 
title  
phone_number  
email 
hwcloud_id 
comment 

delete hwtraining/v1/student 
class_id varchar  前台不显示（前台查询过滤条件） 
name 
phone_number 
