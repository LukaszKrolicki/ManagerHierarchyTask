# XML to JSON maintaining the manager hierarchy

This project is a Spring Boot app that takes an XML file of employees and their managers and turns it into a chierarchical chart in JSON format. It takes a flat XML structure, where each employee entry has an email and optionally a manager's email, and transforms it into a JSON file representing the organization’s structure, showing each employee and their direct reports.\
\
**API endpoint:**\
The application provides an API endpoint (`/build`) that accepts an XML file as input.

When a user uploads the XML file through a tool like Postman, the application temporarily saves the file and then processes it to build the hierarchy.

<figure><img src=".gitbook/assets/image (2).png" alt=""><figcaption></figcaption></figure>

**Building Hierarchy:**

The `HierarchyBuildService` class is the core component that creates the organizational chart. It works as follows:

* **Parse XML File**: Using Java’s `DocumentBuilderFactory`, it reads the XML file and extracts each employee’s email and manager’s email.
* **Map Employees and Managers**: It creates a map of `Employee` objects using their email as the key. For each employee with a listed manager, it assigns them as a "direct report" to that manager’s `Employee` object.
* **Identify Root Manager**: The root manager (top of the hierarchy) is determined by finding the employee who isn’t managed by anyone (i.e., doesn’t appear as a direct report to anyone else).\


After building the employee hierarchy, the `ObjectMapper` from Jackson serializes the hierarchy into JSON format. The JSON shows the top manager and recursively lists each employee's direct reports, creating a readable structure.\
\
Result:

<figure><img src=".gitbook/assets/image (3).png" alt=""><figcaption></figcaption></figure>
