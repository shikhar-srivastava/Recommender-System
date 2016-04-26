# Project Aims: Recommendation System

Immplementing a Feature-Adaptive Recommendation System, analysing large data sets on preferences, and popular activity of similar users 	pertaining to a given criterion. These currently include Music, Movies and Books preferences of over Half-a-Million users in total.

The System User will be asked to provide basic information about his/her preferences in multiple user-feature categories and subsequently provided with recommendations on the same features based on the his/her seed input.

The system learns from the user’s observed choices over time, and optimizes its recommendations further.

## Structure:
  Client Side & Front End: HTML, XML, JavaScript & CSS.
  Server-Side: Java Servlet servicing User Requests and accessing Oracle Database using a JDBC Connection. 
  Database (Currently Local): Oracle 11g XE
  
## Machine Learning:

The Algorithm is heavily inspired from the popular document retreival algorithms used today in ML, but is modified and optimized further, having been written completely in SQL Queries* .

The retreival of the K-Nearest Neighbours of the given System user allows us to answer who the other 'closest' users are to the current user in terms of his/her preferences, and we simply recommend their top rated choices.

The Queries are implemented within the Java Servlets.
