package io.aval.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.aval.exception.UserAlreadyExistsException;
import io.aval.exception.UserNotFoundException;
import io.aval.model.User;
import io.aval.repository.IUserRepository;

@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	private IUserRepository repository;
	
	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User findOne(String id) throws UserNotFoundException {
		User user = repository.findOne(id);
		if(user == null) {
			throw new UserNotFoundException();
		}
		return user;
	}

	@Override
	public User create(User user) throws UserAlreadyExistsException {
		User existing = repository.findByEmail(user.getEmail());
		if(existing != null) {
			throw new UserAlreadyExistsException();
		}
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		return repository.create(user);
	}

	@Override
	public User update(String id, User user) throws UserNotFoundException {
		User existing = repository.findOne(id);
		if(existing == null) {
			throw new UserNotFoundException();
		}
		return repository.update(user);
	}

	@Override
	public void delete(String id) throws UserNotFoundException {
		User existing = repository.findOne(id);
		if(existing == null) {
			throw new UserNotFoundException();
		}
		repository.delete(existing);
	}
	
	@Override
	public User authenticate(String email, String password) {
		User authTemp = repository.findByEmail(email);
		if(authTemp == null) {
			return null;
		}
		if(BCrypt.checkpw(password, authTemp.getPassword())) {
			return authTemp;
		}
		return null;
	}
	
}
