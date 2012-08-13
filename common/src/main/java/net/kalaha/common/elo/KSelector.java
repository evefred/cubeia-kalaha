package net.kalaha.common.elo;

import com.google.inject.ImplementedBy;

@ImplementedBy(UscfKSelector.class)
public interface KSelector {

	public int selectK(double rating);
	
}
