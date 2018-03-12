package crawler;

/**
 * @author barry flamm
 * 
 * Class to convert the relative urls to absolute urls 
*/

public class URLAdjuster implements IAdjuster
{
	private String parentUrl;
	
	/*
	 * Constructor to initialize the parent url so later we can adjust relative paths
	 */
	public URLAdjuster(String parentUrl)
	{
		this.parentUrl = parentUrl;
	}
	
	/*
	 * (non-Javadoc)
	 * @see veracode.IAdjuster#adjustURL(java.lang.String)
	 * 
	 * determine if the url is a relative or absolute path
	 * adjust the relative path to look like an absolute path by prepending the parent url
	 */
	public String adjustURL(String url)
	{

		for (String sheme :	URLProcessor.schemes)
		{
			if ( url.contains(sheme))
			{
				return url;
			}
		}
		//System.out.println("relative: " + url);
		return parentUrl + url;
	}
}
