package sk.lukasmacko.richfaces.chart;


import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import static org.jboss.arquillian.graphene.Graphene.*;
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

import sk.lukasmacko.richfaces.chart.MyBean;

@RunWith(Arquillian.class)
public class FirstTest {

	private static final String WEBAPP_PATH = "src/test/webapp";

	@Drone
	WebDriver browser;

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
	@RunAsClient
	public void chartCreated() {
		browser.get(deploymentUrl.toExternalForm());
		Assert.assertNotNull("The chart should be on the page",browser.findElement(By.id("pieChart")));
	}
	
	
	/**********************************************************************************************
	 * 
	 *                          Click tests for each chart type
	 * 
	 * test have same structure:
	 *    find canvas element which will be clicked and span which will be changed after click  
	 *    click at proper chart position - magic numbers
	 *    assert span change
	 *   
	 ***********************************************************************************************/
	
	
	@FindBy(jquery="#pieChart>.jqplot-event-canvas")
	WebElement pieCanvas;
	
	@FindBy(jquery="#msg")
	WebElement pieSpan;
	
	@Test
	public void testPieClick() throws InterruptedException{
		browser.get(deploymentUrl.toExternalForm());
		Actions builder = new Actions(browser);
					
		//WebElement canvas = browser.findElement(By.xpath("//div[@id='pieChart']/canvas[@class='jqplot-event-canvas']"));
		Assert.assertNotNull(pieCanvas);
		
		
		Action click = builder.moveToElement(pieCanvas, 200, 100).click().build();
		click.perform();
		
		Assert.assertTrue(pieSpan.getText().equals("clicked1"));
		 
	}
	
	

	@FindBy(jquery="#barChart>.jqplot-event-canvas")
	WebElement barCanvas;
	
	@FindBy(jquery="#bar")
	WebElement barSpan;
		
	@Test
	public void testBarClick(){
		browser.get(deploymentUrl.toExternalForm());
		Actions builder = new Actions(browser);
		
		Action click = builder.moveToElement(barCanvas, 87, 202).click().build();
		click.perform();
		
		Assert.assertTrue(barSpan.getText().equals("clicked0"));
		
	}
	
	
	@FindBy(jquery="#lineChart>.jqplot-event-canvas")
	WebElement lineCanvas;
	
	@FindBy(jquery="#line")
	WebElement lineSpan;
	
	@Test
	public void testLineClick(){
		browser.get(deploymentUrl.toExternalForm());
		Actions builder = new Actions(browser);
		
		Action click = builder.moveToElement(lineCanvas, 73, 301).click().build();
		click.perform();
		
		Assert.assertTrue(lineSpan.getText().equals("clicked0"));
		
	}
	/*******************                 END of click tests            ****************************/
	
	
	
}
