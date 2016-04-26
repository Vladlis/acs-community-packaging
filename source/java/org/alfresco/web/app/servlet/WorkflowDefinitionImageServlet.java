package org.alfresco.web.app.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.workflow.WorkflowException;
import org.alfresco.service.cmr.workflow.WorkflowService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * Render Workflow Definition Image
 * @deprecated 5.0 not exposed in web-client web.xml
 */
public class WorkflowDefinitionImageServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException
    {
        // retrieve workflow definition id
        String uri = request.getRequestURI();
        uri = uri.substring(request.getContextPath().length());
        StringTokenizer t = new StringTokenizer(uri, "/");
        if (t.countTokens() != 2)
        {
            throw new WorkflowException("Workflow Definition Image servlet does not contain workflow definition id : " + uri); 
        }
        t.nextToken();  // skip servlet name
        String workflowDefinitionId = t.nextToken();

        // retrieve workflow definition image
        WebApplicationContext wc = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        WorkflowService workflowService = (WorkflowService)wc.getBean(ServiceRegistry.WORKFLOW_SERVICE.getLocalName());
        byte[] definitionImage = workflowService.getDefinitionImage(workflowDefinitionId); 
        
        // stream out through response
        OutputStream out = response.getOutputStream();
        out.write(definitionImage);
        out.flush();
	}
    
}
