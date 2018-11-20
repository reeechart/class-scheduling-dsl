grammar ClassSchedule;
/*
 * Parser Rules
 */

create_classroom: 'CREATE CLASSROOM ' CLASSROOM_ID WHITESPACE capacity;
add_facility: 'ADD FACILITY ' CLASSROOM_ID WHITESPACE facilities;
create_lecture: 'CREATE LECTURE ' LECTURE_ID WHITESPACE lecture_params;
add_requirement: 'ADD_REQUIREMENT ' LECTURE_ID WHITESPACE facilities;
create_lecturer: 'CREATE LECTURER ' LECTURER_NAME;
add_lecturer_availability: 'ADD AVAILABILITY ' LECTURER_NAME WHITESPACE schedule;
add_constraint: 'ADD CONSTRAINT ' LECTURE_ID WHITESPACE LECTURE_ID;
add_preference: 'ADD PREFERENCE ' LECTURE_ID WHITESPACE COMPARATOR ' THAN ' hour_of_day;
max_participant: NUM;
capacity: NUM;
credits: NUM;
schedule: day_number WHITESPACE hour_of_day;
day_number: NUM; // 1-5
hour_of_day: NUM; //(([7-9]) | ('1' [0-7]));
facilities: '(' WHITESPACE* facility_name (DELIMITER facility_name)* WHITESPACE* ')';
facility_name: (WORD | CAPITAL_FIRST_LETTER) (WHITESPACE (WORD))*;
lecture_params: '(' WHITESPACE* LECTURER_NAME DELIMITER max_participant DELIMITER credits WHITESPACE*')';
/*
 * Lexer Rules
 */

fragment DIGIT: [0-9];

fragment NONZERO_DIGIT: [1-9];

CAPITAL_FIRST_LETTER: UPPERCASE LOWERCASE+;

LECTURER_NAME: (CAPITAL_FIRST_LETTER) (WHITESPACE (CAPITAL_FIRST_LETTER))*;

//SCHEDULE: (DAY_NUMBER WHITESPACE HOUR_OF_DAY);

//DAY_NUMBER: NUM; // 1-5
//
//HOUR_OF_DAY: NUM; //(([7-9]) | ('1' [0-7]));

NUM: DIGIT | NONZERO_DIGIT DIGIT+ ;

CLASSROOM_ID: 'C' DIGIT DIGIT DIGIT DIGIT;

LECTURE_ID: (UPPERCASE UPPERCASE DIGIT DIGIT DIGIT DIGIT);

COMPARATOR: ('GREATER' | 'LESS');

WORD: UPPERCASE+ | LOWERCASE+;

UPPERCASE: [A-Z];

LOWERCASE: [a-z];

DELIMITER: WHITESPACE* ',' WHITESPACE*;

WHITESPACE : ( ' ' | '\t' | '\r' | '\n' )+;