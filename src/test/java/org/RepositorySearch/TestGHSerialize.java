package org.RepositorySearch.serialize;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.util.*;
import java.sql.SQLException;
import java.io.File;
import java.nio.file.*;

import org.kohsuke.github.GHTopics;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.RepositorySearch.CreateDBScheme;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonParseException;

@Ignore
public class TestGHSerialize{

    //class under test
    private SGHRepository ref;
    private ObjectMapper mapper;
    @Mock
    private GHTopics mockTopics;
    
    private static Path testPath;

    @BeforeClass
    public static void csetup()throws SQLException{
	CreateDBScheme.forRepository();
	testPath = FileSystems.getDefault().getPath("src", "test", "resources", "mockRepository.json");
    }     
    
    @Before
    public void setUp() {
	mapper = new ObjectMapper();
    }


    public TestGHSerialize()throws Exception{
        MockitoAnnotations.initMocks(this);
	ref = new SGHRepository(mockTopics);
    }
        
    //test only for exceptions
    @Test
    public void serialize()throws Exception{
	
	//mock topics
	ArrayList<String> myTopics = new ArrayList<String>();
	myTopics.add("search");
	myTopics.add("filter");
	
	when(mockTopics.getAll("RepositorySearch", "foss")).thenReturn(myTopics);
	
	//create Repository directly
	GHRepository repo = GitHub.connect().getRepository("germanysources/mockup_loader");
	
	ref.serialize(repo);
	
    }

}
