package api.utilities;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class RequestResponseFilter implements Filter {
	
	 public RequestResponseFilter() {
	        System.out.println("RequestResponseFilter instantiated...");
	    }

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) 
	{
        Logger logger = LogManager.getLogger(this.getClass());
		 StringBuilder curlCommand = new StringBuilder("curl -X ")
		            .append(requestSpec.getMethod())
		            .append(" '").append(requestSpec.getURI()).append("'");
 
		        // Add headers
		        requestSpec.getHeaders().forEach(header -> 
		            curlCommand.append(" -H '").append(header.getName()).append(": ").append(header.getValue()).append("'") );

		        // Add body if present
		        if (requestSpec.getBody() != null) {
		            curlCommand.append(" --data '").append(requestSpec.getBody().toString()).append("'");
		        }
		        System.out.println("======================================================================================");
		        System.out.println("======================================================================================");
		        logger.info("======================================================================================");
		        logger.info("======================================================================================");
		        ExtentReportManager.test.info("<b><pre><span style='color:green;'>" + "======================================================================================"+ "</span></pre></b>");
		        ExtentReportManager.test.info("<b><pre><span style='color:green;'>" + "======================================================================================"+ "</span></pre></b>");

		        
		        System.out.println("cURL Command: \n" + curlCommand.toString());
		        logger.info("cURL Command: \n" + curlCommand.toString());
		        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>cURL Command: \n" + curlCommand.toString() + "</span></pre></b>");

		         Response response = ctx.next(requestSpec, responseSpec);
		         
                System.out.println("******************************************************************");
		        logger.info("******************************************************************");
		        ExtentReportManager.test.info("<b><pre><span style='color:green;'>" + "**************************************************************************************"+ "</span></pre></b>");

                // Log the response details
		        System.out.println("Response Status line: \n" + response.getStatusLine());
		        System.out.println("Response Body: \n" + response.getBody().asPrettyString());
		        System.out.println("Response Headers: \n" + response.getHeaders());
		        System.out.println("======================================================================================");
		        System.out.println("======================================================================================");

		        logger.info("Response Status line: \n" + response.getStatusLine());
		        logger.info("Response Body: \n" + response.getBody().asPrettyString());
		        logger.info("Response Headers: \n" + response.getHeaders());
		        logger.info("======================================================================================");
		        logger.info("======================================================================================");
		        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Response Status line: \n" + response.getStatusLine() + "</span></pre></b>");
		        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Response Body: \n" + response.getBody().asPrettyString() + "</span></pre></b>");
		        ExtentReportManager.test.info("<b><pre><span style='color:yellow;'>Response Headers: \n" + response.getHeaders() + "</span></pre></b>");
		        ExtentReportManager.test.info("<b><pre><span style='color:green;'>" + "======================================================================================"+ "</span></pre></b>");
		        ExtentReportManager.test.info("<b><pre><span style='color:green;'>" + "======================================================================================"+ "</span></pre></b>");

		        return response;
	}

}
