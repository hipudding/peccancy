package com.pipudding.peccancy.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pipudding.peccancy.dao.ImageDao;
import com.pipudding.peccancy.entity.ImageEntity;

@Service
@Transactional
public class WeixinService {
	
	@Autowired
	ImageDao imageDao;
	
	public boolean saveFile(MultipartFile file,String imgPath,String fileName)
	{		
		if(file == null)
		{
			return false;
		}
		
		File destFile = new File(imgPath + "/" + fileName);
		try {
			file.transferTo(destFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		ImageEntity image = new ImageEntity();
		image.setEventId("");
		image.setImgId(fileName);
		image.setUserId("hua");
		imageDao.save(image);
		
		return true;
	}
	
	public void clearLastNotCommitImages(String userId)
	{
		userId = "hua";
		String hql = "FROM img WHERE user_id = ?0 and event_id = ?1";
		List<ImageEntity> imageList = imageDao.findByHQL(hql, userId,"");
		for(ImageEntity image : imageList)
		{
			String fileName = image.getImgId();
			File deleteFile = new File(fileName);
			if(deleteFile.exists())
			{
				deleteFile.delete();
			}
		}
		hql = "DELETE FROM img WHERE user_id = ?0 and event_id = ?1";
		imageDao.executeHQL(hql, userId,"");
	}
	
	public void deleteImage(String imgPath, String fileName)
	{
		File deleteFile = new File(imgPath + "/" + fileName);
		if(deleteFile.exists())
			deleteFile.delete();
		ImageEntity image = imageDao.findById(fileName);
		if(image != null)
			imageDao.delete(image);
	}
}
