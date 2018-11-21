grammar ClassSchedule;
/*
 * Parser Rules
 */

program: command+;
command: (create_classroom |
          create_lecture |
          add_facility |
          add_requirement |
          create_lecturer |
          add_lecturer_availability |
          add_constraint|
          add_preference |
          show_timetable)
          END_OF_COMMAND;
create_classroom: 'CREATE CLASSROOM ' classroom_id WHITESPACE capacity;
add_facility: 'ADD FACILITY ' classroom_id WHITESPACE facilities;
create_lecture: 'CREATE LECTURE ' LECTURE_ID WHITESPACE lecture_params;
add_requirement: 'ADD REQUIREMENT ' LECTURE_ID WHITESPACE facilities;
create_lecturer: 'CREATE LECTURER ' lecturer_name;
add_lecturer_availability: 'ADD AVAILABILITY ' lecturer_name WHITESPACE schedule;
add_constraint: 'ADD CONSTRAINT ' LECTURE_ID WHITESPACE LECTURE_ID;
add_preference: 'ADD PREFERENCE ' LECTURE_ID WHITESPACE COMPARATOR 'THAN ' hour_of_day;
show_timetable: 'SHOW TIMETABLE';

classroom_id: CLASSROOM_ID;
lecture_id: LECTURE_ID;
max_participant: NUM;
capacity: NUM;
credits: NUM;
schedule: day_number WHITESPACE hour_of_day;
day_number: NUM; // 1-5
hour_of_day: NUM; //(([7-9]) | ('1' [0-7]));
facilities: '(' WHITESPACE* facility_name (DELIMITER facility_name)* DELIMITER* WHITESPACE* ')';
facility_name: (WORD) (WHITESPACE (WORD))*;
lecturer_name: (WORD) (WHITESPACE (WORD))*;
lecture_params: '(' WHITESPACE* lecturer_name DELIMITER max_participant DELIMITER credits WHITESPACE*')';

/*
 * Lexer Rules
 */

fragment DIGIT: [0-9];

fragment NONZERO_DIGIT: [1-9];

WORD: UPPERCASE+ | LOWERCASE+ | CAPITAL_FIRST_LETTER;

CAPITAL_FIRST_LETTER: UPPERCASE LOWERCASE+;

NUM: DIGIT | NONZERO_DIGIT DIGIT+ ;

CLASSROOM_ID: 'C' DIGIT DIGIT DIGIT DIGIT;

LECTURE_ID: (UPPERCASE UPPERCASE DIGIT DIGIT DIGIT DIGIT);

COMPARATOR: ('GREATER ' | 'LESS ');

UPPERCASE: [A-Z];

LOWERCASE: [a-z];

DELIMITER: WHITESPACE* ',' WHITESPACE*;

WHITESPACE : ( ' ' | '\t' | '\r' | '\n' )+;

END_OF_COMMAND: WHITESPACE* ';' WHITESPACE*;