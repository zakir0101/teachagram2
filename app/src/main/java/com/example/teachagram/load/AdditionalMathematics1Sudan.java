package com.example.teachagram.load;

import com.example.teachagram.daos.AppDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdditionalMathematics1Sudan implements StandardModel{
    final String name = "الرياضيات المتخصصه 1";
    final String[] units = {"الاستنتاج الرياضي"
            , "المصفوفات", "الكسور الجزئيه", "الاحتمالات", "الاحصاء"};
    final List<String[]> objectives = new ArrayList<>();

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

    public AdditionalMathematics1Sudan() {
        objectives.add(new String[]
                {"مبدا الاستنتاج الرياضي", "مبدا العد"
                        , "مضروب العدد الطبيعي وخصائصه",
                        "التباديل", "التوافيق",
                        "نطريه دات الحدين"});
        objectives.add(new String[]
                {"تمهيد", "بعد الانواع الخاصه من المصفوفات"
                        , "تساوي المصفوفات",
                        "منقول المصفوفات", "جمع المصفوفات"
                        , "خواص جمع المصفوفات", "طرح المصفوفات"
                        , "ضرب المصفوفه بعدد ثابت"
                        , "خواص ضرب المصفوفه بعدد",
                        "ضرب المصفوفات",
                        "بعض خواص ضرب المصفوفات"});
        objectives.add(new String[]
                {"تمهيد",
                        "الحاله الاولى : عندما تكون معاملات المقام خطيه"
                        , "الحاله الثانيه : عندما يكون احد معاملات المقام خطيه متكررا",
                        "الحاله الثالثه : اذا كان احد معاملات المقام من الدرجه الثانيه ولا يمكن تحليله"});
        objectives.add(new String[]
                {"مقدمع", "التجربه العشوائيه", "فضاء العينه",
                        "الحادثه", "العمليات علي الحوادث",
                        "مسلمات نظريه الاحتمالات", "الاحتمالات المتساويه"
                        , "قانون الاحتمال الثنائي", "قانون الاحتمال الكلي"});
        objectives.add(new String[]
                {"مقدمه ونبذه تاريخيه", "مقاييس النزعه المركزيه"
                        , "حساب الوسط الحسابي من جدول تكراري ذي فئات",
                        "الوسيط", "المنوال", "التشتت"});


    }
}



