package org.RepositorySearch;

import java.io.IOException;
import java.sql.SQLException;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHSearchBuilder;
import org.kohsuke.github.PagedSearchIterable;
import org.kohsuke.github.PagedIterator;

import org.RepositorySearch.serialize.SGHRepository;

/**
 * Serialize the search results from github into the inmemory database
 */
public class GHSearchResults{
    GitHub account;
    SGHRepository serializer; 

    public GHSearchResults(GitHub account)throws SQLException{
	this.account = account;
	serializer = new SGHRepository(account);
    }

    /**
     * Query for the specified string and put the results in the database.
     * The fetch limit (number of results) is read from the configuration parameter maxNoResults.
     * @param term The search term. Syntax see {@link https://help.github.com/articles/searching-for-repositories/} 
     * @param AddMyFavorites Favorite addition to the search can be added (for example programming language ...)
     * @returns the number of found entries
     */
    public int query(String term, boolean AddMyFavorites)throws IOException, SQLException{
	
	String sterm;
	if(AddMyFavorites)
	    sterm = term + " " + Config.getInstance().FavoriteAdditions;
	else
	    sterm = term;

	PagedSearchIterable sit = account.searchRepositories().q(sterm).list();
	PagedIterator it = sit._iterator(Config.getInstance().maxNoResults);
	int size = 0;
	while(size < Config.getInstance().maxNoResults && it.hasNext()){
	    
	    serializer.serialize((GHRepository)it.next());
	    size++;
	}
	return size;

    }

}
