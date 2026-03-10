package com.grades.model;

public class Grades {
    String subjectName;
    double prelimGrade;
    double midtermGrade;
    double finalGrade;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getPrelimGrade() {
        return prelimGrade;
    }

    public void setPrelimGrade(double prelimGrade) {
        this.prelimGrade = prelimGrade;
    }

    public double getMidtermGrade() {
        return midtermGrade;
    }

    public void setMidtermGrade(double midtermGrade) {
        this.midtermGrade = midtermGrade;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
    }

    public Grades() {

    }

    public Grades(String subjectName, double prelimGrade, double midtermGrade, double finalGrade) {
        this.subjectName = subjectName;
        this.prelimGrade = prelimGrade;
        this.midtermGrade = midtermGrade;
        this.finalGrade = finalGrade;
    }

    @Override
    public String toString() {
        return String.format("""
                Subject : %s
                Prelim Grade: %d
                Midterm Grade: %d
                Final Grade: %d
                """, getSubjectName(), getPrelimGrade(), getMidtermGrade(), getFinalGrade());
    }
}
