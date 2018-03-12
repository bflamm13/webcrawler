package crawler;

import java.util.Deque;

/**
 * @author barry flamm
 *
 * Class to handler the url validation, retrieving content from url, and parsing of url content
 * 
 */

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class URLHandler implements IHandler
{
	final static String HREF_REG = "href=\"([^\"]*)\"";
	final static Integer MAX_ERR = 10;
	final static Integer MAX_URLS = 50;
	
	/*
	 * (non-Javadoc)
	 * @see veracode.IHandler#parse(java.lang.String)
	 * 
	 * return a unique list of urls parsed from the content 
	 */
	
	public Set<String> parse(String url) 
	{
		Set<String> urlSet = new HashSet<String>();
		IAdjuster adjuster = new URLAdjuster(url);
		try
		{
	        Document doc;
			doc = Jsoup.connect(url).get();
	
			Elements linksOnPage = doc.select("a[href]");
	        for (Element link : linksOnPage) {
	        	String linkHref = adjuster.adjustURL(link.attr("abs:href"));
	        	//System.out.println("**parsed url: "+ linkHref);
	        	urlSet.add(linkHref);
	        }
		}
		catch (Exception e)
		{
			System.out.println("Error: "+ e.getMessage());
		}
		return urlSet;
	}
	
	/*
	 * (non-Javadoc)
	 * @see veracode.IHandler#getUrls(java.lang.String, Integer)
	 * 
	 * Recursively, generate a set of urls from the url content and the subsequent urls parsed from the previous url
	 * The size of the set returned is based on the passed in needed value
	 * 
	 * return a unique list of urls as a java.util.Set 
	 */
	@Override
	public SortedSet<String> getUrls(String origUrl, Integer needed)
	{
		int counter = 0;
		Deque<String> q = new LinkedList<>();
		SortedSet<String> urls = new TreeSet<String>();
		q.add(origUrl);
		urls.add(origUrl);
		counter++;
		while ((!q.isEmpty()) && (counter < needed))
		{
			String url = q.pop();
			
			Set<String>  pageUrls = parse(url);
			for (String u:pageUrls)
			{
				if (!urls.contains(u))
				{					
					q.add(u);
					urls.add(u);
					counter++;
					if (counter >= needed)
					{
						break;
					}
				}
			}
		}
		return urls;
	}
	
}
