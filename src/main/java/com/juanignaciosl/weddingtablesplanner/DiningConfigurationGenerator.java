package com.juanignaciosl.weddingtablesplanner;

import java.util.HashSet;
import java.util.Set;

public class DiningConfigurationGenerator {

	private static final int[] TABLE_CAPACITIES = { 6, 6 };

	public DiningConfiguration exampleConfiguration() {
		final Set<Table> tables = new HashSet<>();
		for (int i = 0; i < TABLE_CAPACITIES.length; i++) {
			tables.add(new Table(i, TABLE_CAPACITIES[i]));
		}
		final Set<Guest> guests = new HashSet<>();
		final Guest a = new Guest("a");
		final Guest b = new Guest("b");
		final Guest c = new Guest("c");
		final Guest d = new Guest("d");
		final Guest e = new Guest("e");
		final Guest f = new Guest("f");
		guests.add(a);
		guests.add(b);
		guests.add(c);
		guests.add(d);
		guests.add(e);
		guests.add(f);

		a.addKnown(b);
		a.addKnown(f);
		f.addKnown(a);
		f.addKnown(b);

		c.addKnown(d);

		b.addHated(a);
		c.addHated(a);
		d.addHated(a);
		e.addHated(a);

		return new DiningConfiguration(
				tables, guests);
	}

}
