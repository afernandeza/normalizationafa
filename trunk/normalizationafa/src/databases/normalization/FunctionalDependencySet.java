package databases.normalization;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Represents a set of functional dependencies. 
 *
 */
public class FunctionalDependencySet extends HashSet<FunctionalDependency>{

	private static FunctionalDependencySet triggeredFds = null;
	static final long serialVersionUID = -12341234;
	
	public FunctionalDependencySet(){
		super();
	}
	
	/**
	 * Determines whether this equals to some other object.
	 * Two functional dependencies are equal when they are equivalent.
	 * This happens when two dependency sets cover one another.
	 */
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(!(o instanceof FunctionalDependencySet))
			return false;
		FunctionalDependencySet fds = (FunctionalDependencySet)o;
		return this.equivalent(fds);
	}
	
	private boolean equivalent(FunctionalDependencySet fds){
		
		/*
		 * Checking whether fds covers this fds
		 * For each fd X-> Y in this fds determine whether Y is in 
		 * the transitive closure of X based on fds
		 */
		for(FunctionalDependency fd: this){
			String trans = transitiveClosure(fd.getLhs(), fds);
			for(Character c: fd.getRhs().toCharArray()){
				if(!trans.contains(c+""))
					return false;
			}
		}
		
		/*
		 * Checking whether this fds covers fds
		 * For each fd X-> Y in fds determine whether Y is in 
		 * the transitive closure of X based on this fds
		 */
		for(FunctionalDependency fd: fds){
			String trans = transitiveClosure(fd.getLhs(), this);
			for(Character c: fd.getRhs().toCharArray()){
				if(!trans.contains(c+""))
					return false;
			}
		}
		return true;
	}	
	
	/**
	 * Calculates the transitive closure of lhs based on the given fds set.
	 * @param lhs the attribute of which the closure will be computed
	 * @param fds the set of functional dependencies used to calculate the closure
	 * @return the resulting transitive closure
	 */
	public static String transitiveClosure(String lhs, FunctionalDependencySet fds){
		triggeredFds = new FunctionalDependencySet();
		StringBuffer st1 = new StringBuffer(lhs + transitiveClosureRec(lhs, fds));
		String s = st1.toString();
		
		StringBuffer res = new StringBuffer();
		for(int i = 0; i < s.length(); i++){
			if(!res.toString().contains(""+s.charAt(i))){
				res.append(s.charAt(i));
			}
		}
		return res.toString();
	}
	
	private static String transitiveClosureRec(String lhs, FunctionalDependencySet fds){
		StringBuffer sb = new StringBuffer();
		for(FunctionalDependency fd: fds){
			if(fd.isTriggeredBy(lhs) && !triggeredFds.contains(fd)){
				triggeredFds.add(fd);
				String rhs = fd.getRhs();
				for(int i = 0; i < rhs.length(); i++){
					if(!sb.toString().contains(""+ rhs.charAt(i))){
						//System.out.println("Agregando " + rhs + " en: " + sb.toString());
						sb.append(rhs.charAt(i));
					}
				}
				String s = sb.toString();
				sb.append(transitiveClosureRec(s, fds));
			}
		}
		return sb.toString();
	}
	
	/**
	 * Calculates the L-minimum cover of this functional dependency set
	 * using its attribute-closed set.
	 * @return
	 */
	public FunctionalDependencySet lMinimumCover(){
		FunctionalDependencySet fds = this.attributeClosedSet();
		FunctionalDependencySet attClosedSet = this.attributeClosedSet();
		int redundancies = 0;
		
		System.out.println("1. Calculating the attribute closed set.");
		System.out.println();
		System.out.println("F = " + this);
		System.out.println("Att-closed set of F = " + fds);
		System.out.println();
		System.out.println("2. Attempting to remove redundant dependencies.");
		
		FunctionalDependencySet set = (FunctionalDependencySet) fds.clone();
		for(FunctionalDependency fd: fds){
			if(redundant(fd, fds)){
				redundancies++;
				set.remove(fd);
				fds = set;
			}
			
		}
		
		System.out.println(redundancies + " redundancies found.");
		
		System.out.println();
		System.out.println("3. Minimizing left hand sides.");
		FunctionalDependencySet lminfds = (FunctionalDependencySet) fds.cloneWithFds();
		for(FunctionalDependency fd: lminfds){
			for(int i = 0; i < fd.getLhs().length() && !fd.isLMinimum(); i++){
				String att = fd.removeAttributeLhs(i);
				System.out.println("Attempting to remove " + att +
						" from " + fd.originalToString());
				if(lminfds.equals(fds)){
					System.out.println("It will be removed.");
				} else {
					System.out.println("It can't be removed.");
					fd.restoreLhs();
				}
			}
		}
		
		System.out.println();
		System.out.println("4. Results.");
		System.out.println("F = " + this);	
		System.out.println("Att-closed set of F = " + attClosedSet);		
		System.out.println("Minimum cover of F = " + fds);
		System.out.println("L-minimum cover of F = " + lminfds);
		
		return fds;
	}
	
	/**
	 * Checks whether a given fd is redundant in the given fds
	 */
	public boolean redundant(FunctionalDependency fd, FunctionalDependencySet fds){
		System.out.println("Checking whether " + fd + " is redundant in " + fds);
		FunctionalDependencySet fdsWithoutFd = (FunctionalDependencySet)fds.clone();
		fdsWithoutFd.remove(fd);
		
		if(! fds.equals(fdsWithoutFd)){
			System.out.println(fd + " is not redundant.");
			return false;
		} else {
			System.out.println(fd + " is redundant. " +
					"It will be removed.");
			return true;
		}
	}
	
	/**
	 * Computes the attribute-closed set of this functional dependency
	 * set.
	 * @return the attribute-closed set.
	 */
	public FunctionalDependencySet attributeClosedSet(){
		FunctionalDependencySet fds = new FunctionalDependencySet();
		for(FunctionalDependency fd: this){
			String trans = transitiveClosure(fd.getLhs(), this);
			fds.add(new FunctionalDependency(fd.getLhs(), trans));
		}
		return fds;
	}
	
	/**
	 * Returns a string representation of this set.
	 */
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer(super.toString());
		sb.replace(0, 1, "{");
		sb.replace(sb.length()-1, sb.length(), "}");
		return sb.toString();
	}
	
	/**
	 * Copies the contents of this set into another.
	 */
	@Override
	public Object clone(){
		FunctionalDependencySet fds = new FunctionalDependencySet();
		triggeredFds = new FunctionalDependencySet();
		for(FunctionalDependency fd: this){
			fds.add(fd);
		}
		return fds;
	}
	
	public FunctionalDependencySet cloneWithFds(){
		FunctionalDependencySet fds = new FunctionalDependencySet();
		for(FunctionalDependency fd: this){
			fds.add((FunctionalDependency)fd.clone());
		}
		return fds;
	}
	
}
