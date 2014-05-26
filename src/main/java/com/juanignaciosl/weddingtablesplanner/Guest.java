package com.juanignaciosl.weddingtablesplanner;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity(difficultyComparatorClass = GuestDifficultyComparator.class)
public class Guest {

	private final String id;
	private final Set<Guest> loved = new HashSet<>();
	private final Set<Guest> hated = new HashSet<>();
	private final Set<Guest> known = new HashSet<>();
	private final Set<Guest> close = new HashSet<>();

	private Table table;

	public Guest() {
		this("a");
	}

	public Guest(final String id) {
		super();
		this.id = id;
	}

	@PlanningVariable(valueRangeProviderRefs = { "tables" })
	public Table getTable() {
		return table;
	}

	public void setTable(final Table table) {
		this.table = table;
	}

	public String getId() {
		return id;
	}

	public int intenseRelationships() {
		return loved.size() + hated.size();
	}

	public Guest addLoved(final Guest person) {
		this.loved.add(person);
		return this;
	}

	public Guest addHated(final Guest person) {
		this.hated.add(person);
		return this;
	}

	public Guest addKnown(final Guest person) {
		this.known.add(person);
		return this;
	}

	public Guest addClose(final Guest person) {
		this.close.add(person);
		return this;
	}

	public Set<Guest> hated(final Set<Guest> others) {
		return others.stream().filter(other -> hated.contains(other))
				.collect(Collectors.toSet());
	}

	public Set<Guest> known(final Set<Guest> others) {
		return others.stream().filter(other -> known.contains(other))
				.collect(Collectors.toSet());
	}

	public Set<Guest> close(final Set<Guest> others) {
		return others.stream().filter(other -> close.contains(other))
				.collect(Collectors.toSet());
	}

	public Set<Guest> missingLovers(final Set<Guest> others) {
		return loved.stream()
				.filter(lovedGuest -> !others.contains(lovedGuest))
				.collect(Collectors.toSet());
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		final Guest other = (Guest) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
