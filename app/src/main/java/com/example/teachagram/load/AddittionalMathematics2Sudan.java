package com.example.teachagram.load;

import com.example.teachagram.daos.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddittionalMathematics2Sudan implements StandardModel {
    static final String name = "الرياضيات المتخصصه 2";
    static final String[] units = {"الدوال الحقيفيه والنهايات"
            , "التفاضل", "تطبيقات على التفاضل", "التكامل"
            , "التكامل المجدد وتطبيقاته", "الدائره", "مجموعه الاعداد المركبه"};
    static final List<String[]> objectives = new ArrayList<>();


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getUnit() {
        return units;
    }

    @Override
    public List<String[]> getObjective() {
        return objectives;
    }

    public AddittionalMathematics2Sudan() {
        objectives.add(new String[]
                {"تمهيد لحساب التفاضل والتكامل", "الدوال الحقيفيه والنهايات"
                        , "التطبيق",
                        "تركيبات الدوال", "التوافيق",
                        "النهايات", "النهايات للدوال الكسربه",
                        "بعض النهايات الهامه", "الدوال المتصله"});
        objectives.add(new String[]
                {"التغير ومتوسط معدل التغير",
                        "مشتقه الداله"
                        , "ايجاد المشتقه الاولى لبض الدوال",
                        "القواعد الاساسيه للتفاضل",
                        "داله الداله"
                        , "تفاضل الدوال المعرفه ضمنيه",
                        "المشتقات العليا"});
        objectives.add(new String[]
                {"تطبيقات على الهندسة التحليلة",
                        "النهايات العظمى والصغرى"
                        , "تطبيقات في الميكانيكا",
                        "المعدلات الزمنيه المرتبطه"});
        objectives.add(new String[]
                {"التكامل كعمليه عكسيه للتفاضل",
                        "بعض طرق التكامل"});
        objectives.add(new String[]
                {"التكامل المحدد", "بعض خواص التكامل المحدد"
                        , "المساحات",
                        "النظريه الاساسيه للتكامل"});
        objectives.add(new String[]
                {"معادله الدائره", "الصوره العامه لمعادله الدائره"
                        , "الدائره التي تحقق شروطا معبنه",
                        "معادله المماس لدائره عند نقطه عليها",
                        "طول المماس المرسوم لدائره من نقطه خارجها"});
        objectives.add(new String[]
                {"التمثيل البياني والصوره القطبيه للعدد المركب",
                        "بعض خواص الصوره القطبيه للعدد المركب"
                        , "القوي ونظريه دي موافر",
                        "حل معادلات الدرجه الثانيه في مجموعه الاعداد المركبه",
                        "الجذور التكعيببه للواحد الصحيح"});
    }



}