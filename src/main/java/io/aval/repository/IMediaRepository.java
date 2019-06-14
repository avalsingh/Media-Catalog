package io.aval.repository;


import java.util.List;

import io.aval.model.Media;
import io.aval.model.Type;

public interface IMediaRepository {

	Media create(Media uploadMedia);

	List<Media> findAll();

	List<Media> findByTitle(String title);

	void deleteMedia(Media uploadMedia);

	List<Media> findMyMedia(String id);

	Media findByFilename(String filename);

	void delete(Media existing);


	List<Media> findMediaByType(String id, Type mediaType);

	void updateMedia(Media uploadMedia);

	

//	List<Media> findMyFiles(String id);
	
}
