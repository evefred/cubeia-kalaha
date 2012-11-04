package net.kalaha.data.entities;

/*
 * NB: DO NOT CHANGE ORDER! These enums
 * are stored by ordinal in database...
 */
public enum GameStatus {

	ACTIVE, // running
	CANCELLED, // cancelled
	FINISHED, // done
	PENDING, // challenge or invite sent

}
