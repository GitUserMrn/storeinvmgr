# storeinvmgr

## Overview

The Store Inventory Management System is a Java-based application designed to help manage product inventory for a store. It provides functionalities for adding, removing, and displaying products, as well as persisting the data to files for future use.

## Features

- **Read and Write to File:** The system supports reading and writing inventory data to a file, ensuring data persistence between program sessions.

- **Add and Remove Products:** Users can add new products to the inventory and remove existing ones.

- **Display Products:** The program can display a list of available products, making it easy to track inventory.

- **Exception Handling:** Robust exception handling is implemented to address potential errors, such as missing products during removal or invlid inputs when adding products.

- **Input Validation:** The application validates user input to ensure accurate and appropriate information, preventing data inconsistencies.

- **Duplicate Product Handling:** The system checks for duplicate product names and prevents the addition of products with identical names.

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- [Other dependencies, if applicable]

### Installation

1. Clone the repository: `git clone https://github.com/GitUserMrn/storeinvmgr`
2. Compile the Java files: `javac *.java`
3. Run the program: `java StoreInventory`

### Usage

1. Run the program and choose options based on the menu prompts.
2. Use the "load" argument to load previously saved data, if desired.
3. Follow on-screen instructions to perform various operations.

## Contribution

Contributions are welcome! If you find a bug or have suggestions for improvement, please open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

