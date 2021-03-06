package com.huawei.hwtraining.student.service.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.huawei.hwtraining.student.service.dao.MysqlStudentServiceDbAdapterImpl;
import com.huawei.hwtraining.student.service.model.ForumContent;

/**
 * 
 * 
 * @author TankTian
 * @date 2018年1月29日
 */
@RestSchema(schemaId = "forumManager")
@RequestMapping(path = "/hwtraining/v1")
public class ForumManagerImpl implements ForumManager {
	List<ForumContent> forumContents = new LinkedList<ForumContent>();
	private ForumManager forumManager = MysqlStudentServiceDbAdapterImpl.getInstance();

	@RequestMapping(path = "/upload", produces = MediaType.TEXT_PLAIN_VALUE, method = RequestMethod.POST)
	public String uploadFile(@RequestPart(name = "file") MultipartFile file) {
		return forumManager.uploadFile(file);
	}

	@RequestMapping(path = "/forumcontent", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public boolean addForumContent(@RequestBody ForumContent forumContent) {

		return forumManager.addForumContent(forumContent);
	}

	@RequestMapping(path = "/forumcontent", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<ForumContent> getForumContent(String classId) {
		return forumManager.getForumContent(classId);
	}

}
