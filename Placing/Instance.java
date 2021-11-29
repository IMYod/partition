package Placing;

import DataSheet.Student;
import Input.InputData;

//Data and an assignment
public class Instance {
    public InputData input;
    public GroupsPool solution;

    public Instance(InputData input, GroupsPool solution) {
        this.input = input;
        this.solution = solution;
    }

    public Instance() {;  }

    public void setInput(InputData input) {
        this.input = input;
    }
    public InputData getInput() { return input; }

    public void setSolution(GroupsPool solution) {
        this.solution = solution;
    }

    public Student peekUniformStudent () {
        return input.getStudents().peekUniform();
    }
}
