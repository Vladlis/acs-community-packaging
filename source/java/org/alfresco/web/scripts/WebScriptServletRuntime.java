/*
 * Copyright (C) 2005-2007 Alfresco Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in Alfresco's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.alfresco.com/legal/licensing"
 */
package org.alfresco.web.scripts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alfresco.service.transaction.TransactionService;
import org.alfresco.web.scripts.WebScriptDescription.RequiredAuthentication;


/**
 * HTTP Servlet Web Script Runtime
 * 
 * @author davidc
 */
public class WebScriptServletRuntime extends WebScriptRuntime
{
    private HttpServletRequest req;
    private HttpServletResponse res;
    private WebScriptServletAuthenticator authenticator;
    

    /**
     * Construct
     * 
     * @param registry
     * @param transactionService
     * @param authenticator
     * @param req
     * @param res
     */
    public WebScriptServletRuntime(WebScriptRegistry registry, TransactionService transactionService, WebScriptServletAuthenticator authenticator, HttpServletRequest req, HttpServletResponse res)
    {
        super(registry, transactionService);
        this.req = req;
        this.res = res;
        this.authenticator = authenticator;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebScriptRuntime#getScriptMethod()
     */
    @Override
    protected String getScriptMethod()
    {
        return req.getMethod();
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebScriptRuntime#getScriptUrl()
     */
    @Override
    protected String getScriptUrl()
    {
        return req.getPathInfo();
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebScriptRuntime#createRequest(org.alfresco.web.scripts.WebScriptMatch)
     */
    @Override
    protected WebScriptRequest createRequest(WebScriptMatch match)
    {
        return new WebScriptServletRequest(req, match);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebScriptRuntime#createResponse()
     */
    @Override
    protected WebScriptResponse createResponse()
    {
        return new WebScriptServletResponse(res);
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebScriptRuntime#authenticate(org.alfresco.web.scripts.WebScriptDescription.RequiredAuthentication, boolean)
     */
    @Override
    protected void authenticate(RequiredAuthentication required, boolean isGuest)
    {
        if (authenticator != null)
        {
            authenticator.authenticate(required, isGuest, req, res);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebScriptRuntime#preExecute(org.alfresco.web.scripts.WebScriptRequest, org.alfresco.web.scripts.WebScriptResponse)
     */
    @Override
    protected boolean preExecute(WebScriptRequest scriptReq, WebScriptResponse scriptRes)
    {
        return true;
    }

    /* (non-Javadoc)
     * @see org.alfresco.web.scripts.WebScriptRuntime#postExecute(org.alfresco.web.scripts.WebScriptRequest, org.alfresco.web.scripts.WebScriptResponse)
     */
    @Override
    protected void postExecute(WebScriptRequest scriptReq, WebScriptResponse scriptRes)
    {
    }

}