Hotel Management System
A Java-based GUI application to manage hotel guest records. This system allows hotel staff to add, view, and manage guest information through an intuitive graphical user interface (GUI), with persistent data storage in a text file.

🖥️ Features
Add new guest records via GUI

View all guest records

Data stored in a persistent text file

Clean architecture using MVC design principles

📁 Project Structure
bash
Copy
Edit
HotelManagementSystem/
├── src/
│   └── com/hms/
│       ├── model/             # Guest data model
│       │   └── Guest.java
│       ├── ui/                # Graphical user interface logic
│       │   └── HotelManagementUI.java
│       └── util/              # Utility functions like file handling
│           └── FileHandler.java
├── data/
│   └── guests.txt             # File used for storing guest data
├── bin/                       # Compiled class files
├── .classpath
├── .project
└── module-info.java
🚀 Getting Started
Prerequisites
Java JDK 11 or higher

Eclipse IDE or any Java-compatible IDE

How to Run
Clone or download the project.

Open it in your IDE (e.g., Eclipse).

Ensure guests.txt is present inside the data folder.

Run the HotelManagementUI class.

Or via terminal:

bash
Copy
Edit
javac -d bin src/module-info.java src/com/hms/**/*.java
java -cp bin com.hms.ui.HotelManagementUI
🧠 Technologies Used
Java SE

Swing (for GUI)

File I/O (for data persistence)

📦 Data Storage
Guest details are saved in data/guests.txt using a custom format, handled via the FileHandler utility class.
