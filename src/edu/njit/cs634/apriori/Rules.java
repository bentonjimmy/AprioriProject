package edu.njit.cs634.apriori;

/**
 * A simple class that is used to hold information about an association rule.  The constructor
 * takes everything that is needed in order to create the rule.
 * @author Jim Benton
 *
 */
public class Rules {
	
	public Rules(String A, String B, long l, long m)
	{
		this.A = A;
		this.B = B;
		this.support = l;
		this.confidence = m;
	}
	
	public Rules()
	{
		this(null, null, 0, 0);
	}
	
	/**
	 * A String representation of the association rule.  The rule will print as:
	 * A => B	Support: XX%	Confidence: XX%
	 */
	public String toString()
	{
		return A + " => " + B + "\t Support: " + Long.toString(support) + "%\t Confidence: " + Long.toString(confidence) + "%";
	}
	
	private String A, B;
	private long support, confidence;
}
