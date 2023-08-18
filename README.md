# bank-app

## Summary
This is a simplified bank app. 

The target user is the customer.

It has some features that can be used to test :

1. **View Balance**, here you can see your balance and account number.
1. **Transfer**, you can transfer to another account which is a registered user.
1. **View Transaction**, you can see your previous transaction here.
1. **Edit Profile**, you can edit your profile to match with your preferences here.

## Why
This project is intended to show the simplicity of banking apps with easy to understand features. <br>It can be used as a guide for beginners in web development.

## The First Thing
I assume you already have your IDE and database installed. This is built using **Java 17**. Also, I assume you already have **Java 17** or **higher** on your computer.

## Config
1. Open `src/main/resources/application.properties` to input the localhost jdbc url, username, and password.
1. Open `src/main/resources/application-test.properties`, and input the jdbc url, username, and password. For this, I recommend the separate db, because it is to do with unit testing.

## How to run
1. If you are using IntelliJ IDEA, on the right side open the `Gradle` menu, and choose `Reload all gradle projects`.
1. Run the app and wait the process for a while.
1. Open the browser, and open `http://localhost:8080`

## How to test
1. Right click `src/test` folder, and choose `Run test..`

## Is this Open Source?
Actually, this is not open source (yet). For now, this is a showcase project for my portfolio. You can learn from the source as much as you can. Feel free to run, test, and debug this app. Any feedback is welcomed!