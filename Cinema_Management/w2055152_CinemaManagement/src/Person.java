import java.util.regex.Pattern;

public class Person {
    private String name;
    private String surname;
    private String email;

    public Person(String name, String surname, String email) {
        setName(name);
        setSurname(surname);
        setEmail(email);
    }

    // Getter and Setter for name with validation
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (isValidName(name)) {
            this.name = name.trim();
        } else {
            throw new IllegalArgumentException("Invalid name. Only alphabetic characters are allowed.");
        }
    }

    // Getter and Setter for surname with validation
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (isValidName(surname)) {
            this.surname = surname.trim();
        } else {
            throw new IllegalArgumentException("Invalid surname. Only alphabetic characters are allowed.");
        }
    }

    // Getter and Setter for email with validation
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (isValidEmail(email)) {
            this.email = email.trim();
        } else {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    // Print person information
    public void printInfo() {
        System.out.println("First Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Email: " + email);
    }

    // Private method to validate name (alphabetical characters only)
    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[a-zA-Z]+");
    }

    // Private method to validate email format
    private boolean isValidEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
        return email != null && emailPattern.matcher(email).matches();
    }
}
