package org.example.see;
import java.io.FileReader;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import java.io.*;
import com.opencsv.CSVReader;

public class FirstStep 
{
	public static void main(String[] args) 
	{
		Repository rep = new SailRepository(new MemoryStore());
		rep.initialize();
		ModelBuilder builder = new ModelBuilder();
		try
		{
			FileReader file = new FileReader("C:\\Users\\Admin\\Desktop\\adult_data_new.csv");
			FileReader mfile1 = new FileReader("C:\\Users\\admin\\Desktop\\Classes.csv");
			FileReader mfile2 = new FileReader("C:\\Users\\admin\\Desktop\\ObjectProperties.csv");

			//CSVReader csvReader = new CSVReader(file);
			//CSVReader csvReader1 = new CSVReader(mfile1);
			CSVReader csvReader2 = new CSVReader(mfile2);
			
			PrintStream o = new PrintStream(new File("src/main/resources/A.ttl"));
			System.setOut(o); 
			
			String domain_col_name = "";
			String range_col_name = "";
			//int index1 = 0;
			//int index2 = 0;
			
			String[] nextRecord2;
			int flag2 = 0;
			while((nextRecord2 = csvReader2.readNext())!=null) 
			{
				if (flag2 == 0) //SKIPS LINE1
					flag2 = 1;
				else			//STARTS READING FROM LINE2
				{
					builder.setNamespace("ex","http://www.iiitd.ac.in/humanpopulation/census/garvita/maleeha/");
					String object_property = nextRecord2[0];
					String domain_class = nextRecord2[1];
					String range_class = nextRecord2[2];
					//System.out.println(domain_class + " " + object_property + " " + range_class);
					
					String[] nextRecord11;
					String[] nextRecord12;
					mfile1 = new FileReader("C:\\Users\\admin\\Desktop\\Classes.csv");
					CSVReader csvReader1 = new CSVReader(mfile1);

					while((nextRecord11 = csvReader1.readNext())!=null) 
					{
						//System.out.println(nextRecord11[0]);
						if (nextRecord11[0].equalsIgnoreCase(domain_class))
						{
							domain_col_name = nextRecord11[1];
							//System.out.println(domain_col_name);
						}
					}
					mfile1 = new FileReader("C:\\Users\\admin\\Desktop\\Classes.csv");
					csvReader1 = new CSVReader(mfile1);
					while((nextRecord12 = csvReader1.readNext())!=null) 
					{
						//System.out.println(nextRecord12[0]);
						if (nextRecord12[0].equalsIgnoreCase(range_class))
						{
							range_col_name = nextRecord12[1];
						}
					}
					//System.out.println(domain_col_name + " " + object_property + " " + range_col_name);
					String[] nextRecord;		
					int flag = 0;
					file = new FileReader("C:\\Users\\Admin\\Desktop\\adult_data_new.csv");
					int index1 = 0;
					int index2 = 0;
					CSVReader csvReader = new CSVReader(file);

					while((nextRecord = csvReader.readNext())!=null)
					{
						if(flag == 0) //AT LINE1 OF DATA CSV FILE
						{
							for (int i=0 ; i<16 ; i++)
							{
								if (nextRecord[i].equalsIgnoreCase(domain_col_name))
								{
									index1 = i;
								}
								if (nextRecord[i].equalsIgnoreCase(range_col_name))
								{
									index2 = i;
								}
							}
							flag = 1;
						}
						else
						{
							/*for (int i=0 ; i<16 ; i++)
							{
								if (nextRecord[i].equalsIgnoreCase(domain_col_name))
								{
									index1 = i;
								}
								if (nextRecord[i].equalsIgnoreCase(range_col_name))
								{
									index2 = i;
								}
							}*/
							//System.out.println(index1 + " " + index2);
							String firstVal = nextRecord[index1];
							String secondVal = nextRecord[index2];
							String namespace = "http://example.org/";
							ValueFactory f = rep.getValueFactory();
							//System.out.println(firstVal + " " + object_property + " " + secondVal);
							try(RepositoryConnection conn = rep.getConnection())
							{
								builder.subject("ex:"+firstVal).add("ex:"+object_property,secondVal);
							}
						 }
							
					}
				}
			}
			Model model = builder.build();
			Rio.write(model, System.out, RDFFormat.TURTLE);
			
			
			/*while((nextRecord=csvReader.readNext())!=null)
			{
				builder.setNamespace("ex","http://www.iiitd.ac.in/humanpopulation/census/garvita/maleeha/");
				String individualNumber = nextRecord[0];
				String age = nextRecord[1];
				String workClass = nextRecord[2];
				String fnlWgt = nextRecord[3];
				String education = nextRecord[4];
				String educationNum = nextRecord[5];
				String maritalStatus = nextRecord[6];
				String occupation = nextRecord[7];
				String relationship = nextRecord[8];
				String race = nextRecord[9];
				String sex = nextRecord[10];
				String capitalGain = nextRecord[11];
				String capitalLoss = nextRecord[12];
				String hoursPerWeek = nextRecord[13];
				String nativeCountry = nextRecord[14];
				String salary = nextRecord[15];
				String namespace = "http://example.org/";
				ValueFactory f = rep.getValueFactory();
				//RepositoryResult<Statement> statements = conn.getStatements(null, null, null);
				//Model model = QueryResults.asModel(statements);
				//Rio.write(model, System.out, RDFFormat.TURTLE);
				try(RepositoryConnection conn = rep.getConnection())
				{
					builder.subject("ex:"+individualNumber).add("ex:hasAge",age);
					builder.subject("ex:"+individualNumber).add("ex:hasEducationTill",education);
					builder.subject("ex:"+individualNumber).add("ex:hasCapitalGain",capitalGain );
					builder.subject("ex:"+individualNumber).add("ex:hasCapitalLoss",capitalLoss);
					builder.subject("ex:"+individualNumber).add("ex:hasFamilyRelationStatusAs",relationship);
					bsuilder.subject("ex:"+individualNumber).add("ex:hasFinalWeightOrUniqueAdharIdAs",fnlWgt);
					builder.subject("ex:"+individualNumber).add("ex:hasGender",sex);
					builder.subject("ex:"+individualNumber).add("ex:hasMaritialStatus",maritalStatus);
					builder.subject("ex:"+individualNumber).add("ex:hasOccupation",occupation);
					builder.subject("ex:"+individualNumber).add("ex:hasRace",race);
					builder.subject("ex:"+individualNumber).add("ex:hasSalary",salary);
					builder.subject("ex:"+individualNumber).add("ex:hasSpouse",relationship);
					builder.subject("ex:"+individualNumber).add("ex:hasWorkClass",workClass);
					builder.subject("ex:"+individualNumber).add("ex:hasWorkingHours",hoursPerWeek);
					builder.subject("ex:"+individualNumber).add("ex:belongsTo",nativeCountry);
					builder.subject("ex:"+education).add("ex:hasEducationNumberAs",educationNum);
				}
			}
			Model model = builder.build();
			Rio.write(model, System.out, RDFFormat.TURTLE);*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}











