package net.kalaha.session.impl;

import static net.kalaha.common.util.Reflection.setPrivateDeclaredField;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

import net.kalaha.data.entities.Session;
import net.kalaha.data.manager.SessionManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReaperTaskTest {

	private ReaperTask task;
	private SessionManager manager;

	@BeforeMethod
	public void setup() {
		task = new ReaperTask();
		manager = mock(SessionManager.class);
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
