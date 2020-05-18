package org.example.see;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.PrintStream;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

import com.opencsv.CSVReader;

public class HelloRDF4J {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Repository rep = new SailRepository(new MemoryStore());
		rep.initialize();
		ModelBuilder builder=new ModelBuilder();
		Model model = builder.build();
		try
		{
			FileReader file=new FileReader("C:\\Users\\Admin\\Desktop\\adult_data_new.csv");
			CSVReader csvReader=new CSVReader(file);
			PrintStream o = new PrintStream(new File("src/main/resources/A.ttl"));
			//
			File filettl=new File("Census_ttl_check123.owl");
	java.io.InputStream input=new FileInputStream(filettl);
	Model model2=builder.build();
	 model2=Rio.parse(input, "", RDFFormat.TURTLE);
			
			System.setOut(o); 
			String[] nextRecord;
			while((nextRecord=csvReader.readNext())!=null)
			{
				builder.setNamespace("ex","http://www.iiitd.ac.in/humanpopulation/census/garvita/maleeha");
				String individualNumber=nextRecord[0];
				String age=nextRecord[1];
				String workClass=nextRecord[2];
				String fnlWgt=nextRecord[3];
				String education=nextRecord[4];
				String educationNum=nextRecord[5];
				String maritalStatus=nextRecord[6];
				String occupation=nextRecord[7];
				String relationship=nextRecord[8];
				String race=nextRecord[9];
				String sex=nextRecord[10];
				String capitalGain=nextRecord[11];
				String capitalLoss=nextRecord[12];
				String hoursPerWeek=nextRecord[13];
				String nativeCountry=nextRecord[14];
				String salary=nextRecord[15];
	
				String namespace = "http://example.org/";
				ValueFactory f = rep.getValueFactory();
				//RepositoryResult<Statement> statements = conn.getStatements(null, null, null);
				//Model model = QueryResults.asModel(statements);
				//Rio.write(model, System.out, RDFFormat.TURTLE);
				try(RepositoryConnection conn = rep.getConnection())
				{
					builder.subject("ex:"+individualNumber).add("ex:hasAge", "ex:"+age);
					builder.subject("ex:"+individualNumber).add("ex:hasEducationTill",education);
					builder.subject("ex:"+individualNumber).add("ex:hasCapitalGain",capitalGain );
					builder.subject("ex:"+individualNumber).add("ex:hasCapitalLoss",capitalLoss);
					builder.subject("ex:"+individualNumber).add("ex:hasFamilyRelationStatusAs",relationship);
					builder.subject("ex:"+individualNumber).add("ex:hasFinalWeightOrUniqueAdharIdAs","ex:"+fnlWgt);
					builder.subject("ex:"+individualNumber).add("ex:hasGender","ex:",sex);
					builder.subject("ex:"+individualNumber).add("ex:hasMaritialStatus",maritalStatus);
					builder.subject("ex:"+individualNumber).add("ex:hasOccupation","ex:"+occupation);
					builder.subject("ex:"+individualNumber).add("ex:hasRace",race);
					builder.subject("ex:"+individualNumber).add("ex:hasSalary",salary);
					builder.subject("ex:"+individualNumber).add("ex:hasSpouse",relationship);
					builder.subject("ex:"+individualNumber).add("ex:hasWorkClass",workClass);
					builder.subject("ex:"+individualNumber).add("ex:hasWorkingHours",hoursPerWeek);
					builder.subject("ex:"+individualNumber).add("ex:belongsTo","ex:",nativeCountry);
					builder.subject("ex:" +education ).add("ex:hasEducationNumberAs",educationNum);
					builder.subject("ex:"+occupation).add("ex:makesSalary","ex:",salary);
					builder.subject("ex:"+occupation).add("ex:workClassTypeAs","ex:",workClass);
					builder.subject("ex:"+occupation).add("ex:hasToWorkForHoursAs","ex:"+hoursPerWeek);
					builder.subject("ex:"+education).add("ex:providesOccupationAs",occupation);
					builder.subject("ex:"+age).add("ex:hasAgeAs","xsd:int");
					builder.subject("ex:"+fnlWgt).add("ex:hasFinalWeightValueAs","ex:","xsd:int");
					builder.subject("ex:"+hoursPerWeek).add("ex:hasHoursAs","ex:","xsd:int");
					builder.subject("ex:"+maritalStatus).add("ex:hasStatusAs","xsd:string");
					builder.subject("ex:"+nativeCountry).add("ex:hasValueAs","xsd:string");
					builder.subject("ex:"+capitalGain).add("ex:forOccupationIs",occupation);
					builder.subject("ex:"+individualNumber).add("ex:isFrom",nativeCountry);
					builder.subject("ex:"+age).add(RDF.TYPE,"age");
					builder.add("ex:","hasAge").add(RDFS.DOMAIN,"ex:"+individualNumber).add(RDFS.RANGE,"ex:"+age);
					
					
					
				}
				
			}
			
			
			Rio.write(model2, System.out, RDFFormat.TURTLE);
			Rio.write(model, System.out, RDFFormat.TURTLE);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}






	}

}
