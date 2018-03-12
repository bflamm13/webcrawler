package crawler;

import java.util.SortedSet;

/**
 * @author barry flamm
 *
 * Interface for the required methods to handle retrieving content, parsing content, and generating url set 
 */
public interface IHandler
{
	public SortedSet<String> getUrls(String origUrl, Integer needed);
}
