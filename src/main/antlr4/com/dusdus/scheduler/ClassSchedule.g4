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
          add_lecturer_time_preferences |
          show_timetable |
          add_classroom_preferences)
          END_OF_COMMAND;
create_classroom: 'CREATE CLASSROOM ' classroom_id WHITESPACE capacity;
add_facility: 'ADD FACILITY ' classroom_id WHITESPACE facilities;
create_lecture: 'CREATE LECTURE ' LECTURE_ID WHITESPACE lecture_params;
add_requirement: 'ADD REQUIREMENT ' LECTURE_ID WHITESPACE facilities;
create_lecturer: 'CREATE LECTURER ' lecturer_name;
add_lecturer_availability: 'ADD AVAILABILITY ' lecturer_name WHITESPACE schedule;
add_constraint: 'ADD CONSTRAINT ' LECTURE_ID WHITESPACE LECTURE_ID;
add_classroom_preferences: 'ADD CLASSROOM PREFERENCES ' LECTURE_ID WHITESPACE* list_of_prefered_classroom;
add_lecturer_time_preferences: 'ADD LECTURER PREFERENCES ' lecturer_name WHITESPACE time_preferences;
show_timetable: 'SHOW TIMETABLE';
show_classes: 'SHOW CLASSES';
show_lectures: 'SHOW LECTURES';

classroom_id: CLASSROOM_ID;
lecture_id: LECTURE_ID;
max_participant: NUM;
capacity: NUM;
credits: NUM;
time_slot: (WHITESPACE* day_number WHITESPACE hour_of_day WHITESPACE*);
schedule: '(' time_slot (DELIMITER* time_slot)* ')';
day_number: NUM; // 1-5
hour_of_day: NUM; //(([7-9]) | ('1' [0-7]));
facilities: '(' WHITESPACE* facility_name (DELIMITER facility_name)* DELIMITER* WHITESPACE* ')';
facility_name: (WORD) (WHITESPACE (WORD))*;
lecturer_name: (WORD) (WHITESPACE (WORD))*;
lecture_params: '(' WHITESPACE* lecturer_name DELIMITER max_participant DELIMITER credits WHITESPACE*')';
time_preferences: '(' WHITESPACE* time_preference (DELIMITER* time_preference)* WHITESPACE* ')';
time_preference: day_number WHITESPACE TIME_COMPARATOR WHITESPACE* hour_of_day (WHITESPACE hour_of_day)? WHITESPACE* priority;
list_of_prefered_classroom: '(' WHITESPACE* weighted_classroom (DELIMITER weighted_classroom)* DELIMITER* WHITESPACE* ')';
weighted_classroom: '(' WHITESPACE* classroom_id DELIMITER priority WHITESPACE*')';
priority: NUM;

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

TIME_COMPARATOR: ('BEFORE ' | 'AFTER ' | 'BETWEEN ');

UPPERCASE: [A-Z];

LOWERCASE: [a-z];

DELIMITER: WHITESPACE* ',' WHITESPACE*;

WHITESPACE : ( ' ' | '\t' | '\r' | '\n' )+;

END_OF_COMMAND: WHITESPACE* ';' WHITESPACE*;