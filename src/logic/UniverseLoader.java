package logic;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class UniverseLoader {
    private Universe universe;
    public Universe loadUniverse(String fileName) {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        factory.setValidating(true);
        factory.setNamespaceAware(false);
        SAXParser parser;
        InputStream xmlData = null;

        try {
            xmlData = new FileInputStream(fileName);
            parser = factory.newSAXParser();
            parser.parse(xmlData, new XMLParser());

        } catch (FileNotFoundException e) {
            System.out.println("File with universe doesn`t exist!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return universe;
    }

    class XMLParser extends DefaultHandler {
        private Player currentPlayer;
        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            if (qName.equals("player")) {
                currentPlayer = new Player(Integer.parseInt(attributes.getValue("id")), universe);
                universe.addPlayer(currentPlayer);
            } else if (qName.equals("ship")) {
                if (currentPlayer == null) {
                    try {
                        throw new Exception("Wrong format");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                SpaceShip s = new SpaceShip(Double.parseDouble(attributes.getValue("x")),
                                            Double.parseDouble(attributes.getValue("y")),
                                            currentPlayer, IdGenerator.getNewId(), universe);
                currentPlayer.addShip(s);
            } else if (qName.equals("planet")) {
                Planet p = new Planet(Double.parseDouble(attributes.getValue("x")),
                                        Double.parseDouble(attributes.getValue("y")),
                                        currentPlayer, IdGenerator.getNewId(),
                                        Double.parseDouble(attributes.getValue("radius")));
                universe.addPlanet(p);
            }
            super.startElement(uri, localName, qName, attributes);
        }

        @Override
        public void characters(char[] c, int start, int length)
                throws SAXException {
            super.characters(c, start,  length);
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            if (qName.equals("player")) {
                currentPlayer = null;
            }
            super.endElement(uri,localName, qName);
        }

        @Override
        public void startDocument() throws SAXException {
            universe = new Universe();
            super.startDocument();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            System.out.println("Load finished!");
       }
    }
}


