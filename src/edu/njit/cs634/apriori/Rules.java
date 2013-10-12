package edu.njit.cs634.apriori;

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
	
	public String toString()
	{
		return A + " => " + B + "\t Support: " + Long.toString(support) + "%\t Confidence: " + Long.toString(confidence) + "%";
	}
	
	private String A, B;
	private long support, confidence;
}
