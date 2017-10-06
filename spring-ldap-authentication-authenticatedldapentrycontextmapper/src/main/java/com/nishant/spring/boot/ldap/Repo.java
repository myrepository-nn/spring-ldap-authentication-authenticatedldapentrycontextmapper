package com.nishant.spring.boot.ldap;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AuthenticatedLdapEntryContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapEntryIdentification;
import org.springframework.ldap.core.LdapTemplate;

public class Repo {

	@Autowired
	private LdapTemplate ldapTemplate;

	public boolean authenticate(String userDn, String password) {
		AuthenticatedLdapEntryContextMapper<DirContextOperations> mapper=null; 
		try {
			mapper = authenticatedLdapEntryContextMapper();
			DirContextOperations dircxt=ldapTemplate.authenticate(query().where("uid").is(userDn), password, mapper);
			return ((dircxt != null) && (dircxt.getStringAttribute("uid").equals(userDn)));
		} catch (Exception e) {
			System.out.println("Login failed: "+ e.getMessage());
			return false;
		}
	}

	private AuthenticatedLdapEntryContextMapper<DirContextOperations> authenticatedLdapEntryContextMapper() {
		return  (DirContext ctx, LdapEntryIdentification ldapEntryIdentification) -> {
			try {
				return (DirContextOperations) ctx.lookup(ldapEntryIdentification.getRelativeName());
			} catch (NamingException e) {
				throw new RuntimeException("Failed to lookup " + ldapEntryIdentification.getRelativeName(), e);			}
		};
	}

}
