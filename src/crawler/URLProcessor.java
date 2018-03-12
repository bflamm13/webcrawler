package crawler;

import java.util.SortedSet;

import org.apache.commons.validator.routines.UrlValidator;

public class URLProcessor
{
	final public int MAX_DEF = 50;
	final static String[] schemes = {"http","https"}; 
	
	private IHandler urlHandler = null;
	private String origUrl = "";
	private int maxUrls = MAX_DEF;
	
	public URLProcessor (IHandler handler, int maxRecords)
	{
		this.urlHandler = handler;
		this.maxUrls = maxRecords;
	}
	
	/*
	 * Verify url is a valid format
	 */
	static public boolean isValid(final String url)
	{
		UrlValidator urlValidator = new UrlValidator(schemes);
		return urlValidator.isValid(url); 
	}
	
	public SortedSet<String> process(String url)
	{
		origUrl = url;
		SortedSet<String> urlSet = urlHandler.getUrls(url, maxUrls);
		return urlSet;
	}
	/*
	 * print out the set of urls generated from the process()
	 */
	public void report(SortedSet<String> urlSet)
	{
		if (urlSet != null)
		{
			System.out.println("");
			System.out.println("# of Urls found: " + urlSet.size() + " for " + origUrl);
			long x = 1;
			//List<String> sortedList = new ArrayList<String> (urlSet);
			//Collections.sort(sortedList);
			for(String urlStr: urlSet)
			{
				System.out.println("" + x + ": " + urlStr);
				x++;
			}
		}
	}
}
