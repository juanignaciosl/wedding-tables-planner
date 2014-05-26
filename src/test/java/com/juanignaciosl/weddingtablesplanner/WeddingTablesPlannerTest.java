package com.juanignaciosl.weddingtablesplanner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class WeddingTablesPlannerTest {

	private WeddingTablesPlanner planner;
	private Set<Guest> guests;
	private Set<Table> tables;
	private Guest a;
	private Guest b;
	private Guest c;
	private Guest d;
	private Guest blackSheep;

	@Before
	public void init() {
		this.planner = new WeddingTablesPlanner();

		this.guests = new HashSet<>();
		this.tables = new HashSet<>();

		this.a = new Guest("a");
		this.b = new Guest("b");
		this.c = new Guest("c");
		this.d = new Guest("d");

		this.blackSheep = new Guest("black sheep");
	}

	@Test
	public void emptyWedding() {
		assertTrue(planner.planTables(emptyConfiguration()).getTables()
				.isEmpty());
	}

	@Test
	public void singleGuest() {
		final DiningConfiguration solution = planner
				.planTables(singleConfiguration());
		final Set<Table> tables = solution.getTables();
		final Set<Guest> guests = solution.getGuests();
		assertThat(tables.size(), is(equalTo(1)));
		assertThat(guests.iterator().next().getTable(), is(equalTo(tables
				.iterator().next())));
	}

	@Test
	public void twoLovingGuests() {
		final DiningConfiguration solution = planner
				.planTables(twoLovingGuestsConfiguration());
		assertThat(solution.neededTables().size(), is(equalTo(1)));
	}

	@Test
	public void twoHatingGuests() {
		final DiningConfiguration solution = planner
				.planTables(twoHatingGuestsConfiguration());
		assertThat(solution.neededTables().size(), is(equalTo(2)));
	}

	@Test
	public void twoKnownGuests() {
		final DiningConfiguration solution = planner
				.planTables(twoKnownGuestsConfiguration());
		assertThat(solution.neededTables().size(), is(equalTo(1)));
	}

	@Test
	public void twoGroupsOfFriendsWithOneBlackSheep() {
		final DiningConfiguration solution = planner
				.planTables(twoGroupsOfFriendsWithCommonBlackSheepConfiguration());

		final Set<Guest> guests = solution.getGuests();
		assertThat(solution.neededTables().size(), is(equalTo(3)));

		final Set<Guest> notBlackSheeps = guests.stream()
				.filter(guest -> !guest.equals(blackSheep))
				.collect(Collectors.toSet());

		final Set<Table> notBlackSheepsTables = notBlackSheeps.stream()
				.map(guest -> guest.getTable()).collect(Collectors.toSet());
		assertFalse(notBlackSheepsTables.contains(blackSheep.getTable()));
		assertNotNull(guests.stream()
				.filter(guest -> guest.getId().equals(blackSheep.getId()))
				.findFirst().get().getTable());
	}

	@Test
	public void minimizeTables() {
		assertThat(planner.planTables(groupOf4FriendsWith4CappacityTables())
				.neededTables().size(), is(equalTo(1)));
	}

	@Test
	public void beingCloseIsMoreImportantThanBeingKnown() {
		final Collection<List<Guest>> groupedGuests = planner
				.planTables(aKnowsBAndIsCloseToC()).guestsPerTable().values();

		assertThat(groupedGuests.size(), is(equalTo(2)));
		for (final List<Guest> guests : groupedGuests) {
			assertTrue(
					guests.toString(),
					(guests.contains(a) && !guests.contains(b) && guests
							.contains(c))
							|| (!guests.contains(a) && guests.contains(b) && !guests
									.contains(c)));
		}
	}

	private DiningConfiguration aKnowsBAndIsCloseToC() {
		guests.add(a);
		guests.add(b);
		guests.add(c);
		a.addKnown(b);
		a.addClose(c);

		tables.add(new Table(1, 2));
		tables.add(new Table(2, 2));

		return new DiningConfiguration(tables, guests);
	}

	private DiningConfiguration groupOf4FriendsWith4CappacityTables() {
		guests.add(a);
		guests.add(b);
		guests.add(c);
		guests.add(d);
		a.addKnown(b).addKnown(c).addKnown(d);
		b.addKnown(a).addKnown(c).addKnown(d);
		c.addKnown(b).addKnown(a).addKnown(d);
		d.addKnown(b).addKnown(c).addKnown(a);

		tables.add(new Table(1, 4));
		tables.add(new Table(2, 4));
		tables.add(new Table(3, 4));

		return new DiningConfiguration(tables, guests);
	}

	private DiningConfiguration twoGroupsOfFriendsWithCommonBlackSheepConfiguration() {
		guests.add(a);
		guests.add(b);
		guests.add(c);
		guests.add(d);
		guests.add(blackSheep);

		a.addKnown(b);
		b.addKnown(a);

		c.addKnown(d);
		d.addKnown(c);

		a.addHated(blackSheep);
		c.addHated(blackSheep);

		tables.add(new Table(1, 3));
		tables.add(new Table(2, 3));
		tables.add(new Table(3, 3));

		return new DiningConfiguration(tables, guests);
	}

	private DiningConfiguration twoKnownGuestsConfiguration() {
		guests.add(a);
		guests.add(b);

		a.addKnown(b);

		tables.add(new Table(1, 2));
		tables.add(new Table(2, 2));

		return new DiningConfiguration(tables, guests);
	}

	private DiningConfiguration twoHatingGuestsConfiguration() {
		guests.add(a);
		guests.add(b);

		a.addHated(b);
		b.addHated(a);

		tables.add(new Table(1, 2));
		tables.add(new Table(2, 2));

		return new DiningConfiguration(tables, guests);
	}

	private DiningConfiguration twoLovingGuestsConfiguration() {
		guests.add(a);
		guests.add(b);

		a.addLoved(b);
		b.addLoved(a);

		tables.add(new Table(1, 2));
		tables.add(new Table(2, 2));

		return new DiningConfiguration(tables, guests);
	}

	private DiningConfiguration singleConfiguration() {
		guests.add(a);
		tables.add(new Table(1, 1));
		return new DiningConfiguration(tables, guests);
	}

	private DiningConfiguration emptyConfiguration() {
		return new DiningConfiguration();
	}

}
