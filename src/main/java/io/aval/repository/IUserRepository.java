package io.aval.repository;

import java.util.List;

import io.aval.model.User;

public interface IUserRepository {
	public List<User> findAll ();
	public User findOne (String id);
	public User findByEmail (String email);
	public User create (User user);
	public User update (User user);
	public void delete (User user);
}
