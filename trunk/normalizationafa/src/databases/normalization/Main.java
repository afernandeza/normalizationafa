package databases.normalization;


public class Main {

	public static void main(String args[]){
		FunctionalDependencySet fds = new FunctionalDependencySet();
		fds.add(new FunctionalDependency("a", "b"));
		fds.add(new FunctionalDependency("c", "d"));
		fds.add(new FunctionalDependency("ac", "bd"));
		fds.add(new FunctionalDependency("e", "f"));
		fds.add(new FunctionalDependency("g", "h"));
		fds.add(new FunctionalDependency("eg", "fh"));
		fds.add(new FunctionalDependency("ej", "cig"));
		fds.add(new FunctionalDependency("gj", "cei"));
		fds.add(new FunctionalDependency("eja", "kl"));
		fds.lMinimumCover();
		
//		FunctionalDependencySet fds = new FunctionalDependencySet();
//		fds.add(new FunctionalDependency("a", "x"));
//		fds.add(new FunctionalDependency("b", "x"));		
//		fds.add(new FunctionalDependency("a", "b"));
//		fds.add(new FunctionalDependency("b", "a"));
//		fds.add(new FunctionalDependency("a", "e"));
//		fds.add(new FunctionalDependency("b", "e"));
//		fds.add(new FunctionalDependency("d", "e"));	
//		fds.add(new FunctionalDependency("e", "d"));
//		fds.add(new FunctionalDependency("e", "h"));
//		fds.add(new FunctionalDependency("ef", "g"));
//		fds.add(new FunctionalDependency("i", "y"));
//		fds.add(new FunctionalDependency("i", "e"));
//		fds.add(new FunctionalDependency("lmio", "k"));
//		fds.add(new FunctionalDependency("blmio", "p"));	
//		fds.lMinimumCover();
		
		
//		FileManager.saveFunctionalDependencySet(min, "fds.txt");
		
//		System.out.println(FileManager.loadFunctionalDependencySet("fds1.txt"));
//		System.out.println(FileManager.loadFunctionalDependencySet("fds.txt"));
	}
	
}
