package com.myproject.models;

import static org.junit.Assert.*;

import org.apache.sling.api.SlingHttpServletRequest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.adobe.acs.commons.models.injectors.annotation.impl.AemObjectAnnotationProcessorFactory;
import com.adobe.acs.commons.models.injectors.impl.AemObjectInjector;

import io.wcm.testing.mock.aem.junit.AemContext;


@RunWith(MockitoJUnitRunner.class)
public class MyModelTest {

    @Rule
    public final AemContext context = new AemContext();

    @Mock
    private SlingHttpServletRequest request;


    private static final String RESOURCE_PATH = "/content/parent-page/jcr:content/content/renderer";
    private static final String PAGE_PATH = "/content/parent-page";

    private MyModel mymodel;
    private AemObjectInjector aemObjectInjector;
    private AemObjectAnnotationProcessorFactory factory;

    @Before
    public final void setUp() throws Exception {

        // register model
        // NOTE: this is the alternative to creating an adapter/adapter factory.
        context.addModelsForClasses(MyModel.class);

        // load page and resource from json
        context.load().json("/pages/common-page.json", PAGE_PATH);
        context.load().json("/models/MyModel.json", RESOURCE_PATH);

        // set current page to the page path
        context.currentPage(PAGE_PATH);

        // register ACS AemObjectInjector service
        aemObjectInjector = new AemObjectInjector();
        context.registerService(AemObjectInjector.class, aemObjectInjector);

        // adapt request to model
        mymodel = context.request().adaptTo(MyModel.class);
    }

    @Test
    public void simpleLoadTest() {
        // mymodel is NOT null
        assertNotNull(mymodel);
        // mymodel's page has property 'isRootPage=true', therefor it's the root page
        assertEquals(mymodel.getRootPath(), PAGE_PATH);
    }
}