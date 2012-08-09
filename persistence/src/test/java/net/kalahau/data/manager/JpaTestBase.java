package net.kalahau.data.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import net.kalaha.data.manager.GameManager;
import net.kalaha.data.manager.ManagerModule;
import net.kalaha.data.manager.UserManager;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;
import com.google.inject.persist.jpa.JpaPersistModule;

public abstract class JpaTestBase {

	@Inject
	protected GameManager gameManager;
	
	@Inject
	protected UserManager userManager;
	
	@Inject
	private UnitOfWork unit;
	
	@Inject
	private PersistService service;
	
	@Inject
	private Provider<EntityManager> em;
	
	@BeforeClass
	protected void setupJpa() {
		Injector inj = Guice.createInjector(new ManagerModule(), new JpaPersistModule("testUnit"));
		inj.injectMembers(this);
		service.start();
	}
	
//	@Test
//	@Transactional
//	public void testOne() {
//		em.get().persist(new User(1));
//	}
//	
//	@Test
//	@Transactional
//	public void testTwo() {
//		em.get().persist(new User(1));
//	}
	
	@BeforeMethod
	protected void beforeMethod() {
		unit.begin();
		EntityManager man = em.get();
		EntityTransaction tran = man.getTransaction();
		tran.begin();
	}
	
	@AfterMethod
	protected void afterMethod() {
		EntityManager man = em.get();
		EntityTransaction tran = man.getTransaction();
		tran.rollback();
		unit.end();
	}
	
	@AfterClass
	protected void destroyJpa() {
		service.stop();
	}
}
