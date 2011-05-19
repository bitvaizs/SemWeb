package uk.ac.shef.semweb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.junit.Before;

import com.hp.hpl.jena.rdf.model.Model;


public class UrlFileReaderTest extends TestCase {

    private UrlFileReader urlFileReader;
    
    @Override
    @Before
    public void setUp(){
	this.urlFileReader = new UrlFileReader();
    }
    public void testReadFile() throws FileNotFoundException{
	List<String> urls = this.urlFileReader.readFile(UrlFileReader.INPUT_PATH);
	assertFalse(urls.isEmpty());
	assertFalse(urls.get(0).isEmpty());
	
    }
    public void testOpenUrl() throws ClientProtocolException, IOException{
	String url = "http://poplar.dcs.shef.ac.uk/~u0082/intelweb2/%3fq=taxonomy/term/1&amp;page=7";
	HttpEntity entity = this.urlFileReader.openUrl(url);
	assertNotNull(entity);
    }
    public void testIsXML() throws ClientProtocolException, IOException{
	String notXmlUrl = "http://poplar.dcs.shef.ac.uk/~u0082/intelweb2/%3fq=taxonomy/term/1&amp;page=7";
	assertFalse(this.urlFileReader.isXML(this.urlFileReader.openUrl(notXmlUrl)));
	
	String isXmlUrl = "http://poplar.dcs.shef.ac.uk/~u0082/intelweb2/%3fq=users/user10/xml";
	assertTrue(this.urlFileReader.isXML(this.urlFileReader.openUrl(isXmlUrl)));
	
    }
    public void testParseRdf() throws IllegalStateException, ClientProtocolException, IOException{
	Model model = this.urlFileReader.parseRdf(UrlFileReader.ONTOLOGY_URL);
	model.write(System.out, "N-TRIPLE");
	assertNotNull(model);
	
    }
}
