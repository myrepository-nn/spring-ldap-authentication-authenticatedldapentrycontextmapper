package com.nishant.spring.boot.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@Autowired
	public Repo directContextMapperRepo;

	@PostMapping("/userauth")
	public boolean getAuthenticate(@RequestBody User user) {
		return directContextMapperRepo.authenticate(user.getUsername(), user.getPassword());
	}
}