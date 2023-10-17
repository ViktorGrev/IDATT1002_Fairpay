# Fairpay
Dorm group payment application

## IDATT1002 GROUP 3
- Henrik Dybdahl Berg
- Malin Haugland Høli
- Viktor Grevskott
- Victor Kaste
- Vilde Min Vikan

# Innstalation Manuel
## Preface
This manual will show you how to install and run the Fairpay application. The application requires that a few programs are installed on your computer before running and installing the Fairpay application. There are two ways to run the program, however both require a JDK.

### Dependencies

#### Java Development Kit
A Java Development Kit (JDK) is required to run the program. Minimum JDK version is 17. You can download JDK from the Java [downloads page](https://www.oracle.com/cis/java/technologies/downloads/#java17). Choose the JDK that is suitable to the operating system you are running. 


## Option 1: Cloning the project
### Git
Git is a program that be used for version control and makes it possible for you to clone/copy the program over to your computer. You can find the installation page for git [here](https://git-scm.com/downloads). If you are using MacOS you can install git with the help of [Homebrew](https://brew.sh), via the [install git command](https://formulae.brew.sh/formula/git#default). 

### SSH key
You will need a SSH key to clone the project via git. If have not set up a SSH key before, you can find out how to do so [here](https://docs.gitlab.com/ee/user/ssh.html).

### The cloning
Create an empty folder where you want Fairpay to be installed. Now access and open that folder in the terminal. Now you want to run the command. 

`git clone git@gitlab.stud.idi.ntnu.no:team-3/idatt1002-2023-3.git `

Now another folder containing the program will have been cloned into the folder you created. 

### Maven
Maven is a build tool that is used in many Java projects. You can find where to download and how to install maven [here](https://maven.apache.org). If you are using MacOS you can also use Homebrew to install maven, [link to homebrew website here](https://formulae.brew.sh/formula/maven). 

To run the program you will have to access the folder that was cloned into the folder you created. When you are in this folder you need to use the following command. 

`mvn javafx:run`


## Option 2: Precompiled cross-platform executable
You can download the [precompiled jar](https://drive.google.com/file/d/11rVjfxu8PyJufvrarnldbG6a6G-v5Q5M/view?usp=share_link) to quickly run the application.

# Testing
The application is built with JavaFX. Most of the logic lies within controller classes ran by JavaFX. We have made the decision to exclude these classes from testing due to how JavaFX injects data and the difficulty to replicate that. Tests have been made for the classes found in the _no.ntnu.idatt1002.model_ and _no.ntnu.idatt1002.dao_ package. These classes are used in every part of the system, and their functionality is critical to ensure intended behavior for the controller classes.

Test results from Jacoco can be seen in this image.

![Test coverage from jacoco](https://github.com/ViktorGrev/Fairpay/assets/114399917/cfa85a08-9ba7-469f-8861-f04fbbfa7dc4)

# [Wiki](Home)
