package uk.ac.shef.semweb;

import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

public class ArtistExtractor extends FileExtractor 
{
	
    public ArtistExtractor(Model ontology, Document xml, String url) throws XPathExpressionException 
    {
    	super(ontology, xml, url);
    }

    @Override
    public void extract() 
    {
        Resource artistRes = this.ontology.createResource(getUri());
        artistRes.addProperty(RDF.type, this.artistClas);

        artistRes.addProperty(this.nameProp, getSingleProp(this.titleNode));
        artistRes.addProperty(this.biographyProp, getSingleProp(this.biographyNode));
        artistRes.addProperty(this.imageProp, getSingleProp(this.imageNode));
        artistRes.addProperty(this.websiteProp, getSingleProp(this.websiteNode));

        for (int i=0; i<this.albumNodes.getLength(); i++){
            Node albumNode = this.albumNodes.item(i);
            Resource albumRes = this.ontology.createResource(getUrlAttr(albumNode));
            albumRes.addProperty(RDF.type, this.albumClas);
            artistRes.addProperty(this.albumProp, albumRes);
            
            albumRes.addProperty(this.titleProp, getSingleProp(albumNode));
        }
        
        //TODO dbpedia
        //genre, associatedband, wikipage,hometown
    }
}
