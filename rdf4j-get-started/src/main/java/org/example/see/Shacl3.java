package org.example.see;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;


import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.vocabulary.RDF4J;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.repository.sail.SailRepositoryConnection;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.eclipse.rdf4j.sail.shacl.ShaclSail;
import org.eclipse.rdf4j.sail.shacl.ShaclSailValidationException;
import org.eclipse.rdf4j.sail.shacl.results.ValidationReport;

public class Shacl3 {

	public static void main(String[] args) throws RDFParseException, RepositoryException, IOException {
		// TODO Auto-generated method stub
		ShaclSail shaclSail = new ShaclSail(new MemoryStore());
		
		 //Logger root = (Logger) LoggerFactory.getLogger(ShaclSail.class.getName());
        //root.setLevel(Level.INFO);

        //shaclSail.setLogValidationPlans(true);
        //shaclSail.setGlobalLogValidationExecution(true);
        //shaclSail.setLogValidationViolations(true);

        SailRepository sailRepository = new SailRepository(shaclSail);
        sailRepository.init();

        try (SailRepositoryConnection connection = sailRepository.getConnection()) {
           
                connection.begin();
                System.out.println("Connection begin");
               StringReader shaclRules = new StringReader(
                        String.join("\n", "",
                                "@prefix ex2: <http://iiitd.ac.in/cbse_schools/DataProperty/owl#> .",
                                "@prefix ex1: <http://iiitd.ac.in/cbse_schools/owl#> .",
                                "@prefix sh: <http://www.w3.org/ns/shacl#> .",
                                "@prefix  ex: <http://www.iiitd.ac.in/humanpopulation/census/garvita/maleeha> .", 
                                "@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.",
                                "@prefix xml: <http://www.w3.org/XML/1998/namespace> .",
                                "@prefix owl: <http://www.w3.org/2002/07/owl#> .",
                                "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .",
                                "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .",
                                	 
                                	
                                "ex1:Shape1",
                                "  a sh:NodeShape;",
                                "  sh:targetClass ex:Age ;",
                                "  sh:property ex:PersonShapeProperty .",

                                "ex:Property1 ",
                                "  sh:path ex:hasAge ;",
                                "  sh:datatype xsd:int ;",
                                "  sh:maxCount 1;",
                                "  sh:minCount 1 ."
                                
                                
                        ));
                StringReader shaclRules1 = new StringReader(
                String.join("\n", "",
                		 "@prefix ex2: <http://iiitd.ac.in/cbse_schools/DataProperty/owl#> .",
                         "@prefix ex1: <http://iiitd.ac.in/cbse_schools/owl#> .",
                         "@prefix sh: <http://www.w3.org/ns/shacl#> .",
                         "@prefix  ex: <http://www.iiitd.ac.in/humanpopulation/census/garvita/maleeha> .", 
                         "@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.",
                         "@prefix xml: <http://www.w3.org/XML/1998/namespace> .",
                         "@prefix owl: <http://www.w3.org/2002/07/owl#> .",
                         "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .",
                         "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .",

                        "ex1:Shape2",
                        "  a sh:NodeShape;",
                        "  sh:targetClass ex:Salary ;",
                        "  sh:property ex:PersonShapeProperty .",
                        

                        "ex:Property2 ",
                        "  sh:path ex:makesSalary ;",
                        "  sh:datatype xsd:int .",
                        "  sh.nodeKind sh:IRI ."
                       
                        
                        
                ));
                StringReader shaclRules2 = new StringReader(
                String.join("\n", "",
                        "@prefix ex: <http://iiitd.ac.in/cbse_schools/DataProperty/owl#> .",
                        "@prefix ex1: <http://iiitd.ac.in/cbse_schools/owl#> .",
                        "@prefix sh: <http://www.w3.org/ns/shacl#> .",
                        "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .",
                        "@prefix foaf: <http://xmlns.com/foaf/0.1/>.",

                        "ex1:Principal",
                        "  a sh:NodeShape;",
                        "  sh:targetClass foaf:Person ;",
                        "  sh:property ex:PersonShapeProperty .",

                        "ex:PersonShapeProperty ",
                        "  sh:path foaf:name ;",
                        "  sh:datatype xsd:String ."
                        
                        
                ));



                connection.add(shaclRules, "", RDFFormat.TURTLE, RDF4J.SHACL_SHAPE_GRAPH);

                //connection.add(shaclRules1, "", RDFFormat.TURTLE, RDF4J.SHACL_SHAPE_GRAPH);

                //connection.add(shaclRules2, "", RDFFormat.TURTLE, RDF4J.SHACL_SHAPE_GRAPH);
                connection.commit();

                connection.begin();
                //

                /*StringReader invalidSampleData = new StringReader(
                        String.join("\n", "",
                        		"@prefix ex: <http://iiitd.ac.in/cbse_schools/DataProperty/owl#> .",
                                "@prefix ex1: <http://iiitd.ac.in/cbse_schools/owl#> .",
                                "@prefix sh: <http://www.w3.org/ns/shacl#> .",
                                "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .",
                                "@prefix foaf: <http://xmlns.com/foaf/0.1/>.",

                                "ex1:Principal a foaf:Person ;",
                                "foaf:name 20, \"30\"^^xsd:int ."

                        ));*/
                InputStream file_ttl=new FileInputStream("src/main/resources/A.ttl");
        		//java.io.InputStream input=new FileInputStream(filettl);
                connection.add(file_ttl, "", RDFFormat.TURTLE);
                //connection.add(invalidSampleData, "", RDFFormat.TURTLE);
                try {
                        connection.commit();
                } catch (RepositoryException exception) {
                        Throwable cause = exception.getCause();
                        if (cause instanceof ShaclSailValidationException) {
                                ValidationReport validationReport = ((ShaclSailValidationException) cause).getValidationReport();
                                Model validationReportModel = ((ShaclSailValidationException) cause).validationReportAsModel();
                                // use validationReport or validationReportModel to understand validation violations
                                PrintStream o = new PrintStream(new File("src/main/resources/validation_result.txt"));
                                Rio.write(validationReportModel, System.out, RDFFormat.TURTLE);
                        }
                        throw exception;
                }
        }

	}

}

