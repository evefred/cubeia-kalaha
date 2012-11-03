package net.kalaha.session.impl;

import static net.kalaha.common.util.Reflection.setPrivateDeclaredField;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

import net.kalaha.data.entities.Session;
import net.kalaha.data.manager.SessionManager;
import net.kalaha.data.util.TransactionDispatch;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.inject.persist.UnitOfWork;

public class ReaperTaskTest {

	private ReaperTask task;
	private SessionManager manager;

	@BeforeMethod
	public void setup() {
		task = new ReaperTask();
		manager = mock(SessionManager.class);
		UnitOfWork work = mock(UnitOfWork.class);
		TransactionDispatch disp = new TransactionDispatch();
		setPrivateDeclaredField(disp, "work", work);
		setPrivateDeclaredField(task, "trans", disp);
		setPrivateDeclaredField(task, "sessions", manager);
		setPrivateDeclaredField(task, "maxAge", 10);
		when(manager.reapSessions(anyLong())).thenReturn(new LinkedList<Session>());
	}
	
	@Test
	public void testReap() {
		task.run();
		verify(manager).reapSessions(10);
	}
	
}
