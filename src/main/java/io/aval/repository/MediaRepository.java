package io.aval.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import io.aval.exception.MediaNotFoundException;
import io.aval.exception.UserNotFoundException;
import io.aval.model.Media;
import io.aval.model.Type;
import io.aval.model.User;

@Repository
public class MediaRepository implements IMediaRepository{


	@PersistenceContext
	private EntityManager mEmgr;
	
	
	@Override
	public Media create(Media uploadMedia) {
		mEmgr.persist(uploadMedia);
		return null;
	}


	@Override
	public List<Media> findAll() {
		// TODO Auto-generated method stub
		TypedQuery<Media> query = mEmgr.createQuery("SELECT m FROM Media m ORDER BY m.title ASC", Media.class);
		return query.getResultList();
	}


	@Override
	public List<Media> findByTitle(String title) {
		// TODO Auto-generated method stub
		TypedQuery<Media> query = mEmgr.createQuery("SELECT m FROM Media m WHERE m.title= :pTitle", Media.class);
		query.setParameter("pTitle", title);
		return query.getResultList();
	}


	@Override
	public void deleteMedia(Media uploadMedia) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Media> findMyMedia(String id) {

		TypedQuery<Media> query = mEmgr.createQuery("SELECT m FROM Media m WHERE m._userAccount= :pId", Media.class);
		query.setParameter("pId", id);
		return query.getResultList();
		
	}
//
//	@Override
//	public List<String> findMyFiles(String id) {
//
//		TypedQuery<Media> query = mEmgr.createQuery("SELECT m.fileName FROM Media m WHERE m._userAccount= :pId", Media.class);
//		query.setParameter("pId", id);
//		
//		List<String> filenames = query.getResultList().;
//		
//		return ;
//		
//	}


	@Override
	public Media findByFilename(String filename) {
		TypedQuery<Media> query = mEmgr.createQuery("SELECT m FROM Media m WHERE m.fileName= :pfilename", Media.class);
		query.setParameter("pfilename", filename);
		
		List<Media> media = query.getResultList();
		if(media != null && media.size() == 1) {
			return media.get(0);
		}
		else {
			return null;
		}
	}


	@Override
	public void delete(Media existing) {
		
		mEmgr.remove(existing);
	}


	@Override
	public List<Media> findMediaByType(String id, Type mediaType) {
		
		TypedQuery<Media> query = mEmgr.createQuery("SELECT m FROM Media m WHERE m._userAccount= :pId AND m._type = :pType", Media.class);
		query.setParameter("pId", id);
		query.setParameter("pType", mediaType);
		return query.getResultList();
	}


	@Override
	public void updateMedia(Media uploadMedia) {
		mEmgr.merge(uploadMedia);
		
	}


	

}
