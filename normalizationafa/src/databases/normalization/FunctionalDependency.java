package databases.normalization;

/**
 * Represents a functional dependency in relational algebra
 * with the following form:
 * left hand side -> right hand side
 *
 */
public class FunctionalDependency {

	private StringBuffer lhs;
	private String originalLhs;
	private StringBuffer rhs;
	
	/**
	 * Creates an empty functional dependency.
	 */
	public FunctionalDependency(){
		this.lhs = new StringBuffer("");
		this.rhs = new StringBuffer("");
	}

	/**
	 * Creates a fully defined functional dependency.
	 * @param lhs left hand side
	 * @param rhs right hand side
	 */
	public FunctionalDependency(String lhs, String rhs){
		if(lhs == null || rhs == null){
			throw new IllegalArgumentException("Invalid Functional Dependency");
		}
		this.lhs = new StringBuffer(lhs);
		this.originalLhs = lhs;
		this.rhs = new StringBuffer(rhs);
	}	
	
	private FunctionalDependency(String lhs,String rhs, String originalLhs){
		if(lhs == null || rhs == null || originalLhs == null){
			throw new IllegalArgumentException("Invalid Functional Dependency");
		}
		this.lhs = new StringBuffer(lhs);
		this.originalLhs = originalLhs;
		this.rhs = new StringBuffer(rhs);
	}
	
	public String removeAttributeLhs(int attributeIndex){
		if(attributeIndex < 0 || attributeIndex >= this.lhs.length()){
			throw new IllegalArgumentException("Atributo erroneo");
		}
		if(this.isLMinimum()){
			throw new IllegalArgumentException("No se pueden quitar mas atributos");
		}
		String s = "" + this.lhs.charAt(attributeIndex);
		this.lhs.deleteCharAt(attributeIndex);
		return s;
	}
	
	public void restoreLhs(){
		this.lhs = new StringBuffer(this.originalLhs);
	}
	
	public boolean isLMinimum(){
		return this.lhs.length() == 1;
	}
	
	public int lhsAttributes(){
		return this.lhs.toString().length();
	}
	
	public String getLhs(){
		return this.lhs.toString();
	}
	
	public boolean hasLhs(String lhs){
		if(lhs.length() != this.lhs.length())
			return false;
		for(Character c: this.lhs.toString().toCharArray()){
			if(!lhs.contains(c+""))
				return false;
		}
		return true;
	}
	
	public boolean hasRhs(String rhs){
		if(rhs.length() != this.rhs.length())
			return false;
		for(Character c: this.rhs.toString().toCharArray()){
			if(!rhs.contains(c+""))
				return false;
		}
		return true;
	}
	
	public boolean isTriggeredBy(String s){
		for(Character c: this.lhs.toString().toCharArray()){
			if(!s.contains(c+""))
				return false;
		}
		return true;
	}
	
	public String getRhs(){
		return this.rhs.toString();
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null)
			return false;
		if(this == o)
			return true;
		if(o instanceof FunctionalDependency){
			FunctionalDependency fd = (FunctionalDependency) o;
			return this.hasLhs(fd.getLhs()) && this.hasRhs(fd.getRhs());
		} else {
			return false;
		}
	}
	
	@Override
	public Object clone(){
		return new FunctionalDependency(this.lhs.toString(), this.rhs.toString(),
				this.originalLhs);
	}
	
	@Override
	public int hashCode() { 
		int hash = 1;
	    hash = hash * 7 + this.lhs.toString().hashCode();
	    hash = hash * 31 + this.rhs.toString().hashCode();
	    return hash;
	}
	
	/**
	 * Provides a String representation of this object
	 * which is left hand side -> right hand side
	 */
	@Override
	public String toString(){
		return this.lhs + "->" + this.rhs;
	}
	
	public String originalToString(){
		return this.originalLhs + "->" + this.rhs;
	}
}
