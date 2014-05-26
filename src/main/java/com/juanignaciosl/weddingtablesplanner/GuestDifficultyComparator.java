package com.juanignaciosl.weddingtablesplanner;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;

public class GuestDifficultyComparator implements Comparator<Guest> {
	@Override
	public int compare(final Guest a, final Guest b) {
		return new CompareToBuilder()
				.append(a.intenseRelationships(), b.intenseRelationships())
				.append(a.getId(), b.getId()).toComparison();
	}
}
