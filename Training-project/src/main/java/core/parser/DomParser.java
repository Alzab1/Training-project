package core.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class DomParser {
    public List<UserXML> parse(Document document) {
        NodeList nodeList = document.getElementsByTagName("User");
        List<UserXML> users = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            users.add(getUser(nodeList.item(i)));
        }
        return users;
    }

    private static UserXML getUser(Node node) {
        UserXML userXML = new UserXML();
        Element element = (Element) node;
        userXML.setId(Integer.parseInt(element.getAttribute("id")));
        userXML.setLogin(getTagValue("login", element));
        userXML.setDomain(getTagValue("domain", element));
        userXML.setPassword(getTagValue("password", element));
        return userXML;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}