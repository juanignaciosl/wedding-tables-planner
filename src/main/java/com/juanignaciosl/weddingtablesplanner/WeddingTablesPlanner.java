package com.juanignaciosl.weddingtablesplanner;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.XmlSolverFactory;

public class WeddingTablesPlanner {

	public static void main(final String[] args) {
		System.out.println("\nSolved :\n"
				+ new WeddingTablesPlanner().planTables(
						new DiningConfigurationGenerator()
						.exampleConfiguration()).toString());

	}

	public DiningConfiguration planTables(
			final DiningConfiguration unsolvedDiningConfiguration) {
		final SolverFactory solverFactory = new XmlSolverFactory(
				"/com/juanignaciosl/weddingtablesplanner/weddingTablesPlannerConfig.xml");

		final Solver solver = solverFactory.buildSolver();

		solver.setPlanningProblem(unsolvedDiningConfiguration);

		solver.solve();

		final DiningConfiguration solvedDiningConfiguration = (DiningConfiguration) solver
				.getBestSolution();
		return solvedDiningConfiguration;
	}
}
