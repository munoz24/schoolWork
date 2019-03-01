#ifndef GRADE_H

void grade(int Fd, char *name, char *students, int assignments[1000][100], int exams[1000][100], int addAs, int addEx);
void gradeA(int Fd, char *number, char *students, int assignments[1000][100], int addAs, int countSt);
void gradeE(int Fd, char *number, char *students, int exams[1000][100], int addEx, int countSt);

#endif
