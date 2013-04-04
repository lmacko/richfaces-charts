package sk.lukasmacko.richfaces.chart;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.thoughtworks.selenium.DefaultSelenium;

import sk.lukasmacko.richfaces.chart.beans.MyBean;
import sk.lukasmacko.richfaces.chart.component.model.NumberChartModel;

@RunWith(Arquillian.class)
public class FirstTest{
	
	private static final String BEAN_SRC="src/test/webapp";
	
	
	@Drone
	WebDriver browser;
	
	@ArquillianResource
    URL deploymentUrl;
	
	
	@Deployment(testable=false)
	public static WebArchive createDeployment(){
		return ShrinkWrap.create(WebArchive.class)
				.addClass(FirstTest.class)
				.addClass(MyBean.class)
				.addAsWebResource(new File(BEAN_SRC,"index.xhtml"))
				.addAsWebInfResource(
                new StringAsset("<faces-config version=\"2.0\"/>"),
                "faces-config.xml")
                .addAsWebInfResource(new File(BEAN_SRC,"web.xml"));
		
                
	}
	
	@Test
	public void test(){
		Assert.assertNull(null);
	}
	
	@Test
	public void chartCreated(){
		browser.get(deploymentUrl.toExternalForm());
		System.out.println(browser.getPageSource());
		Assert.assertNotNull(browser.findElement(By.id("lineChart")));
		
	}
}
