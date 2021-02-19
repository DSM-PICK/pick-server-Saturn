INSERT INTO teacher(id, pw, name, office) VALUES('teacherId', 'bcc11c9fd8ab1e74e1bc0717239f8f7be24921abca9d5f31705612834dd1d6da3111df3f7120a0e445a91d6c3158b3e09595cd5d06c034c9997d4be2de5a02ca', 'teacherName', 'teacherOffice');

INSERT INTO club_location(location, floor, priority) VALUES('testLocation', 3, 0);

INSERT INTO club(name, location, teacher, club_head) VALUES('testClub', 'testLocation', 'testName', '3417 Jin');

INSERT INTO class(name, floor, priority, manager) VALUES('testClassroom', 3, 0, 'teacherId');

INSERT INTO student(num, name, club_name, class_name, is_monday_self_study, is_tuesday_self_study) VALUES('3417', 'Jin', 'testClub', 'testClassroom', true, true);

INSERT INTO activity(date, schedule, second_floor_teacher_id, third_floor_teacher_id, forth_floor_teacher_id) VALUES('2021-01-01', 'club', 'teacherId', 'teacherId', 'teacherId');
INSERT INTO activity(date, schedule, second_floor_teacher_id, third_floor_teacher_id, forth_floor_teacher_id) VALUES('2021-01-02', 'self-study', 'teacherId', 'teacherId', 'teacherId');
INSERT INTO activity(date, schedule, second_floor_teacher_id, third_floor_teacher_id, forth_floor_teacher_id) VALUES(NOW(), 'club', 'teacherId', 'teacherId', 'teacherId');

INSERT INTO attendance(id, date, student_num, period, teacher_id, state, memo) VALUES(1, '2021-01-01', '3417', '8', 'teacherId', '출석', null);
INSERT INTO attendance(id, date, student_num, period, teacher_id, state, memo) VALUES(2, '2021-01-02', '3417', '8', 'teacherId', '이동', 'go undefined');
INSERT INTO attendance(id, date, student_num, period, teacher_id, state, memo) VALUES(3, NOW(), '3417', '8', 'teacherId', '외출', null);

INSERT INTO admin(id, pw) VALUES('admin', 'nothing');

INSERT INTO notice(notice_id, content, admin_id, category, created_at) VALUES(1, 'memberNotice', 'admin', 'member', NOW());
INSERT INTO notice(notice_id, content, admin_id, category, created_at) VALUES(2, 'clubNotice', 'admin', 'club', NOW());