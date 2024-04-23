import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class LinearSolver {

    public static double solveShortestPath(Graph graph) {
        int numVertices = graph.getNbVertices();
        int destination = graph.getDestination();
        int source = graph.getSource();
        List<List<AbstractMap.SimpleEntry<Integer, Double>>> adjacencyList = graph.getAdjacencyList();

        // Création de la fonction objectif
        double[] coefficients = new double[numVertices];
        for (int i = 0; i < numVertices; i++) {
            if (i == destination) {
                coefficients[i] = 1; // Coefficient 1 pour la destination (maximisation de la fonction objectif)
            } else if (i == source) {
                coefficients[i] = -1; // Coefficient -1 pour la source (minimisation de la distance source-destination)
            }else {
                coefficients[i] = 0;
            }
        }
        LinearObjectiveFunction objectiveFunction = new LinearObjectiveFunction(coefficients, 0);

        // Création des contraintes
        List<LinearConstraint> constraints = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            if( i != destination) {
                double[] constraintCoefficients = new double[numVertices];
                int cptConstraintCoefficient = 0;
                for (AbstractMap.SimpleEntry<Integer, Double> pair : adjacencyList.get(i)) {
                    double weight = pair.getValue();
                    if(weight > 0) {
                        constraintCoefficients[cptConstraintCoefficient] = -1 * weight;
                        cptConstraintCoefficient++;
                    }
                }
                constraints.add(new LinearConstraint(constraintCoefficients, Relationship.LEQ, 0));
            }
        }
        constraints.add(new LinearConstraint(new double[]{1}, Relationship.EQ, 0)); // Contrainte initiale

        // Résolution du problème d'optimisation linéaire
        SimplexSolver solver = new SimplexSolver();
        return solver.optimize(objectiveFunction, new NonNegativeConstraint(true), new LinearConstraintSet(constraints), GoalType.MINIMIZE).getValue();
    }
}
