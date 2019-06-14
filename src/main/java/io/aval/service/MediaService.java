package io.aval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.aval.exception.MediaNotFoundException;
import io.aval.exception.UserNotFoundException;
import io.aval.model.Media;
import io.aval.model.Type;
//import io.aval.repository.IMediaRepository;
import io.aval.model.User;
import io.aval.repository.IMediaRepository;

@Service
@Transactional
public class MediaService implements IMediaService{
	
	@Autowired
	IMediaRepository mRepository;	
	

	@Override
	public Media create(Media uploadMedia) {
		
		return mRepository.create(uploadMedia);
	}


	@Override
	public List<Media> findAll() {
		mRepository.findAll();
		return null;
	}


	@Override
	public List<Media> findByTitle(String title) {

		return mRepository.findByTitle(title);

	}


	@Override
	public void delete(String filename) throws MediaNotFoundException {
		
		Media existing = mRepository.findByFilename(filename);
		if(existing == null) {
			throw new MediaNotFoundException();
		}
		mRepository.delete(existing);
		
	}


	@Override
	public List<Media> findMyMedia(User user) {
		
		return mRepository.findMyMedia(user.getId());
	}


	@Override
	public List<Media> findMediaByType(User user, Type mediaType) {
		
		return mRepository.findMediaByType(user.getId(), mediaType);
	}


	@Override
	public Media findMediaByFilename(String filename) {
		return mRepository.findByFilename(filename);
	}


	@Override
	public void updateMedia(Media uploadMedia) {
		mRepository.updateMedia(uploadMedia);
		
	}


//	@Override
//	public void delete(String title) {
//		
//		Media uploadMedia = findByTitle(title);
//		
//		mRepository.deleteMedia(uploadMedia);
//	}
}
