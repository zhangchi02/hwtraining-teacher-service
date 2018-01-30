package com.huawei.hwtraining.student.service.dao;

import com.huawei.hwtraining.DbAdapter;
import com.huawei.hwtraining.student.service.api.ForumManager;
import com.huawei.hwtraining.student.service.api.StudentScoreManager;

/**  
*  
*  
* @author TankTian
* @date 2018年1月30日    
*/
public interface StudentServiceDbAdapter extends DbAdapter,StudentScoreManager,ForumManager{
}
