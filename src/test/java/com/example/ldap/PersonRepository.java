package com.example.ldap;

import org.springframework.data.repository.CrudRepository;

import javax.naming.Name;

/**
 * @author caohaifengx@163.com 2021-06-23 15:51
 */
public interface PersonRepository extends CrudRepository<Person, Name> {

}