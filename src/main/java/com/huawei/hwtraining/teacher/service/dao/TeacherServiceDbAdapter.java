package com.huawei.hwtraining.teacher.service.dao;

import com.huawei.hwtraining.DbAdapter;
import com.huawei.hwtraining.teacher.service.api.StudentManager;
import com.huawei.hwtraining.teacher.service.api.TaskManager;

/**
 * 
 * 
 * @author TankTian
 * @date 2018年1月30日
 */
public interface TeacherServiceDbAdapter extends DbAdapter, TaskManager, StudentManager {

}
