package ahmet.com.pwl.Interface;

import java.util.List;

import ahmet.com.pwl.Model.TeachersDepartment;

public interface LoadDepartmetMain {

    void onLoadDepartmetMainSuccess(List<TeachersDepartment> mListTeachersDepartment);
    void onLoadDepartmetMainFailed(String error);
}
