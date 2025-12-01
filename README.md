# Blogger

A basic yet functional blogging platform built in Java, enabling users to create posts, read blogs, and interact through comments.

## Table of Contents

- [About](#about)  
- [Features](#features)  
- [Tech Stack](#tech-stack)  
- [Installation & Setup](#installation--setup)  
- [Usage](#usage)  
- [Project Structure](#project-structure)  
- [Contributing](#contributing)  
- [License](#license)  

## About

Blogger is a simple blogging platform built with Java. It allows users to create blog posts, view posts, and add comments — giving a basic but functional experience of writing and reading blogs.  

Use Blogger if you want a lightweight, self-hostable blog engine for experimentation, learning, or small-scale use.  

## Features

- Create new blog posts  
- View list of existing blog posts  
- Add comments to blog posts  
- Basic web interface (UI) for blog + comments  
- (Future) — user authentication, post editing, rich text support  

## Tech Stack

- Language: Java
- Build Tool: Maven (see `pom.xml`)
- Project layout: typical Maven project (`src/` folder)

## Installation & Setup

1. Clone the repository  
   ```bash
   git clone https://github.com/soumyaghosh71/Blogger.git
   cd Blogger
2. Build using Maven
   ```bash
   mvn clean install
2. Run the application
   ```bash
   mvn spring-boot:run
## Installation & Setup
  ```pgsql
  Blogger/
  ├── src/             # source code (Java classes, controllers, models, views, etc.)
  ├── pom.xml          # Maven build configuration
  ├── mvnw, mvnw.cmd   # Maven wrapper
  ├── .gitignore
  └── README.md        # documentation file
  ```

## Contributing

Contributions are welcome! If you’d like to help improve Blogger:
1. Fork the repository
2. Create a feature branch: git checkout -b feature/foo
3. Make your changes & test
4. Submit a pull request describing your changes
Please make sure any new feature or change is well-tested, and document any new steps or configuration required.

If you find a bug or have a suggestion — feel free to open an issue.
