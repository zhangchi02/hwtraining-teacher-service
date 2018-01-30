package com.huawei.hwtraining.student.service.api;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.huawei.hwtraining.student.service.model.ForumContent;

/**  
*  
*  
* @author TankTian
* @date 2018年1月29日    
*/
public interface ForumManager {
	String uploadFile(MultipartFile file);
	boolean addForumContent(ForumContent forumContent);
	List<ForumContent> getForumContent(String classId);
}
