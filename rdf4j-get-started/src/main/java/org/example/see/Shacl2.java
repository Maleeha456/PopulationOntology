package org.example.see;

import java.io.File;
import java.io.IOException;
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

public class Shacl2 {

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
                                "  sh:datatype xsd:int ."
                               
                                
                                
                        ));



                //connection.add(shaclRules, "", RDFFormat.TURTLE, RDF4J.SHACL_SHAPE_GRAPH);

                //connection.add(shaclRules1, "", RDFFormat.TURTLE, RDF4J.SHACL_SHAPE_GRAPH);

                connection.add(shaclRules2, "", RDFFormat.TURTLE, RDF4J.SHACL_SHAPE_GRAPH);
                connection.commit();

                connection.begin();
                //

                StringReader invalidSampleData = new StringReader(
                        String.join("\n", "",
                        		"@prefix ex: <http://iiitd.ac.in/cbse_schools/DataProperty/owl#> .",
                                "@prefix ex1: <http://iiitd.ac.in/cbse_schools/owl#> .",
                                "@prefix sh: <http://www.w3.org/ns/shacl#> .",
                                "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .",
                                "@prefix foaf: <http://xmlns.com/foaf/0.1/>.",

                                "ex1:Principal a foaf:Person ;",
                                "foaf:name 20, \"30\"^^xsd:int ."

                        ));
                
                connection.add(invalidSampleData, "", RDFFormat.TURTLE);
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

