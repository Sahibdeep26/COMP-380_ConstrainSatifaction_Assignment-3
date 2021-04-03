import java.util.Arrays;
/*
 * The driver class.
 * Creates a couple of CSPs and runs arc consistency on them.
 * Test out both binary and unary constraints.
 */

public class CSPTester {

	public static void main(String[] args) {
		System.out.println("Binary constraint test");
		// Three variables, each with the domain {1,2,3,4}
		Variable vA = new Variable("A", Arrays.asList(new Object[] { 1, 2, 3, 4 }));
		Variable vB = new Variable("B", Arrays.asList(new Object[] { 1, 2, 3, 4 }));
		Variable vC = new Variable("C", Arrays.asList(new Object[] { 1, 2, 3, 4 }));
		System.out.println("Variable domains before arc consistency");
		System.out.println(vA);
		System.out.println(vB);
		System.out.println(vC);
		Variable[] vars = { vA, vB, vC };
		// Two less-than constraints (binary constraints).
		// Same simple example shown in the lecture and book.
		LessThanConstraint c1 = new LessThanConstraint("A<B", vA, vB);
		LessThanConstraint c2 = new LessThanConstraint("B<C", vB, vC);
		Constraint[] cons = { c1, c2 };

		// Create a CSP with these Variables and Constraints.
		CSP csp = new CSP(vars, cons);
		// Run arc consistency algorithm on the CSP.
		csp.GAC();
		// After running arc consistency, the domains of the variables
		// will either be reduced or the same.
		System.out.println("Variable domains after arc consistency");
		System.out.println(vA);
		System.out.println(vB);
		System.out.println(vC);
		System.out.println();

		// Test a unary constraint too
		// (constraint involving one variable)
		System.out.println("Unary Constraint Test");
		// Three variables again
		Variable vX = new Variable("X", Arrays.asList(new Object[] { 1, 2, 3, 4 }));
		Variable vY = new Variable("Y", Arrays.asList(new Object[] { 1, 2, 3, 4 }));
		Variable vZ = new Variable("Z", Arrays.asList(new Object[] { 1, 2, 3, 4 }));
		Variable[] vars2 = { vX, vY, vZ };

		// Two binary constraints again
		LessThanConstraint c4 = new LessThanConstraint("X<Y", vX, vY);
		LessThanConstraint c5 = new LessThanConstraint("Y<Z", vY, vZ);
		// One unary constraint -
		// Says Z has to be less than 4
		InvalidUnaryValueConstraint c6 = new InvalidUnaryValueConstraint("Z<4", vZ);
		Constraint[] cons2 = { c4, c5, c6 };
		System.out.println("Variable domains before arc consistency");
		System.out.println(vX);
		System.out.println(vY);
		System.out.println(vZ);
		// Create a second CSP.
		CSP csp2 = new CSP(vars2, cons2);
		// Run arc consistency on it.
		csp2.GAC();
		// Check the variable domains after running arc consistency.
		System.out.println("Variable domains after arc consistency");
		System.out.println(vX);
		System.out.println(vY);
		System.out.println(vZ);
	}
}