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
add_preference: 'ADD PREFERENCE ' lecturer_name WHITESPACE time_preferences;
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
time_preferences: '(' time_preference (DELIMITER* time_preference)* ')';
time_preference: day_number WHITESPACE TIME_COMPARATOR WHITESPACE* hour_of_day;

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

TIME_COMPARATOR: ('BEFORE ' | 'AFTER ');

UPPERCASE: [A-Z];

LOWERCASE: [a-z];

DELIMITER: WHITESPACE* ',' WHITESPACE*;

WHITESPACE : ( ' ' | '\t' | '\r' | '\n' )+;

END_OF_COMMAND: WHITESPACE* ';' WHITESPACE*;