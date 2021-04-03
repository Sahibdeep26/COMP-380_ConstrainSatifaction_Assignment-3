
// ONLY EDIT THE GAC() METHOD BELOW
import java.util.List;
import java.util.ArrayList;

/*
 * COMP-380
 * Sahibdeep Singh  
 */

/*
 * A class representing a CSP.
 * A CSP consists of Constraints and Arcs to the Variables involved.
 */

public class CSP {

	// private Variable[] vars;
	private Constraint[] cons;
	private ArrayList<Arc> toDoArcs;

	// Constructor
	public CSP(Variable[] v, Constraint[] c) {
		// vars = v;
		cons = c;
		toDoArcs = new ArrayList<Arc>();
		for (Constraint constraint : cons) {
			for (Variable variable : constraint.getVariables()) {
				Arc arc = new Arc(constraint, variable);
				toDoArcs.add(arc);
			}
		}
	}

	/*
	 * For binary constraints, return the other variable involved in Constraint
	 * c with Variable v1.
	 */
	public Variable getOtherVariable(Variable v1, Constraint c) {
		List<Variable> allConVars = c.getVariables();

		if (allConVars.size() == 2) {
			if (v1.equals(allConVars.get(0)))
				return allConVars.get(1);
			else if (v1.equals(allConVars.get(1)))
				return allConVars.get(0);
		}
		return null;
	}

	/*
	 * This is the only method you need to implement. Follow the provided
	 * pseudo-code.
	 */
	public void GAC() {
		// WHILE toDoArcs is not empty:
		while (!toDoArcs.isEmpty()) {

			// GET an Arc from toDoArcs
			Arc curArc = toDoArcs.get(0);
			toDoArcs.remove(curArc);

			// Arc involves some Variable X and Constraint C
			Variable X = curArc.getVariable();
			Constraint C = curArc.getConstraint();

			// dx is domain of X
			List<Object> dx = X.getDomain();

			// ndx will be new domain of X
			List<Object> ndx = new ArrayList<Object>();

			// FOR EACH value val in dx:
			for (Object val : dx) {

				// boolean satisfied = false;
				Boolean satisfied = false;

				// create new Assignment
				Assignment newAssignment = new Assignment();

				// SET assignment of X to val
				newAssignment.setAssignment(X, val);

				// IF C is binary constraint, constraint over a pair of variable
				if (C.getVariables().size() == 2) {

					// GET other variable O involved in C
					Variable O = C.getVariables().get(0);

					// if 0 or last var is X
					if (O.getID().equals(X.getID())) {
						// then get other var
						O = C.getVariables().get(1);
					}
					// dO is domain of O
					List<Object> d0 = O.getDomain();

					// FOR each value val2 in dO:
					for (Object val2 : d0) {

						// SET assignment of O to val2
						newAssignment.setAssignment(O, val2);

						// IF C constraint is satisfied:
						if (C.constraintIsSatisfied(newAssignment)) {
							satisfied = true;
							break;
						}
					}
				}

				// else: (is unary constraint, constraint on a single variable)
				else {
					// IF C constraint is satisfied with the newAssignment
					if (C.constraintIsSatisfied(newAssignment)) {
						satisfied = true;
					}
				}

				if (satisfied) {
					// ADD val to ndx
					ndx.add(val);
				}

			}

			// IF size of ndx < size of dx: (values have been removed)
			if (ndx.size() < dx.size()) {

				// set domain of v to ndx
				X.setDomain(ndx);

				// Run through all of the X's constraints
				for (Constraint otherCon : X.getConstraints()) {

					if (C.getID().equals(X.getID())) {
						continue;
					}

					for (Variable otherVar : otherCon.getVariables()) {
						if (otherVar.getID().equals(X.getID())) {
							continue;
						}
						toDoArcs.add(new Arc(otherCon, otherVar));
					}
				}

			}

		}

	}
}
