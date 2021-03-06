package es.bit.tareasproyectoshex.repositoryadapters;

import java.util.Collection;
import java.util.List;

import es.bit.tareasproyectoshex.maps.UserMapper;
import es.bit.tareasproyectoshex.models.User;
import es.bit.tareasproyectoshex.models.UserJPA;
import es.bit.tareasproyectoshex.ports.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class UserJPARepositoryAdapter implements UserRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public User findOne(Long id) {
		UserJPA userJPA=em.find(UserJPA.class,id);
		System.out.println("***userJPA:"+userJPA);
		return UserMapper.INSTANCE.userJPAToUser(userJPA);
	}

	@Override
	@Transactional
	public Collection<User> findAll() {
		TypedQuery<UserJPA> q = em.createQuery("SELECT u FROM UserJPA u", UserJPA.class);
		List<UserJPA> usersJPA=q.getResultList();
		return UserMapper.INSTANCE.usersJPAToUsers(usersJPA);
	}

	@Override
	@Transactional
	public User save(User user) {
        UserJPA userJPA=UserMapper.INSTANCE.userToUserJPA(user);
        em.persist(userJPA);
		return UserMapper.INSTANCE.userJPAToUser(userJPA);
	}

	@Override
    @Transactional
    public void delete(User user) {
		em.remove(UserMapper.INSTANCE.userToUserJPA(user));
	}
}
