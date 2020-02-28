# WebServerLogAnalizer

/**A product of Samson Ssali. a.k.a jermsam. (Lead developer at Jitpomi.Inc).
* ~ illustrates use of advanced java8 functional programming techniques
* ~ employs java object data persistence in relational database (MySql)
* ~ and explores abit of SQL aggregate function implimentations
*/
## Motivation and Objective
The networking department for one of my clients explained that they were facing a challenge monitoring their servers. They were looking for a simple tool to help them log patterns and errors rapidly without the need to investigate and read every individual log file. Something that works like [Datadog](https://www.datadoghq.com)

## My Solution
As a proof of concept I decided to write a parser in Java that parses web server access log file, loads the log to MySQL and checks if a given IP makes more than a certain number of requests for the given duration. With this WebServerLogAnalizer, I intend to demonstrate how such a solution looked like. The purpose is to demonstrate to my mentees, students and other fellow developers how they would go about solving such a scenario from scratch. As such:

(1) I have Created a java tool that can parse and load the given log file to MySQL:
	its executable .jar file is located in parser/dist directory

  The delimiter of the log file is pipe (|)
	Below is the sample log.txt file read.
	The webserver log file is to be droped in the C:/ directory
	And Named log.txt.
	i.e: server log file path is: C:/log.txt  

	SAMPLE C:/log.txt
	
	127.0.0.1|-|frank|[2000-10-21.21:55:36 -0700]|"GET /apache_pb.gif HTTP/1.0"|200|2326
	127.0.0.2|-|frank|[2000-10-21.21:55:39 -0703]|"GET /apache_pb.gif HTTP/1.0"|400|2326
	127.0.0.3|-|frank|[2000-10-21.22:55:39 -0703]|"GET /apache_pb.gif HTTP/1.0"|400|2326
	127.0.0.1|-|frank|[2000-10-21.22:55:40 -0703]|"GET /apache_pb.gif HTTP/1.0"|200|2326
	127.0.0.1|-|waren|[2000-10-21.23:55:42 -0700]|"GET /apache_pb.gif HTTP/1.0"|401|2326
	127.0.0.2|-|frank|[2000-10-21.23:56:36 -0700]|"GET /apache_pb.gif HTTP/1.0"|407|2326
	127.0.0.3|-|frank|[2000-10-21.23:56:21 -0703]|"GET /apache_pb.gif HTTP/1.0"|417|2326
	127.0.0.1|-|waren|[2000-10-21.23:59:36 -0700]|"GET /apache_pb.gif HTTP/1.0"|200|2326
	127.0.0.1|-|frank|[2000-10-22.21:55:36 -0700]|"GET /apache_pb.gif HTTP/1.0"|400|2326
	127.0.0.2|-|frank|[2000-10-22.21:55:39 -0703]|"GET /apache_pb.gif HTTP/1.0"|450|2326
	127.0.0.3|-|frank|[2000-10-22.22:55:39 -0703]|"GET /apache_pb.gif HTTP/1.0"|200|2326
	127.0.0.1|-|waren|[2000-10-22.23:55:42 -0700]|"GET /apache_pb.gif HTTP/1.0"|200|2326
	127.0.0.2|-|frank|[2000-10-23.23:56:36 -0700]|"GET /apache_pb.gif HTTP/1.0"|414|2326
	127.0.0.3|-|frank|[2000-10-23.23:56:21 -0703]|"GET /apache_pb.gif HTTP/1.0"|410|2326
	127.0.0.1|-|waren|[2000-10-23.23:59:36 -0700]|"GET /apache_pb.gif HTTP/1.0"|400|2326



(2) The tool takes "startDate", "duration" and "threshold" as command line arguments.
	"startDate" is of "yyyy-MM-dd.HH:mm:ss" format, 
	"duration" can take only "hourly", "daily" as inputs 
	and "threshold" can be an integer.
## Requirements
1. You need to be on a PC that has a C: directory
(sorry if you are on another OS. Feel free to send a PR incase you get time to work on this. The codebase is in the [WebServerLogParser public repository](https://github.com/jermsam/WebServerLogParser))
2. You need to have java installed. If not get it from [here](https://www.java.com/en/). 
3. You also need mysql. If you do not have it on your pc. You may do so using [xampp](https://www.apachefriends.org/index.html)

## This is how to test it.
(1) Start your xampp server. (You may use xampp or any other database management system for mysql) And open the terminal that comes with it. There you can:
(i) Log into the mysql server as the root user
```
mysql -h localhost -u root
```
(ii) Set the password for the root user to "total"
```
SET PASSWORD FOR root@localhost = PASSWORD("total")
```
Incase you intend to use phpMyAdmin, make sure to set the new password into phpmyadmin's `config.inc.php` and restart the Apache server
```
$cfg['Servers'][$i]['password'] = 'total';
```
Otherwise phpMyAdmin may give you the following error:
```
Access denied for user 'user'@'localhost' (using password:YES)
(iii) create a mysql database; calling it "parser"
```CREATE DATABASE parser;```
You could do this with phpMyAdmin

(2) Create a `log.txt` file in your `C:\` directory, then copy and paste the sample logs in `C:/log.txt` above and save.
(You might have to do this in a text editor opened as an administrator to be able to save in the `C:\` directory)

(3) Clone this repo. Navigate to the parser.jar executable file and run it:
```
git clone https://github.com/jermsam/WebServerLogAnalizer.git
cd WebServerLogAnalizer/parser/dist
java -cp "parser.jar" com.ef.Parser --startDate=2000-10-21.21:55:36 --duration=hourly --threshold=11
```
This will create the following tables in the "parser" dtatbase

`statuscomment` a storage for `statusComment_code`s for the different `comment`s
`ipaddress` a storage for the respective ip `address`es and corresponding auto generated `ipAddress_id`s
`ipaddresses_statuscomments` a join table that define how the status comments relate to the ip addresse through `statusComment_code` - `ipAddress_id` combinations
`hibernate_sequence` a relationship automatically generated from our use of the hibernate orm

The tool will also find any IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00 (one hour) as instructed in the above console command and print them to console showing the `BLOCKED IP` and corresponding `COMMENT FROM AGGREGATE LOGS`

## Example (The test I made)
To check for the same results over a period of a day, I ran the following	
```
java -cp "parser.jar" com.ef.Parser --startDate=2000-10-21.21:55:36 --duration=daily --threshold=1
```

The tool was able to find any IPs that made more than 1 requests starting from 2000-10-21.21:55:36 to 2000-10-21.22:55:36 (24 hours) and printed them to console.

	SOMETHING LIKE:
	
	_________________________________________
	BLOCKED IP vs COMMENT AGGREGATE LOGS
	_________________________________________
	IP        :      COMMENT  
	________________________________________
	127.0.0.3 : 400 Bad Request
	127.0.0.3 : 417 Expectation Failed
	127.0.0.2 : 400 Bad Request
	127.0.0.2 : 408 Request Timeout
	127.0.0.1 : 401 Unauthorized
	127.0.0.1 : Not Blocked
	________________________________________

 AND also loaded them to the other MySQL tables mapping ips with comments on why it's blocked.
	
	SOMETHING LIKE:

	TABLE: ipaddress
	_________________________________________
	IP        :      COMMENT  
	________________________________________
	1 	  : 	127.0.0.1
	2 	  : 	127.0.0.2
	3 	  : 	127.0.0.3
	________________________________________

	TABLE: statuscomment
	_________________________________________
	IP        :      COMMENT  
	________________________________________
	200 	  : 	Not Blocked
	400   	  : 	400 Bad Request
	401 	  : 	401 Unauthorized
	407 	  : 	408 Request Timeout
	417 	  : 	417 Expectation Failed 
	________________________________________

	TABLE: ipaddresses_statuscomments
	_________________________________________
	statuscomment_code   :      ipAddress_id  
	________________________________________
		417 	     : 		1
		407   	     : 		2
		400 	     : 		2
		401 	     : 		3
		200 	     : 		3 
	________________________________________
Hence realising the objective even in a more rhobust way than what way required.

## FOR PERFORMANCE REASONS:
~ Used java 8's [lambda expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html) (functional programming) for efficiency and minimal code

~ Used [JPA](https://docs.oracle.com/javaee/6/tutorial/doc/bnbpz.html) with [Hibernate ORM] (https://hibernate.org/orm/) implementation for proper persisting of Java Object with relational SQL Objects and automatic database creation

~ I used [log4j Logger](https://logging.apache.org/log4j/2.x/) to manage Hibernet logs
Also did some commenting... to give a clue about what's going on


## Additional SQL Exercise:


(1) Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.
Ex: Write SQL to find IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00.

		
	SELECT ip, COUNT (ip) AS requests FROM parsetable
	WHERE startDate BETWEEN "2017-01-01 13:00:00" 
	AND "2017-01-01 14:00:00"
	GROUP BY (ip) 
	HAVING requests > 100;

(2) Write MySQL query to find requests made by a given IP. 

	SELECT ip, COUNT (ip) AS requests FROM parsetable
	WHERE startDate BETWEEN "2017-01-01 13:00:00" 
	AND "2017-01-01 14:00:00"
	GROUP BY (ip) 

NB: SNAPSHOTS ON MY TESTS ARE INCLUDED IN A DIRECTORY NAMED sqlSolution

## What should have been done better
1. For better security, I would have made it possible for users of the tool to specify their database credentials instead of forcing them to have a generalised password of `total`
2. To make the tool platform agnostic; I should have made it possible for the user to drop their log files any where and then in the console be prompted to specify the path to their log file.
3. I should have provided a beter way to represent the data results suing a GUI
4. I should have made it possible to prompt the users to decide the number of attempts they want to check the tool against

## Contributions Are Welcome
Incase you want to contribute to this tool, you are free to send in PRs. Find the codebase in this repository below:
[WebServerLogParser public repository](https://github.com/jermsam/WebServerLogParser)
