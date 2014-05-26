package com.juanignaciosl.weddingtablesplanner;

import java.util.List;

public class Table {

	private final int id;
	private final int capacity;

	public Table() {
		this(-1, -1);
	}

	public Table(final int id, final int size) {
		super();
		this.id = id;
		this.capacity = size;
	}

	public int getId() {
		return id;
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Table other = (Table) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	public int exceededOccupation(final List<Guest> guests) {
		return capacity >= guests.size() ? 0 : (guests.size() - capacity);
	}

}
