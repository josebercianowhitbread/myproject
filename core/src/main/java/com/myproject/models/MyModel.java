package com.myproject.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import com.adobe.acs.commons.models.injectors.annotation.AemObject;
import com.day.cq.wcm.api.Page;

@Model(
    adaptables = { SlingHttpServletRequest.class },
    resourceType = MyModel.RESOURCE_TYPE)
public class MyModel {

    public static final String RESOURCE_TYPE = "myproject/components/renderer";

    @AemObject
    private Page currentPage;
    
    protected final String ROOT_PAGE_PROPERTY = "isRootPage";
    private Page rootPage;

    @PostConstruct
    private void initModel() {
        // Fetches the root language page in order to get the data from that node.
        while (!isRootPage(currentPage)) {
            currentPage = currentPage.getParent();
        }
        rootPage = currentPage;
    }

    private boolean isRootPage(Page selectedPage) {
        return selectedPage.getProperties().get(ROOT_PAGE_PROPERTY, false);
    }
    
    public String getRootPath() {
    		return rootPage.getPath();
    }

}