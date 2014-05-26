package com.juanignaciosl.weddingtablesplanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.value.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.solution.Solution;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;

@PlanningSolution
public class DiningConfiguration implements Solution<HardSoftScore> {

	private Set<Table> tables;
	private Set<Guest> guests;

	private HardSoftScore score;

	public DiningConfiguration() {
		this(new HashSet<>(), new HashSet<>());
	}

	public DiningConfiguration(final Set<Table> tables, final Set<Guest> guests) {
		super();
		setTables(tables);
		setGuests(guests);
	}

	@ValueRangeProvider(id = "tables")
	public Set<Table> getTables() {
		return tables;
	}

	public void setTables(final Set<Table> tables) {
		this.tables = tables == null ? null : ImmutableSet.copyOf(tables);
	}

	@PlanningEntityCollectionProperty
	public Set<Guest> getGuests() {
		return guests;
	}

	public void setGuests(final Set<Guest> guests) {
		this.guests = guests == null ? null : ImmutableSet.copyOf(guests);
	}

	@Override
	public HardSoftScore getScore() {
		return score;
	}

	@Override
	public void setScore(final HardSoftScore score) {
		this.score = score;
	}

	@Override
	public Collection<? extends Object> getProblemFacts() {
		final List<Object> facts = new ArrayList<Object>();
		facts.addAll(tables);
		return facts;
	}

	public int hatedRelationsInTheSameTable() {
		return guests
				.parallelStream()
				.mapToInt(
						guest -> guest.hated(sharingTableGuests(guest)).size())
						.sum();
	}

	private Set<Guest> sharingTableGuests(final Guest guest) {
		return guests
				.stream()
				.filter(aGuest -> aGuest.getTable() != null)
				.filter(tableGuest -> !guest.equals(tableGuest))
				.filter(otherGuest -> otherGuest.getTable().equals(
						guest.getTable())).collect(Collectors.toSet());
	}

	public int tableOccupationExceedingCapacity() {
		return guestsPerTable()
				.entrySet()
				.stream()
				.mapToInt(
						guestsAtTable -> guestsAtTable.getKey()
						.exceededOccupation(guestsAtTable.getValue()))
						.sum();
	}

	public Map<Table, List<Guest>> guestsPerTable() {
		return guests.stream().filter(guest -> guest.getTable() != null)
				.collect(Collectors.groupingBy(Guest::getTable));
	}

	public int lovedRelationsNotInTheSameTable() {
		return guests
				.parallelStream()
				.mapToInt(
						guest -> guest.missingLovers(sharingTableGuests(guest))
						.size()).sum();
	}

	public int knownRelationsInTheSameTable() {
		return guests
				.parallelStream()
				.mapToInt(
						guest -> guest.known(sharingTableGuests(guest)).size())
						.sum();
	}

	public int closeRelationsInTheSameTable() {
		return guests
				.parallelStream()
				.mapToInt(
						guest -> guest.close(sharingTableGuests(guest)).size())
						.sum();
	}

	@Override
	public String toString() {
		final Map<Table, List<Guest>> guestsPerTable = guestsPerTable();
		final StringBuilder sb = new StringBuilder("Score: " + score + "\n");
		for (final Entry<Table, List<Guest>> guestsAtTable : guestsPerTable
				.entrySet()) {
			sb.append("Table " + guestsAtTable.getKey().getId() + ": "
					+ Joiner.on(", ").join(guestsAtTable.getValue()) + "\n");
		}
		return sb.toString();
	}

	public Set<Table> neededTables() {
		return guests.stream().map(guest -> guest.getTable())
				.collect(Collectors.toSet());
	}

}
