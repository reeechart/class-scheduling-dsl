# DusDus Lecture Scheduler

An interpreter for DusDus Scheduling Language. DusDus Scheduling Language is a new language for lecture scheduling.  

**Developed by:**  

| Name | NIM |
| ---- | --- |
| Felix Limanta | 13515065 |
| Ferdinandus Richard | 13515066 |
| Roland Hartanto | 13515107 |
| Vincent Endrahadi | 13515117 |

## Prerequisites
1. Maven installed

## Installation
1. Download or clone from 
http://gitlab.informatika.org/IF4150-ClassSchedulingDSL/class-scheduling-dsl
2. Open terminal/powershell and go inside the folder.  
    ```
    cd class-scheduling-dsl
    ```
3. Enter maven command 
    ```
    mvn install
    ```

## How to use
1. Go inside the target folder.
    ```
    cd target
    ``` 
2. Insert text file to the folder. 
3. With the same terminal/powershell condition, enter command below. 
    ```
    java -jar ClassSchedulingDSL-1.0-SNAPSHOT-jar-with-dependencies.jar <filename>
    ```
4. Enter the language syntax as defined in Language Syntax.



## How it works 
DusDus interprets DusDus source code and automatically arrange schedule based on the given source code. The output of the program is the arranged schedule.

## DusDus Scheduling Language Syntax
#### 1. Create new classroom
**Description:**  
Creates a new classroom based on given **classroom id** and **capacity**.
```
CREATE CLASSROOM <classroom_id> <capacity>
```

#### 2. Add facilities to a classroom
**Description:**  
Adds facilities to a classroom based on given **classroom id**.
```
ADD FACILITY <classroom_id> (<facility> [, <facility>...])
```

#### 3. Create new lecturer
**Description:**  
Creates a new lecturer with **lecturer name** as the parameter.
```
CREATE LECTURER <lecturer_name>
```

#### 4. Add lecturer availability
**Description:**  
Adds **list of availability schedule** of a lecturer.
```
ADD AVAILABILITY <lecturer_name> (<day(0-4)> <hour_of_day(0-10)> [, <day(0-4)> <hour_of_day(0-10)...])
```

#### 5. Create lecture
**Description:**  
Creates a new lecture based given on **lecture id, lecturer name, max participant, and credits**.
```
CREATE LECTURE <lecture_id> <lecturer_name> <max_participant> <credits>
```

#### 6. Add lecture requirement
**Description:**  
Add list of lecture **classroom facility requirement**.
```
ADD REQUIREMENT <lecture_id> (<facility> [, <facility>...])
```

#### 7. Add constraint
**Description:**  
Add constraint to lectures. A lecture pair with constraint cannot be occured at the same time. 
```
ADD CONSTRAINT <lecture_id> <lecture_id>
```

#### 8. Add lecturer time preferences
**Description:**  
Add time preferences of lecturer with given id.
```
ADD LECTURER PREFERENCES <lecturer_name> (<day> BEFORE/AFTER <hour> <priority> [,<day> BEFORE/AFTER <hour> <priority>...])

ADD LECTURER PREFERENCES <lecturer_name> (<day> BETWEEN <hour> <hour> <priority> [,<day> BETWEEN <hour> <hour> <priority>...])
```

#### 7. Add classroom preferences
**Description:**  
Add classroom preferences of a lecture with given id.
```
ADD CLASSROOM PREFERENCES <lecture_id> (<classroom_id> <priority> [, <classroom_id> <priority>, ...]
```
