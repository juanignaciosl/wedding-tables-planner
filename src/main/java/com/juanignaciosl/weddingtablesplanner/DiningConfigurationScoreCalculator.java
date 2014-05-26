package com.juanignaciosl.weddingtablesplanner;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.simple.SimpleScoreCalculator;

public class DiningConfigurationScoreCalculator implements
		SimpleScoreCalculator<DiningConfiguration> {

	@Override
	public HardSoftScore calculateScore(final DiningConfiguration configuration) {
		final int hardScore = configuration.tableOccupationExceedingCapacity()
				+ configuration.hatedRelationsInTheSameTable()
				+ configuration.lovedRelationsNotInTheSameTable();

		final int softScore = 4 * configuration.closeRelationsInTheSameTable()
				+ 2 * configuration.knownRelationsInTheSameTable()
				- configuration.neededTables().size();

		return HardSoftScore.valueOf(-hardScore, softScore);
	}

}
