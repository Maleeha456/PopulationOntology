package org.example.see;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;

public class SparqlQuery {

	public static void main(String[] args) throws IOException {
		try {
			File file = new File("query1.txt");
			int i=1;
			String i_str= String.valueOf(i);
			//PrintWriter writer=new PrintWriter("result.txt","UTF-8");
			FileWriter writer = new FileWriter("result.txt"); 
			
			String content = FileUtils.readFileToString(file);
			Repository repo=new HTTPRepository("http://localhost:7200","project123");	
			repo.initialize();
			Value val=null,val1=null;
			RepositoryConnection repoConn=repo.getConnection();
			TupleQuery tupleQuery=repoConn.prepareTupleQuery(QueryLanguage.SPARQL,content);
		//	TupleQueryResult tupleResult=tupleQuery.evaluate();
			List<BindingSet> resultList;
			try(TupleQueryResult tupleResult=tupleQuery.evaluate())
			{
				//List<String> bindingNames=tupleResult.getBindingNames();
				
				//System.out.println(tupleResult);
				if(tupleResult.hasNext())
				{
				
					resultList=QueryResults.asList(tupleResult);
					String[] res=resultList.toString().split(",");
					//Files.deleteIfExists(java.nio.file.Paths.get("result.txt"));
					//Files.createFile(java.nio.file.Paths.get("result.txt"));
					
					for(String s: res)
					{
						i_str= String.valueOf(i);
						writer.write(i_str+s);
						writer.write("\n");
						i++;
						
						//System.out.println(s);
					}
					writer.close();
				}
				
			}

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}

	}

}

