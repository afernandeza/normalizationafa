package databases.normalization;

import org.junit.Test;

public class FunctionalDependencySetTest {

	@Test
	public void testMinimumCover(){
		FunctionalDependencySet fds = new FunctionalDependencySet();
		fds.add(new FunctionalDependency("a", "b"));
		fds.add(new FunctionalDependency("c", "d"));
		fds.add(new FunctionalDependency("e", "f"));
		fds.add(new FunctionalDependency("g", "h"));
		fds.add(new FunctionalDependency("ej", "cig"));
		fds.add(new FunctionalDependency("gj", "cei"));
		fds.add(new FunctionalDependency("eja", "kl"));
		fds.lMinimumCover();
	}
	
	//@Test
	public void testTransitiveClosure() {
		FunctionalDependencySet fds = new FunctionalDependencySet();
		fds.add(new FunctionalDependency("a", "b"));
		fds.add(new FunctionalDependency("c", "d"));
		fds.add(new FunctionalDependency("e", "f"));
		fds.add(new FunctionalDependency("g", "h"));
		fds.add(new FunctionalDependency("ej", "cig"));
		fds.add(new FunctionalDependency("gj", "cei"));
		fds.add(new FunctionalDependency("eja", "kl"));

		System.out.println(fds);
		System.out.println("Test 1");
		System.out.println(FunctionalDependencySet.transitiveClosure("a", fds));
		System.out.println("Test 2");
		System.out.println(FunctionalDependencySet.transitiveClosure("e", fds));
		System.out.println("Test 3");
		System.out.println(FunctionalDependencySet.transitiveClosure("g", fds));
		System.out.println("Test 4");
		System.out.println(FunctionalDependencySet.transitiveClosure("ej", fds));
		System.out.println("Test 5");
		System.out.println(FunctionalDependencySet.transitiveClosure("c", fds));
		System.out.println("Test 6");
		System.out.println(FunctionalDependencySet.transitiveClosure("gj", fds));
		System.out.println("Test 7");
		System.out.println(FunctionalDependencySet.transitiveClosure("aej", fds));
		
		FunctionalDependencySet fds2 = new FunctionalDependencySet();
		fds2.add(new FunctionalDependency("a", "b"));
		fds2.add(new FunctionalDependency("c", "d"));
		fds2.add(new FunctionalDependency("e", "f"));
		fds2.add(new FunctionalDependency("g", "h"));
		fds2.add(new FunctionalDependency("je", "gic"));
		fds2.add(new FunctionalDependency("jg", "iec"));
		fds2.add(new FunctionalDependency("aej", "lk"));
		
		System.out.println(fds.equals(fds2));
	}

}
