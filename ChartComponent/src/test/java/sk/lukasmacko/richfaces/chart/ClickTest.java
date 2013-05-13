package sk.lukasmacko.richfaces.chart;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import org.jboss.arquillian.graphene.enricher.findby.FindBy;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

@RunWith(Arquillian.class)
public class ClickTest {

	private static final String WEBAPP_PATH = "src/test/webapp";
	private static final String SERVER_SIDE_PAGE = "faces/server-side.xhtml";
	
	Actions builder;
	Map<String, Coordinate> firstPoints;

	@Drone
	WebDriver browser;

	@ArquillianResource
	URL deploymentUrl;

	@Deployment()
	public static WebArchive createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class)
				.addClass(ClickTest.class)
				.addClass(MyBean.class)
				.addAsWebResource(new File(WEBAPP_PATH, "index.xhtml"))
				.addAsWebResource(new File(WEBAPP_PATH, "server-side.xhtml"))
				.addAsWebInfResource(
						new StringAsset("<faces-config version=\"2.0\"/>"),
						"faces-config.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsLibraries(
						DependencyResolvers
								.use(MavenDependencyResolver.class)
								.artifact(
										"org.richfaces.ui:richfaces-components-ui:4.3.1-SNAPSHOT")
								.artifact(
										"org.richfaces.core:richfaces-core-impl:4.3.1-SNAPSHOT")
								.artifact(
										"sk.lukasMacko.richfaces.cdk:chartComponent:1.0-SNAPSHOT")
								.resolveAsFiles())
				.setWebXML(new File(WEBAPP_PATH, "web.xml"));

	}
	
	@Before
	public void intialization(){
		builder = new Actions(browser);
		//temporary magic numbers - position at chart canvas until
		//jqplot testing suite will be done
		firstPoints = new HashMap<String, Coordinate>();
		firstPoints.put("pieChart", new Coordinate(341, 174));
		firstPoints.put("barChart", new Coordinate(87, 202));
		firstPoints.put("lineChart", new Coordinate(73, 301));
	}

	
	public Action createClickFirstPointAction(String chartId,WebElement canvas){
		Coordinate point = firstPoints.get(chartId);
		Action click = builder.moveToElement(canvas, point.getX(), point.y).click().build();
		return click;
	}
	
	@Test
	@RunAsClient
	public void chartCreated() {
		browser.get(deploymentUrl.toExternalForm());
		Assert.assertNotNull("The chart should be on the page",
				browser.findElement(By.id("frm:pieChart")));
	}

	/**********************************************************************************************
	 * 
	 * Client-side click tests for all chart type
	 * 
	 * each test have same structure: find canvas element which will be clicked
	 * and span which will be changed after click click at proper chart position
	 * - magic numbers assert span change
	 * 
	 ***********************************************************************************************/

	@FindBy(xpath = "//div[@id='frm:pieChart']/canvas[@class='jqplot-event-canvas']")
	WebElement pieCanvas;

	@FindBy(jquery = "#msg")
	WebElement pieSpan;

	@Test
	@RunAsClient
	public void testPieClick() throws InterruptedException {
		browser.get(deploymentUrl.toExternalForm());
				
		Assert.assertNotNull(pieCanvas);

		createClickFirstPointAction("pieChart", pieCanvas).perform();

		Assert.assertTrue(pieSpan.getText().equals("clicked0"));

	}

	@FindBy(xpath = "//div[@id='frm:barChart']/canvas[@class='jqplot-event-canvas']")
	WebElement barCanvas;

	@FindBy(jquery = "#bar")
	WebElement barSpan;

	@Test
	@RunAsClient
	public void testBarClick() {
		browser.get(deploymentUrl.toExternalForm());
		
		createClickFirstPointAction("barChart", barCanvas).perform();

		Assert.assertTrue(barSpan.getText().equals("clicked0"));

	}

	@FindBy(xpath = "//div[@id='frm:lineChart']/canvas[@class='jqplot-event-canvas']")
	WebElement lineCanvas;

	@FindBy(jquery = "#line")
	WebElement lineSpan;

	@Test
	@RunAsClient
	public void testLineClick() {
		browser.get(deploymentUrl.toExternalForm());
		
		createClickFirstPointAction("lineChart", lineCanvas).perform();

		Assert.assertTrue(lineSpan.getText().equals("clicked0"));

	}

	/******************* END of client-side click tests ****************************/


	
	/********************* Server-side test ****************************************/
	@FindBy(id="frm:msg-text")
	WebElement msgText;

	@Test
	@RunAsClient
	public void testServerSidePieClick() {
		browser.get(deploymentUrl.toExternalForm() + SERVER_SIDE_PAGE);
		
		createClickFirstPointAction("pieChart", pieCanvas).perform();
        
		waitAjax().until().element(msgText).is().present();	
		Assert.assertNotNull(msgText);
		System.out.println(msgText.getText());
		Assert.assertTrue(msgText.getText().equals("0"));
	}
	
	@Test
	@RunAsClient
	public void testServerSideBarClick() {
		browser.get(deploymentUrl.toExternalForm()+ SERVER_SIDE_PAGE);
		
		createClickFirstPointAction("barChart", barCanvas).perform();
		
		waitAjax().until().element(msgText).is().present();	
		Assert.assertNotNull(msgText);
		System.out.println(msgText.getText());
		Assert.assertTrue(msgText.getText().equals("0"));

	}
	
	@Test
	@RunAsClient
	public void testServerSideLineClick() {
		browser.get(deploymentUrl.toExternalForm()+ SERVER_SIDE_PAGE);
		
		createClickFirstPointAction("lineChart", lineCanvas).perform();
		
		waitAjax().until().element(msgText).is().present();	
		Assert.assertNotNull(msgText);
		System.out.println(msgText.getText());
		Assert.assertTrue(msgText.getText().equals("0"));

	}
	/************************************************************************************/

	public class Coordinate{
		private int x;
		private int y;

		public Coordinate(int x,int y){
		   this.x=x;
		   this.y=y;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
	}
}
