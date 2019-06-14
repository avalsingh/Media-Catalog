package io.aval.service;

import java.util.List;

import io.aval.exception.UserAlreadyExistsException;
import io.aval.exception.UserNotFoundException;
import io.aval.model.User;

public interface IUserService {

	public List<User> findAll ();
	public User findOne (String id) throws UserNotFoundException;
	public User create (User user) throws UserAlreadyExistsException;
	public User update (String id, User user) throws UserNotFoundException;
	public void delete (String id) throws UserNotFoundException;
	public User authenticate (String email, String password);
}
