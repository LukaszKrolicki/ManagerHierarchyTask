package com.app.keepittask.services;

import com.app.keepittask.models.Employee;
import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class HierarchyBuildService {

    public Employee buildHierarchyJSON(File xmlFile) throws Exception {
        Document doc = (Document) DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
        doc.getDocumentElement().normalize();

        Map<String, Employee> employees = new HashMap<>();
        Map<String, String> managerMap = new HashMap<>();
        NodeList nodeList = doc.getElementsByTagName("employee");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String email = getFieldValue(element, "email");
            String managerEmail = getFieldValue(element, "manager");

            employees.putIfAbsent(email, new Employee(email));
            if (managerEmail != null && !managerEmail.isEmpty()) {
                employees.putIfAbsent(managerEmail, new Employee(managerEmail));
                employees.get(managerEmail).addDirectReport(employees.get(email));
                managerMap.put(email, managerEmail);
            }
        }

        String rootEmail = employees.keySet().stream()
                .filter(email -> !managerMap.containsKey(email))
                .findFirst()
                .orElse(null);

        return employees.get(rootEmail);
    }

    private String getFieldValue(Element element, String id) {
        NodeList fields = element.getElementsByTagName("field");
        for (int i = 0; i < fields.getLength(); i++) {
            Element field = (Element) fields.item(i);
            if (field.getAttribute("id").equals(id)) {
                return field.getTextContent();
            }
        }
        return null;
    }
}