package sk.lukasmacko.richfaces.chart;


import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.thoughtworks.selenium.DefaultSelenium;

import sk.lukasmacko.richfaces.chart.beans.MyBean;

@RunWith(Arquillian.class)
public class FirstTest {

	private static final String WEBAPP_PATH = "src/test/webapp";

	@Drone
	DefaultSelenium browser;

	@ArquillianResource
	URL deploymentUrl;

	@Deployment(testable=false)
	public static WebArchive createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class)
				.addClass(FirstTest.class)
				.addClass(MyBean.class)
				.addAsWebResource(new File(WEBAPP_PATH, "index.xhtml"))
				.addAsWebInfResource(
						new StringAsset("<faces-config version=\"2.0\"/>"),
						"faces-config.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml")		
				.addAsLibraries(
						DependencyResolvers
						.use(MavenDependencyResolver.class)
						.artifact("org.richfaces.ui:richfaces-components-ui:4.3.1-SNAPSHOT")
						.artifact("org.richfaces.core:richfaces-core-impl:4.3.1-SNAPSHOT")
						.artifact("sk.lukasMacko.richfaces.cdk:chartComponent:1.0-SNAPSHOT")
						.resolveAsFiles())
				.setWebXML(new File(WEBAPP_PATH, "web.xml"));

	}

	
	@Test
	public void chartCreated() {
		browser.open(deploymentUrl.toExternalForm());
		System.out.println(browser.getHtmlSource());
		Assert.assertTrue("Chart shoul be on page",browser.isElementPresent("xpath=//div[@id='lineChart']"));
	}
	
	@Test
	public void testClick() throws InterruptedException{
		browser.open(deploymentUrl.toExternalForm());
		System.out.println(browser.getClass());
	
		Assert.assertTrue(browser.isElementPresent("xpath=//canvas[@class='jqplot-event-canvas']"));
		
		browser.clickAt("xpath=//canvas[@class='jqplot-event-canvas']", "200,100");
		Assert.assertTrue(browser.isElementPresent("xpath=//span[contains(text(), 'clicked1')]"));
	}
	
	
}
