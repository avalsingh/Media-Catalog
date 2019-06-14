package io.aval.service;

import java.util.List;

import io.aval.exception.MediaNotFoundException;
import io.aval.model.Media;
import io.aval.model.Type;
import io.aval.model.User;

public interface IMediaService {

	public List<Media> findAll ();
	public List<Media> findByTitle (String id);// throws MediaNotFoundException;
	Media create(Media uploadMedia);
	public List<Media> findMyMedia(User user);
	public void delete (String filename) throws MediaNotFoundException; //throws UserNotFoundException;
	public List<Media> findMediaByType(User sessionedUser, Type mediaType);
	
	public Media findMediaByFilename(String filename);
	public void updateMedia(Media uploadMedia);
	
}
