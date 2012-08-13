package net.kalaha.common.elo;

public class UscfKSelector implements KSelector {

	@Override
	public int selectK(double rating) {
		if(rating < 2100) {
			return 32;
		} else if(rating < 2400) {
			return 24;
		} else {
			return 16;
		}
	}
}
