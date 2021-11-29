package Input;

import DataSheet.DiversityRequestsPool;
import Placing.StudentsPool;

//Holds all the input data
//Used by the algorithms
public class InputData {

    public InputData(StudentsPool students, DiversityRequestsPool diversityRequests) {
        this.students = students;
        this.diversityRequests = diversityRequests;
    }

    StudentsPool students;
    DiversityRequestsPool diversityRequests;

    public StudentsPool getStudents() {
        return students;
    }
    public DiversityRequestsPool getDiversityRequests() {
        return diversityRequests;
    }
}
