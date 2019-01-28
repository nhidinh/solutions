package utilities.helper;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BaseKeyword class
 *
 * @author  Vi Nguyen
 * @version 1.0
 * @since   2018-12-03
 */
public class XmlHelper {

    /**
     * Split xml file to multiple file.
     * @author Vi Nguyen
     * @param originalFile , targetFolder, rootNode, elementNode, keyAttribute
     * @return multiple files per element.
     * @since   2019-01-24
     * @see
     */
    public void splitFile(String originalFile, String targetFolder, String rootNode, String elementNode, String keyAttribute) throws Exception{
        File xmlFile = new File(originalFile);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!

        XPathFactory xfactory = XPathFactory.newInstance();
        XPath xpath = xfactory.newXPath();
        XPathExpression allElementsExpression = xpath.compile("//" + elementNode +"/"+ keyAttribute + "/text()");
        NodeList listElementNodes = (NodeList) allElementsExpression.evaluate(doc, XPathConstants.NODESET);

        //Save all the accounts
        List<String> listKeyAttributes = new ArrayList<String>();
        for (int i = 0; i < listElementNodes.getLength(); ++i) {
            Node productName = listElementNodes.item(i);

            System.out.println(productName.getTextContent());
            listKeyAttributes.add(productName.getTextContent());
        }

        //Now we create the split XMLs
        for (String keyAttr : listKeyAttributes) {
            String xpathQuery = "/"+ rootNode+"/"+ elementNode +"["+ keyAttribute+"='" + keyAttr + "']";

            xpath = xfactory.newXPath();
            XPathExpression query = xpath.compile(xpathQuery);
            NodeList elementNodesFiltered = (NodeList) query.evaluate(doc, XPathConstants.NODESET);

            System.out.println("Found " + elementNodesFiltered.getLength() + " " +
                    elementNode+" for "+ keyAttribute +" " + keyAttr);

            //We store the new XML file in supplierName.xml e.g. Sony.xml
            Document suppXml = dBuilder.newDocument();

            //we have to recreate the root node <Accounts>
            Element root = suppXml.createElement(rootNode);
            suppXml.appendChild(root);
            for (int i = 0; i < elementNodesFiltered.getLength(); ++i) {
                Node node = elementNodesFiltered.item(i);

                //we append a product (cloned) to the new file
                Node clonedNode = node.cloneNode(true);
                suppXml.adoptNode(clonedNode); //We adopt the orphan :)
                root.appendChild(clonedNode);
            }

            //At the end, we save the file XML on disk
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(suppXml);

            StreamResult result = new StreamResult(new File(targetFolder + keyAttr.trim() + ".xml"));
            transformer.transform(source, result);

            System.out.println("Done splitting for " + keyAttr);
        }
    }

    /**
     * Compare 2 xml files to find differences.
     * @author Vi Nguyen
     * @param file1 , file2, diffList
     * @return differences as files
     * @since   2019-01-24
     * @see
     */
    public void compare2XMLFiles(String file1, String file2, String diffList) throws SAXException, IOException {
        // reading two xml file to compare in Java program
        FileInputStream fis1 = new FileInputStream(file1);
        FileInputStream fis2 = new FileInputStream(file2);

        // using BufferedReader for improved performance
        BufferedReader source = new BufferedReader(new InputStreamReader(fis1));
        BufferedReader target = new BufferedReader(new InputStreamReader(fis2));

        //configuring XMLUnit to ignore white spaces
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);

        //comparing two XML using XMLUnit in Java
        List differences = compareXML(source, target);

        //showing differences found in two xml files
        printDifferencesToFile(differences, diffList);
    }

    /**
     * Compare 2 xml files to find differences.
     * @author Vi Nguyen
     * @param source , target
     * @return differences as list
     * @since   2019-01-24
     * @see
     */
    public List compareXML(Reader source, Reader target) throws SAXException, IOException {
        //creating Diff instance to compare two XML files
        Diff xmlDiff = new Diff(source, target);

        //for getting detailed differences between two xml files
        DetailedDiff detailXmlDiff = new DetailedDiff(xmlDiff);
        return detailXmlDiff.getAllDifferences();
    }

    /**
     * Compare 2 xml files to find differences.
     * @author Vi Nguyen
     * @param differences , diffFile
     * @return generate files contain different
     * @since   2019-01-24
     * @see
     */
    public void printDifferencesToFile(List differences, String diffFile) throws IOException {

        int totalDifferences = differences.size();

        System.out.println("===============================");
        System.out.println("Total differences : " + totalDifferences);
        System.out.println("================================");

        BufferedWriter output = null;

        try {
            File file = new File(diffFile);
            output = new BufferedWriter(new FileWriter(file));
            output.write("Total differences : " + totalDifferences + "\n\n");

            for (Object difference : differences) {
                System.out.println(difference);
                output.write(String.valueOf(difference) + "\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null!= output) {
                output.close();
            }
        }
    }
}
