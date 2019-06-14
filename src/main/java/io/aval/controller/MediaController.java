package io.aval.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import io.aval.exception.FileNotDeletedException;
import io.aval.exception.MediaNotFoundException;
import io.aval.model.Media;
import io.aval.model.Type;
import io.aval.model.User;
import io.aval.service.IMediaService;

@RestController
//@RequestMapping("/files")
public class MediaController {



	
	@Autowired
	IMediaService mService;

	
	public static String uploadDirectory = "/tmp/uploads";
	
	
	@RequestMapping(value="/files", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ModelAndView uploadForm() {
		new File(uploadDirectory).mkdir();
		ModelAndView viewMav = new ModelAndView("NewUpload");
		return viewMav;
	}
	
	@RequestMapping(value="/home.jsp", method = RequestMethod.GET)
	public ModelAndView home(Map<String, List<Media>> model, HttpSession session) {
		
		ModelAndView viewMav = new ModelAndView();
		
		User sessionedUser = (User) session.getAttribute("user");
		if(sessionedUser!= null)
		{
			List<Media> mediaFiles = mService.findMyMedia(sessionedUser);
			model.put("mediaList", mediaFiles);
			viewMav.setViewName("index");
			
		}
		else
		{
			viewMav.setViewName("forward:/logout.htm");
		}
		return viewMav;
	}
	
	
	@RequestMapping(value="/media/{pageType}.jsp", method = RequestMethod.GET)
	public ModelAndView images(@PathVariable String pageType, Map<String, List<Media>> model, HttpSession session) {
		
		ModelAndView viewMav = new ModelAndView();
		Type mediaType;
		
		User sessionedUser = (User) session.getAttribute("user");
		if(sessionedUser!= null)
		{
			if(pageType.contains("audio")) {
				mediaType = Type.AUDIO;
				System.out.println("AUDIO");
			}
			else if(pageType.contains("video")) {
				mediaType = Type.VIDEO;}
			else if(pageType.contains("image")) {
				mediaType = Type.IMAGE;}
			else {
				mediaType = Type.OTHER;
				System.out.println("other");
			}
			List<Media> mediaFiles = mService.findMediaByType(sessionedUser, mediaType);
			
			model.put("mediaList", mediaFiles);
			viewMav.setViewName(pageType);
			
		}
		else
		{
			viewMav.setViewName("forward:/logout.htm");
		}
		return viewMav;
	}	
	
	
	
	@RequestMapping(value = "/updateMedia/{filename:.+}", method = RequestMethod.POST)
	public ModelAndView updateMedia(@PathVariable String filename, HttpSession session,HttpServletRequest request, Map<String, List<Media>> model) throws FileNotDeletedException, MediaNotFoundException {
	
		ModelAndView viewMav = new ModelAndView();
		
		User sessionedUser = (User) session.getAttribute("user");
		if(sessionedUser!= null)
		{
			Path filePath = Paths.get("/tmp/uploads", filename);
			
			Media uploadMedia = mService.findMediaByFilename(filename);
			uploadMedia.setTitle(request.getParameter("input_title"));
			uploadMedia.setDescription(request.getParameter("input_description"));
			
			mService.updateMedia(uploadMedia);
			
			
			List<Media> mediaFiles = mService.findMyMedia(sessionedUser);
			model.put("mediaList", mediaFiles);
			viewMav.setViewName("index");
			
			
		}
		else {
			viewMav.setViewName("forward:/logout.htm");
		}
		
		return viewMav;
	}
	
	
//	@RequestMapping(value = "/updateMedia/{filename:.+}", method = RequestMethod.GET)
//	public ModelAndView updateMedia(@PathVariable String filename, HttpSession session,HttpServletRequest request, Map<String, List<Media>> model) throws FileNotDeletedException, MediaNotFoundException {
//	
//		ModelAndView viewMav = new ModelAndView();
//		
//		User sessionedUser = (User) session.getAttribute("user");
//		if(sessionedUser!= null)
//		{
//			Path filePath = Paths.get("/tmp/uploads", filename);
//			
//			Media uploadMedia = mService.findMediaByFilename(filename);
//			uploadMedia.setTitle(request.getParameter("input_title"));
//			uploadMedia.setDescription(request.getParameter("input_description"));
//			
//			List<Media> mediaFiles = mService.findMyMedia(sessionedUser);
//			model.put("mediaList", mediaFiles);
//			viewMav.setViewName("index3");
//		}
//		else {
//			viewMav.setViewName("forward:/logout.htm");
//		}
//		
//		return viewMav;
//	}
	

	@RequestMapping(value = "/delete/{filename:.+}", method = RequestMethod.GET)
	public ModelAndView deleteMedia(@PathVariable String filename, HttpSession session, Map<String, List<Media>> model) throws FileNotDeletedException, MediaNotFoundException {
	
		ModelAndView viewMav = new ModelAndView();
		
		User sessionedUser = (User) session.getAttribute("user");
		if(sessionedUser!= null)
		{
			Path filePath = Paths.get("/tmp/uploads", filename);
			
			mService.delete(filename);
			
			File file = new File(filePath.toString());
			if(!file.delete()){
				throw new FileNotDeletedException();
			}
			List<Media> mediaFiles = mService.findMyMedia(sessionedUser);
			model.put("mediaList", mediaFiles);
			viewMav.setViewName("index");
		}
		else {
			viewMav.setViewName("forward:/logout.htm");
		}
		
		return viewMav;
	}
	
	@RequestMapping(value= "/upload",method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ModelAndView upload(HttpSession session,HttpServletRequest request,  @RequestParam("files") MultipartFile[] files){

		ModelAndView viewMav = new ModelAndView();
		User sessionedUser = (User) session.getAttribute("user");		
//		
//		if(sessionedUser!= null)
//		{
		
			Media uploadMedia = new Media();
			String mediaType;
			
			StringBuilder fileNames = new StringBuilder();
			if (files != null && files.length > 0) {			
				for (MultipartFile file : files) {	
					Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
					fileNames.append(file.getOriginalFilename()+" ");
					
					uploadMedia.setFileName(file.getOriginalFilename());
					uploadMedia.setFilePath(fileNameAndPath.toString());
					uploadMedia.set_userAccount(sessionedUser.getId());
					uploadMedia.setTitle(request.getParameter("input_title"));
					uploadMedia.setDescription(request.getParameter("input_description"));
					mediaType = file.getContentType();
					System.out.println(file.getContentType());
					
					if(mediaType.contains("audio")) {
						uploadMedia.set_type(Type.AUDIO);}
					else if(mediaType.contains("video")) {
						uploadMedia.set_type(Type.VIDEO);}
					else if(mediaType.contains("image")) {
						uploadMedia.set_type(Type.IMAGE);}
					else {
						uploadMedia.set_type(Type.OTHER);
					}
					mService.create(uploadMedia);
					
					
					try {
						Files.write(fileNameAndPath, file.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			viewMav.setViewName("Success");
			
//		}
//		else {
//			viewMav.setViewName("forward:/logout.htm");
//		}
//		
		
		return viewMav;
	}

//	
//	@RequestMapping("/uploads/{filename:.+}")
//	public Resource serveFile(@PathVariable String filename) {
//		
//		
//		Path fileNameAndPath = Paths.get(uploadDirectory, filename);
//		Resource resource;
//		try {
//			resource = new UrlResource(fileNameAndPath.toUri());
//			if(resource.exists() || resource.isReadable()) {
//				return resource;
//				}
//			} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		return null;
//	}


}
