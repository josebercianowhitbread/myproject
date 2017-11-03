package com.myproject.models;

import static org.junit.Assert.*;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import com.adobe.acs.commons.models.injectors.annotation.impl.AemObjectAnnotationProcessorFactory;
import com.adobe.acs.commons.models.injectors.impl.AemObjectInjector;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit.AemContext;

@RunWith(MockitoJUnitRunner.class)
public class MyModelTest {

    @Rule
    public final AemContext context = new AemContext();

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    AemObjectAnnotationProcessorFactory factory;

    @InjectMocks
    AemObjectInjector aemObjectInjector;

    private static final String RESOURCE_PATH = "/content/parent-page/jcr:content/content/renderer";
    private static final String PAGE_PATH = "/content/parent-page";

    private MyModel mymodel;

    @Before
    public final void setUp() throws Exception {
        context.load().json("/pages/common-page.json", PAGE_PATH);
        Resource pageResource = context.resourceResolver().getResource(PAGE_PATH);
        Page page = pageResource.adaptTo(Page.class);
        context.currentPage(page);

        context.load().json("/models/MyModel.json", RESOURCE_PATH);
        context.registerInjectActivateService(factory);
        context.registerService(AemObjectInjector.class, aemObjectInjector);

        when(request.getResource())
            .thenReturn(context.resourceResolver().getResource(RESOURCE_PATH));
        Resource resource = request.getResource();
        mymodel = resource.adaptTo(MyModel.class);
    }

    @Test
    public void simpleLoadTest() {
        assertNull(mymodel);
    }
}