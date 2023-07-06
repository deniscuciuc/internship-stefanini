package org.dcuciuc.profile;

import org.dcuciuc.dao.AbstractDAO;

/**
 * This Profile DAO is responsible for profiles of users.
 * Each user should have one-to-one relationship with profile
 *
 * @param <Profile>
 * @author dcuciuc
 */
public interface ProfileDAO<Profile> extends AbstractDAO<Profile> {

}
