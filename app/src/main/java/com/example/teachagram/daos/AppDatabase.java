package com.example.teachagram.daos;

import static java.lang.String.format;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.teachagram.R;
import com.example.teachagram.data.Backup;
import com.example.teachagram.data.ClassStandard;
import com.example.teachagram.data.Classes;
import com.example.teachagram.data.Objective;
import com.example.teachagram.data.Progress;
import com.example.teachagram.data.Standard;
import com.example.teachagram.data.Student;
import com.example.teachagram.data.SubjectName;
import com.example.teachagram.data.Unit;
import com.example.teachagram.helper.fakeData.Val;
import com.example.teachagram.load.AdditionalMathematics1Sudan;
import com.example.teachagram.load.AddittionalMathematics2Sudan;
import com.example.teachagram.load.CambridgeAdditionalMathematics;
import com.example.teachagram.load.StandardModel;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Database(entities = {Classes.class, Objective.class, Progress.class, Standard.class,
        Student.class, SubjectName.class, Unit.class, ClassStandard.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    abstract ClassDao classDao();

    abstract ObjectiveDao objectiveDao();

    abstract ProgressDao progressDao();

    abstract StandardDao standardDao();

    abstract StudentDao studentDao();

    abstract SubjectDao subjectDao();

    abstract UnitDao unitDao();

    abstract ClassStandardDao classStandardDao();

    private void deleteObjective(Objective objective) {
        objectiveDao().delete(objective);
        Standard s = standardDao().getStandard(objective.standardName);
        List<Classes> list2 = new ArrayList<>();
        if (!list2.isEmpty()) {
            for (Classes c : list2) {
                Progress p = progressDao().getProgresses(c.name, objective.index);
                progressDao().delete(p);
            }
        }
    }

    public void deleteObjectiveAsync(Objective objective) {
        AsyncTask.execute(() -> {
            deleteObjective(objective);
        });
    }

    public LiveData<Boolean> deleteObjectiveAsync(List<Objective> objectives) {
        MutableLiveData<Boolean> done = new MutableLiveData<Boolean>();
        AsyncTask.execute(() -> {
            for (Objective o : objectives)
                deleteObjective(o);
            done.postValue(true);
        });
        return done;
    }

    private void deleteUnit(Unit unit) {
        unitDao().delete(unit);
        objectiveDao().deleteByUnit(unit.standardName, unit.name);
    }

    public LiveData<Boolean> deleteUnitAsync(List<Unit> unit) {
        MutableLiveData<Boolean> done = new MutableLiveData<Boolean>();
        AsyncTask.execute(() -> {
            for (Unit u : unit)
                deleteUnit(u);
            done.postValue(true);
        });
        return done;
    }

    private void deleteStandard(Standard standard) {
        standardDao().delete(standard);
        unitDao().deleteByStandard(standard.name);
        objectiveDao().deleteByStandard(standard.name);
        classStandardDao().deleteByStandardName(standard.name);
    }

    public LiveData<Boolean> deleteStandardAsync(List<Standard> standards) {
        MutableLiveData<Boolean> done = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            for (Standard c : standards)
                deleteStandard(c);
            done.postValue(true);
        });
        return done;
    }

    private void deleteClass(Classes classes) {
        classStandardDao().deleteByClassName(classes.name);
        studentDao().deleteByClass(classes.name);
        progressDao().deleteByClass(classes.name);
        classDao().delete(classes);
    }

    public LiveData<Boolean> deleteClassAsync(List<Classes> classes) {
        MutableLiveData<Boolean> done = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            for (Classes c : classes)
                deleteClass(c);
            done.postValue(true);
        });
        return done;
    }

    public LiveData<Boolean> deleteStudentAsync(List<Student> students) {
        MutableLiveData<Boolean> done = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            for (Student c : students)
                studentDao().delete(c);
            done.postValue(true);
        });
        return done;
    }


    public LiveData<Boolean> editObjectiveAsync(Objective objective, String text, String standardName
            , String unitName) {
        MutableLiveData<Boolean> done = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            Objective o = objectiveDao().getObjective(text, unitName, standardName);
            if (o != null && !text.equals(objective.text))
                done.postValue(false);
            else {
                editObjective(objective, text, standardName, unitName);
                done.postValue(true);
            }
        });
        return done;
    }

    private void editObjective(Objective objective, String text, String standardName
            , String unitName) {
        if (objectiveDao().getObjective(text, unitName, standardName) != null)
            return;
        objective.text = text;
        objective.standardName = standardName;
        objective.unitName = unitName;
        objectiveDao().insertAll(objective);
    }

    private void editObjective(List<Objective> objectives, String standardName
            , String unitName) {
        for (Objective o : objectives)
            editObjective(o, o.text, standardName, unitName);
    }

    private void editObjective(List<Objective> objectives, String standardName) {
        for (Objective o : objectives)
            editObjective(o, o.text, standardName, o.unitName);
    }

    public LiveData<Boolean> editUnitAsync(Unit unit, String unitName, String standardName) {
        MutableLiveData<Boolean> done = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            if (unitDao().getUnit(unitName, standardName) != null
                    && !unit.name.equals(unitName))
                done.postValue(false);
            else {
                editUnit(unit, unitName, standardName);
                done.postValue(true);
            }
        });
        return done;
    }

    private void editUnit(Unit unit, String unitName, String standardName) {
        if (unitDao().getUnit(unitName, standardName) != null)
            return;
        List<Objective> objectives = objectiveDao().getObjectives(unit.name, unit.standardName);
        editObjective(objectives, standardName, unitName);
        unit.name = unitName;
        unit.standardName = standardName;
        unitDao().insertAll(unit);

    }

    private void editUnit(List<Unit> units, String standardName) {
        for (Unit u : units)
            editUnit(u, u.name, standardName);
    }

    private void editClassStandard(ClassStandard c, String classs, String standard) {
        c.standardName = standard;
        c.className = classs;
        classStandardDao().insertAll(c);
    }

    private void editClassStandard(List<ClassStandard> list, String standard) {
        for (ClassStandard c : list)
            editClassStandard(c, c.className, standard);
    }

    private void editClassStandard(List<ClassStandard> list, String classs, int x) {
        for (ClassStandard c : list)
            editClassStandard(c, classs, c.standardName);
    }

    public LiveData<Boolean> editStandardAsync(Standard standard, String name, String subject, Integer color) {
        MutableLiveData<Boolean> done = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            if (standardDao().getStandard(name) != null && !name.equals(standard.name)) {
                done.postValue(false);
            }else{
                editStandard(standard, name, subject, color);
                done.postValue(true);
            }

        });
        return done;
    }

    private void editStandard(Standard standard, String name, String subject, Integer color) {

        List<Objective> objectives = objectiveDao().getObjectives(standard.name);
        List<Unit> units = unitDao().getUnits(standard.name);
        List<ClassStandard> classStandards = classStandardDao().getByStandardName(standard.name);
        editObjective(objectives, name);
        editUnit(units, name);
        editClassStandard(classStandards, name);
        standard.name = name;
        standard.subjectName = subject;
        standard.color = color;
        standardDao().insertAll(standard);
    }

    public LiveData<Boolean> editStudentAsync(Student student, String name, String className, Integer color) {
        MutableLiveData<Boolean> done = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            editStudent(student, name, className, color);
            done.postValue(true);
        });
        return done;
    }

    private void editStudent(Student student, String name, String className, Integer color) {
        if (studentDao().getStudents(name, className) != null)
            return;
        student.name = name;
        student.className = className;
        student.color = color;
        studentDao().insertAll(student);
    }

    private void editStudent(List<Student> student, String className) {
        for (Student s : student)
            editStudent(s, s.name, className, s.color);
    }

    private void editProgress(List<Progress> list, String classes) {
        for (Progress p : list) {
            editProgress(p, classes, p.objectiveId, p.done);
        }
    }

    private LiveData<Boolean> editProgressAsync(Progress p, String classes, Integer id, Boolean done) {
        MutableLiveData<Boolean> done1 = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            editProgress(p, classes, id, done);
            done1.postValue(true);
        });
        return done1;
    }

    private void editProgress(Progress p, String classes, Integer id, Boolean done) {
        p.className = classes;
        p.done = done;
        p.objectiveId = id;
        progressDao().insertAll(p);

    }

    public LiveData<Boolean> editClassAsync(Classes classes, String name, Integer color ,String standard) {
        MutableLiveData<Boolean> done = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            if(classDao().getClassByName(name) != null &&
                    !name.equals(classes.name)){
                done.postValue(false);
            }else {
                Standard newS;
                Standard old = getStandardForClass(classes).get(0);
                newS = standardDao().getStandard(standard);
                if (newS.index != old.index)
                    attachClassToStandard(classes, newS);
                editClass(classes, name, color);
                done.postValue(true);
            }
        });
        return done;

    }

    private void editClass(Classes classes, String name, Integer color) {
        if (classDao().getClassByName(name) != null)
            return;
        List<ClassStandard> classStandards = classStandardDao().getByClassName(classes.name);
        editClassStandard(classStandards, name, 0);
        List<Student> students = studentDao().getStudents(classes.name);
        editStudent(students, name);
        List<Progress> progresses = progressDao().getProgresses(classes.name);
        editProgress(progresses, name);
        classes.color = color;
        classes.name = name;
        classDao().insertAll(classes);
    }

    private List<Classes> getClassForStandard(Standard standard) {
        List<ClassStandard> list1 = classStandardDao().getByStandardName(standard.name);
        List<Classes> list2 = new ArrayList<>();
        if (list1.isEmpty())
            return list2;
        for (ClassStandard c : list1)
            list2.add(classDao().getClassByName(c.className));
        return list2;
    }

    public LiveData<List<Standard>> getStandardForClassAsync(Classes classes) {
        MutableLiveData<List<Standard>> list = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            list.postValue(getStandardForClass(classes));
        });
        return list;
    }

    private List<Standard> getStandardForClass(Classes classes) {
        List<ClassStandard> list1 = classStandardDao().getByClassName(classes.name);
        List<Standard> list2 = new ArrayList<>();
        if (list1.isEmpty())
            return list2;
        for (ClassStandard c : list1)
            list2.add(standardDao().getStandard(c.standardName));
        return list2;
    }

    public LiveData<Standard> getFirstStandardForClassAsync(Classes classes) {
        MutableLiveData<Standard> standard = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            List<Standard> list2 = getStandardForClass(classes);
            if (list2.isEmpty())
                standard.postValue(null);
            else
                standard.postValue(list2.get(0));
        });
        return standard;
    }

    private Standard getFirstStandardForClass(Classes classes) {
        List<Standard> list2 = getStandardForClass(classes);
        if (list2.isEmpty())
            return null;
        else
            return list2.get(0);
    }

    public LiveData<Boolean> addObjectiveAsync(String text, String unitN, String standardN) {
        MutableLiveData<Boolean> added = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            Objective o = objectiveDao().getObjective(text, unitN, standardN);
            if (o != null) {
                added.postValue(false);
            } else {
                addObjective(text, unitN, standardN);
                added.postValue(true);
            }
        });
        return added;
    }

    private void addObjective(String text, String unitN, String standardN) {
        if (objectiveDao().getObjective(text,unitN,standardN)!= null)
            return;
        Objective objective = new Objective(text, unitN, standardN);
        objectiveDao().insertAll(objective);
        objective = objectiveDao().getObjective(objective.text, objective.unitName, objective.standardName);
        Standard s = standardDao().getStandard(objective.standardName);
        List<Classes> list2 = getClassForStandard(s);
        if (!list2.isEmpty()) {
            for (Classes c : list2)
                progressDao().insertAll(new Progress(c.name, objective.index));
        }
    }

    public LiveData<Boolean> addUnitAsync(String name, String standardName) {
        MutableLiveData<Boolean> added = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            if (unitDao().getUnit(name, standardName) != null) {
                added.postValue(false);
            } else {
                addUnit(name, standardName);
                added.postValue(true);
            }
        });
        return added;
    }

    private void addUnit(String name, String standardName) {
        if (unitDao().getUnit(name,standardName) != null)
            return;
        Unit unit = new Unit(name, standardName);
        if (unitDao().getUnit(name, standardName) == null)
            unitDao().insertAll(unit);
    }

    public LiveData<Boolean> addStandardAsync(String name, String subName) {
        MutableLiveData<Boolean> added = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            Standard s = standardDao().getStandard(name);
            if (s != null) {
                added.postValue(false);
            }else {
                addStandard(name, subName);
                added.postValue(true);
            }
        });
        return added;
    }

    private void addStandard(String name, String subName) {
        if(standardDao().getStandard(name) != null  )
            return;
        SubjectName subjectName = subjectDao().getSubject(subName);
        Integer color = Val.colors[new Random().nextInt(Val.colors.length)];
        Standard standard = new Standard(name, color, subName);
        if (standardDao().getStandard(name) == null)
            standardDao().insertAll(standard);
    }

    public LiveData<Boolean> addClassAsync(String name , String sName) {
        MutableLiveData<Boolean> added = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            if(classDao().getClassByName(name) == null){
                addClass(name);
                Standard newS = standardDao().getStandard(sName);
                attachClassToStandard(getClassByName(name), newS);
                added.postValue(true);
            }
            else{
                added.postValue(false);
            }
            addClass(name);
        });
        return added;
    }

    private void addClass(String name) {
        Integer color = Val.colors[new Random().nextInt(Val.colors.length)];
        Classes classes = new Classes(name, color);
        if (classDao().getClassByName(name) == null)
            classDao().insertAll(classes);
    }

    public LiveData<Boolean> addStudentAsync(String name, String className) {
        MutableLiveData<Boolean> done = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            if ( studentDao().getStudents(name, className) != null)
                done.postValue(false);
            else {
                addStudent(name, className);
                done.postValue(true);
            }
        });
        return done;
    }

    private void addStudent(String name, String className) {
        Integer color = Val.colors[new Random().nextInt(Val.colors.length)];
        Student student = new Student(name, color, className);
        studentDao().insertAll(student);
    }

    public void addSubjectAsync(String name, Integer res) {
    }

    private void addSubject(String name, Integer res) {
        SubjectName subjectName = new SubjectName(name, res);
        if (subjectDao().getSubject(name) == null)
            subjectDao().insertAll(subjectName);
    }

    public LiveData<Boolean> attachClassToStandardAsync(Classes classes, String standard) {
        MutableLiveData<Boolean> attached = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            if(classStandardDao().getClassStandard(standard,classes.name).isEmpty())
            {
                attachClassToStandard(classes,standardDao().getStandard( standard) );
                attached.postValue(true);
            }else{
                attached.postValue(false);
            }
        });
        return attached;
    }

    private void attachClassToStandard(Classes classes, Standard standard) {
        classStandardDao().insertAll(new ClassStandard(classes.name, standard.name));
        List<Objective> objectives = objectiveDao().getObjectives(standard.name);
        for (Objective o : objectives)
            progressDao().insertAll(new Progress(classes.name, o.index));

    }

    public LiveData<Boolean> detachClassToStandardAsync(Classes selClass, List<Standard> newS) {
        MutableLiveData<Boolean> detached = new MutableLiveData<>();
        AsyncTask.execute(()-> {
            for(Standard s : newS)
                detachClassToStandard(selClass,s);
            detached.postValue(true);
        });
        return detached;
    }

    private void detachClassToStandard(Classes selClass, Standard newS) {
        classStandardDao().deleteClassStandard(selClass.name, newS.name);
        List<Objective> objectives = objectiveDao().getObjectives(newS.name);
        for (Objective o : objectives)
            progressDao().deleteProgresses(selClass.name, o.index);

    }

    public Classes getClassByNameAsync(String name) {
        return classDao().getClassByName(name);
    }

    private Classes getClassByName(String name) {
        return classDao().getClassByName(name);
    }


    public Standard getStandardByNameAsync(String name) {
        return standardDao().getStandard(name);
    }

    private Standard getStandardByName(String name) {
        return standardDao().getStandard(name);
    }

    public LiveData<Boolean> getProgressAsync(Objective objective, Classes classes) {
        MutableLiveData<Boolean> done = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            done.postValue(getProgress(objective, classes));
        });
        return done;
    }

    private Boolean getProgress(Objective objective, Classes classes) {
        return progressDao().getProgresses(classes.name, objective.index).done;
    }

    public LiveData<Boolean> setProgressAsync(Objective objective, Classes classes, Boolean b) {
        MutableLiveData<Boolean> done = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            setProgress(objective, classes, b);
            done.postValue(true);
        });
        return done;
    }

    private void setProgress(Objective objective, Classes classes, Boolean b) {
        Progress p = progressDao().getProgresses(classes.name, objective.index);
        editProgress(p, classes.name, objective.index, b);
    }

    public LiveData<Integer> countObjectiveAsync(Unit unit) {
        MutableLiveData<Integer> value = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            value.postValue(countObjective(unit));
        });
        return value;
    }

    private Integer countObjective(Unit unit) {
        return objectiveDao().getObjectives(unit.name, unit.standardName).size();
    }


    public Integer countObjectiveAsync(Standard standard) {
        return 1;
    }

    private Integer countObjective(Standard standard) {
        return objectiveDao().getObjectives(standard.name).size();
    }

    public Integer countDoneObjectiveAsync(Unit unit, Classes classes) {
        return 1;
    }

    private Integer countDoneObjective(Unit unit, Classes classes) {
        int count = 0;
        for (Objective o : objectiveDao().getObjectives(unit.name, unit.standardName)) {
            if (getProgress(o, classes))
                count++;
        }
        return count;
    }

    public Integer countDoneObjectiveAsync(Standard standard, Classes classes) {
        return 1;
    }

    private Integer countDoneObjective(Standard standard, Classes classes) {
        int count = 0;
        for (Objective o : objectiveDao().getObjectives(standard.name)) {
            if (getProgress(o, classes))
                count++;
        }
        return count;
    }

    public LiveData<String> getPercentTextForUnitAsync(Unit u, Classes c){
        MutableLiveData<String> text = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            Integer x = countObjective(u);
            Integer y = countDoneObjective(u, c);
            text.postValue(format("%d of %d Objective", y, x));
        });
        return text;

    }

    public LiveData<Integer> getPercentForUnitAsync(Unit u, Classes c) {
        MutableLiveData<Integer> integer = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            integer.postValue(getPercentForUnit(u, c));
        });
        return integer;
    }

    private Integer getPercentForUnit(Unit u, Classes c) {
        Float x = 100f;
        x = x * countDoneObjective(u, c).floatValue() / countObjective(u).floatValue();
        return x.intValue();
    }

    public LiveData<Integer> getPercentForStandardAsync(Standard s, Classes c) {
        MutableLiveData<Integer> integer = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            integer.postValue(getPercentForStandard(s, c));
        });
        return integer;
    }

    private Integer getPercentForStandard(Standard s, Classes c) {
        Float x = 100f;
        x = x * countDoneObjective(s, c).floatValue() / countObjective(s).floatValue();
        return x.intValue();
    }

    public Boolean checkStandardAsync(String name) {
        return true;
    }

    private Boolean checkStandard(String name) {
        Standard s = standardDao().getStandard(name);
        if (s == null)
            return false;
        else
            return true;
    }

    public LiveData<List<Classes>> getAllClassAsync() {
        return classDao().getAllAsync();
    }

    public LiveData<List<Standard>> getAllStandardAsync() {
        return standardDao().getAllAsync();
    }

    public LiveData<SubjectName> getSubjectAsync(String name) {
        return subjectDao().getSubjectAsync(name);
    }

    public LiveData<List<Unit>> getUnitsAsync(String standard) {
        return unitDao().getUnitsAsync(standard);
    }

    public LiveData<List<Objective>> getObjectivesAsync(String unit, String standard) {
        return objectiveDao().getObjectivesAsync(unit, standard);
    }

    public LiveData<List<Student>> getStudentsAsync(String classs) {
        return studentDao().getStudentsAsync(classs);
    }


    public LiveData<List<SubjectName>> getAllSubjectAsync(){
        return subjectDao().getAllAsync();
    }






    public void loadDataAsync() {
        AsyncTask.execute(() -> {
            loadSubject();
            loadDefaultStandard(new CambridgeAdditionalMathematics());
            loadDefaultStandard(new AdditionalMathematics1Sudan());
            loadDefaultStandard(new AddittionalMathematics2Sudan());
            loadClasses();
            loadClassStandard();
            loadStudent();
        });


    }

    private void loadClassStandard() {
        List<Classes> list = classDao().getAll();
        Standard one = standardDao().getAll().get(0);

        for (Classes c : list) {
            attachClassToStandard(c, one);
        }
    }

    private void loadSubject() {
        // Do an asynchronous operation to fetch users.
        final int[] resource = {R.drawable.religion, R.drawable.functions
                , R.drawable.science,
                R.drawable.physics_24,
                R.drawable.res3,
                R.drawable.public_24,
                R.drawable.ic_action_name};
        String[] names = {"Religion", "Mathematics", "Chemistry", "Physics"
                , "Engineering", "Geography", "History"};

        for (int i = 0; i < 7; i++) {
            addSubject(names[i], resource[i]);
        }
    }


    private void loadClasses() {

        String[] names = {"Grade2 Almanar School", "Summer Course"};

        for (int i = 0; i < 2; i++) {
            addClass(names[i]);
        }
    }


    private void loadStudent() {
        List<Classes> list = classDao().getAll();

        String[] student = {"Fatima", "Ibrahim", "Omer", "Samir", "Mohamed", "Ali", "Hassan"};
        for (Classes c : list)
            for (int i = 0; i < 7; i++) {

                addStudent(student[i], c.name);
            }
    }

    public void loadDefaultStandard(StandardModel sd) {
        addStandard(sd.getName(), "Mathematics");
        int count = 0;
        for (String unit : sd.getUnit()) {
            addUnit(unit, sd.getName());
            for (String objective : sd.getObjective().get(count))
                addObjective(objective, unit, sd.getName());
            count++;
        }

    }

    public LiveData<String> getBackUpAsync() {
        MutableLiveData<String> json = new MutableLiveData<>();
        AsyncTask.execute(()-> {
            json.postValue(getBackUp());
        });
        return json;
    }

    private String getBackUp() {
        Backup backup = new Backup();
        backup.classList = classDao().getAll();
        backup.standardList = standardDao().getAll();
        backup.classStandardList = classStandardDao().getAll();
        backup.studentList = studentDao().getAll();
        backup.unitList = unitDao().getAll();
        backup.objectiveList = objectiveDao().getAll();
        backup.progressList = progressDao().getAll();
        return backup.toJson();
    }

    public LiveData<Boolean> loadFromBackupAsync(Backup backup) {
        MutableLiveData<Boolean> done = new MutableLiveData<>();
        AsyncTask.execute(()-> {
            deleteAllData();
            loadFromBackup(backup);
            done.postValue(true);
        });
    return done;
    }

    private void loadFromBackup(Backup backup) {
        standardDao().insertAll(backup.standardList.toArray(new Standard[0]));
        classDao().insertAll(backup.classList.toArray(new Classes[0]));
        classStandardDao().insertAll(backup.classStandardList.toArray(new ClassStandard[0]));
        studentDao().insertAll(backup.studentList.toArray(new Student[0]));
        progressDao().insertAll(backup.progressList.toArray(new Progress[0]));
        unitDao().insertAll(backup.unitList.toArray(new Unit[0]));
        objectiveDao().insertAll(backup.objectiveList.toArray(new Objective[0]));
    }

    private void deleteAllData() {
        standardDao().deleteAll();
        classDao().deleteAll();
        classStandardDao().deleteAll();
        studentDao().deleteAll();
        progressDao().deleteAll();
        unitDao().deleteAll();
        objectiveDao().deleteAll();
    }
}