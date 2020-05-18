package org.example.see;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.rio.RDFFormat;

public class GraphDbLoad {
private static RepositoryConnection getRepositoryConnection(){
Repository repo=new HTTPRepository("http://localhost:7200","project123");	
repo.initialize();
RepositoryConnection repoConn=repo.getConnection();
return repoConn;
}
public static void main(String[] abc)
{
	String filePath="src/main/resources/";
	GraphDbLoad gLoad=new GraphDbLoad();
	RepositoryConnection repoConn=null;
	try{
		repoConn=getRepositoryConnection();
		File file=new File(filePath);
		System.out.println(file);
		File[] filearray=file.listFiles();
		String fileName;
		for(int i=0;i<filearray.length;i++){
			fileName=filearray[i].getName();
			if(fileName.contains(".ttl"))//config??
			{
				System.out.println(fileName);
				InputStream is=new FileInputStream(filePath + "/" + fileName);
				Reader r=new InputStreamReader(new BufferedInputStream(is));
				repoConn.add(r,"",RDFFormat.TURTLE);
				System.out.println("done");
			}
		}
		
	}
	catch(Throwable t){
		System.out.println("Connection error");
	}
	finally{
		repoConn.close();
	}
}
}

