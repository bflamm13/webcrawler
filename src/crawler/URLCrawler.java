package crawler;

import java.util.SortedSet;

/**
 * @author barry flamm
 * 
 * Veracde Code Exercise
 * 
 * Instructions:
 * Write a Java or Python program that takes a URL as input and:
1.    Validates it
2.    Accesses content
3.    Parses content to find other URLs*
4.    Repeats 2 and 3 recursively until 50 URLs are found or nothing is left to find.
5.    Reports all found URLs
6.    Should not repeat URLs
7.    Reports any other possible errors
 
*Note: Linked URLs refer to relative or absolute links in a href attribute, 
*i.e. <a href=”/foo.jsp”></a> 
*  or 
*<a href=”[http://somewhere.overtherainbow.xn--com%3e%3c-qw3b/a]Error! Hyperlink reference not valid.>.
*
* Parsing links bound through Javascript events is not expected.
 *
 */
public class URLCrawler 
{
	final static int MAX_URL = 50;
	final static String ARG_URL = "-u";
	final static String ARG_MAX = "-m";

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		int needed = MAX_URL;
		if (args.length > 0)
		{
			if ((args.length == 2) || (args.length == 4))
			{
				if (args[0].compareTo(ARG_URL) == 0)
				{
					String url = args[1];
					
					if (args.length == 4)
					{
						if (args[2].compareTo(ARG_MAX) == 0)
						{
							try
							{
								needed = Integer.parseInt(args[3]);
							}
							catch(Exception e)
							{
								System.out.println("Invalid Max number of URLs to retrieve: " + args[3]);
								usage();
								return;
							}
						}
					}
					URLHandler handler = new URLHandler();
					if (URLProcessor.isValid(url))
					{
						URLProcessor processor = new URLProcessor(handler, needed);
						SortedSet<String> urls = processor.process(url);
						processor.report(urls);
					}
					else
					{
						System.out.println("Invalid URL entered: " + url);
					}
				}
				else
				{
					System.out.println("Invalid Parmeter: " + args[0]);
					usage();
				}
			}
			else
			{
				System.out.println("Wrong number of paramters, try again");
				usage();
			}
		}
		else
		{
			System.out.println("No Parmeters passed!");
			usage();
		}
	}
	
	public static void usage()
	{
		System.out.println("");
		System.out.println("Usage: java -jar URLCrawler.jar -u <URL String> [-m <# of urls returned>]");
		System.out.println("Default number urls returned: 50");
		System.out.println("Default max error threshold : 10");
		System.out.println("");
	}

}
