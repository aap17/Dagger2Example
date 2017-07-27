package com.localhost.grok.dagger2.workClasses;

import java.util.List;

/**
 * Created by grok on 1/17/17.
 */
public interface DAO {

    void insertPersons(List<ContactJson> json);
    ContactJson selectPerson(int id);
    void updatePerson(ContactJson json, int id);
    void deletePerson(int id);



}
